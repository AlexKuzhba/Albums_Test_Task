package com.alextruck.cob_test_task.ui.component.albums

import androidx.annotation.VisibleForTesting
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alextruck.cob_test_task.data.DataRepositorySource
import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.ui.base.BaseViewModel
import com.alextruck.cob_test_task.utils.SingleEvent
import com.alextruck.cob_test_task.utils.wrapEspressoIdlingResource
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * Created by AlextrucK
 */

@HiltViewModel
class AlbumsListViewModel @Inject
constructor(private val dataRepositoryRepository: DataRepositorySource) : BaseViewModel() {

    /**
     * Data --> LiveData, Exposed as LiveData, Locally in viewModel as MutableLiveData
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    val albumsLiveDataPrivate = MutableLiveData<Resource<Albums>>()
    val albumsLiveData: LiveData<Resource<Albums>> get() = albumsLiveDataPrivate

    /**
     * UI actions as event, user action is single one time event, Shouldn't be multiple time consumption
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val openAlbumDialogPrivate = MutableLiveData<SingleEvent<AlbumItem>>()
    val openAlbumDialog: LiveData<SingleEvent<AlbumItem>> get() = openAlbumDialogPrivate

    /**
     * Error handling as UI
     */
    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showSnackBarPrivate = MutableLiveData<SingleEvent<Any>>()
    val showSnackBar: LiveData<SingleEvent<Any>> get() = showSnackBarPrivate

    @VisibleForTesting(otherwise = VisibleForTesting.PRIVATE)
    private val showToastPrivate = MutableLiveData<SingleEvent<Any>>()
    val showToast: LiveData<SingleEvent<Any>> get() = showToastPrivate


    fun getAlbums() {
        viewModelScope.launch {
            albumsLiveDataPrivate.value = Resource.Loading()
            wrapEspressoIdlingResource {
                dataRepositoryRepository.requestAlbums().collect {
                    albumsLiveDataPrivate.value = it
                }
            }
        }
    }

    fun openAlbumDialog(album: AlbumItem) {
        openAlbumDialogPrivate.value = SingleEvent(album)
    }

    fun showToastMessage(errorCode: Int) {
        val error = errorManager.getError(errorCode)
        showToastPrivate.value = SingleEvent(error.description)
    }
}