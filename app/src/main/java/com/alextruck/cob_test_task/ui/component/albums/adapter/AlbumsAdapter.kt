package com.alextruck.cob_test_task.ui.component.albums.adapter

import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.alextruck.cob_test_task.databinding.AlbumItemBinding
import com.alextruck.cob_test_task.ui.base.listeners.RecyclerItemListener
import com.alextruck.cob_test_task.ui.component.albums.AlbumsListViewModel

/**
 * Created by AlextrucK
 */

class AlbumsAdapter(private val albumsListViewModel: AlbumsListViewModel, private val albums: List<AlbumItem>) : RecyclerView.Adapter<AlbumsViewHolder>() {

    private val onItemClickListener: RecyclerItemListener = object : RecyclerItemListener {
        override fun onItemSelected(album: AlbumItem) {
            albumsListViewModel.openAlbumDialog(album)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val itemBinding = AlbumItemBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AlbumsViewHolder(itemBinding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {
        holder.bind(albums[position], onItemClickListener)
    }

    override fun getItemCount(): Int {
        return albums.size
    }
}