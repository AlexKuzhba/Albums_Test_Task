package com.alextruck.cob_test_task.ui.component.albums

import android.os.Bundle
import android.util.Log
import android.view.View.GONE
import android.view.View.VISIBLE
import androidx.activity.viewModels
import androidx.lifecycle.LiveData
import androidx.recyclerview.widget.LinearLayoutManager
import com.alextruck.cob_test_task.R
import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.databinding.AlbumsListActivityBinding
import com.alextruck.cob_test_task.ui.base.BaseActivity
import com.alextruck.cob_test_task.ui.component.albums.adapter.AlbumsAdapter
import com.alextruck.cob_test_task.utils.SingleEvent
import com.google.android.material.snackbar.Snackbar
import com.alextruck.cob_test_task.utils.*
import dagger.hilt.android.AndroidEntryPoint

/**
 * Created by AlextrucK
 */

@AndroidEntryPoint
class AlbumsListActivity : BaseActivity() {

    private lateinit var binding: AlbumsListActivityBinding

    private val albumsListViewModel: AlbumsListViewModel by viewModels()
    private lateinit var albumsAdapter: AlbumsAdapter

    override fun initViewBinding() {
        binding = AlbumsListActivityBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = getString(R.string.albums)
        val layoutManager = LinearLayoutManager(this)
        binding.rvAlbumsList.layoutManager = layoutManager
        binding.rvAlbumsList.setHasFixedSize(true)
        albumsListViewModel.getAlbums()
    }

    private fun bindListData(albums: Albums) {
        if (!(albums.albumsList.isNullOrEmpty())) {
            albumsAdapter = AlbumsAdapter(albumsListViewModel, albums.albumsList)
            binding.rvAlbumsList.adapter = albumsAdapter
            showDataView(true)
        } else {
            showDataView(false)
        }
    }

    private fun observeToast(event: LiveData<SingleEvent<Any>>) {
        binding.root.showToast(this, event, Snackbar.LENGTH_LONG)
    }

    private fun showDataView(show: Boolean) {
        binding.tvNoData.visibility = if (show) GONE else VISIBLE
        binding.rvAlbumsList.visibility = if (show) VISIBLE else GONE
        binding.pbLoading.toGone()
    }

    private fun showLoadingView() {
        binding.pbLoading.toVisible()
        binding.tvNoData.toGone()
        binding.rvAlbumsList.toGone()
    }

    private fun handleAlbumsList(status: Resource<Albums>) {
        when (status) {
            is Resource.Loading -> showLoadingView()
            is Resource.Success -> status.data?.let { bindListData(albums = it) }
            is Resource.DataError -> {
                showDataView(false)
                status.errorCode?.let { albumsListViewModel.showToastMessage(it) }
            }
        }
    }

    override fun observeViewModel() {
        observe(albumsListViewModel.albumsLiveData, ::handleAlbumsList)
        observeToast(albumsListViewModel.showToast)
    }
}