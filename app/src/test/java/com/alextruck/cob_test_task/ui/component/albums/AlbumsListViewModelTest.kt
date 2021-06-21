package com.alextruck.cob_test_task.ui.component.albums

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.alextruck.cob_test_task.data.DataRepository
import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.data.error.NETWORK_ERROR
import com.alextruck.cob_test_task.utils.InstantExecutorExtension
import com.alextruck.cob_test_task.utils.MainCoroutineRule
import com.alextruck.cob_test_task.utils.TestModelsGenerator
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flow
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.jupiter.api.extension.ExtendWith

@ExperimentalStdlibApi
@ExperimentalCoroutinesApi
@ExtendWith(InstantExecutorExtension::class)
class RecipesListViewModelTest {
    // Subject under test
    private lateinit var recipesListViewModel: AlbumsListViewModel

    // Use a fake UseCase to be injected into the viewModel
    private val dataRepository: DataRepository = mockk()

    // Set the main coroutines dispatcher for unit testing.
    @ExperimentalCoroutinesApi
    @get:Rule
    var mainCoroutineRule = MainCoroutineRule()

    // Executes each task synchronously using Architecture Components.
    @get:Rule
    var instantExecutorRule = InstantTaskExecutorRule()

    private lateinit var albumTitle: String
    private val testModelsGenerator: TestModelsGenerator = TestModelsGenerator()

    @Before
    fun setUp() {
        // Create class under test
        // We initialise the repository with no tasks
        albumTitle = testModelsGenerator.getStubSearchTitle()
    }

    @Test
    fun `get Albums List`() {
        // Let's do an answer for the liveData
        val albumModel = testModelsGenerator.generateRecipes()

        //1- Mock calls
        coEvery { dataRepository.requestAlbums() } returns flow {
            emit(Resource.Success(albumModel))
        }

        //2-Call
        recipesListViewModel = AlbumsListViewModel(dataRepository)
        recipesListViewModel.getAlbums()
        //active observer for livedata
        recipesListViewModel.albumsLiveData.observeForever { }

        //3-verify
        val isEmptyList = recipesListViewModel.albumsLiveData.value?.data?.albumsList.isNullOrEmpty()
        assertEquals(albumModel, recipesListViewModel.albumsLiveData.value?.data)
        assertEquals(false,isEmptyList)
    }

    @Test
    fun `get Albums Empty List`() {
        // Let's do an answer for the liveData
        val recipesModel = testModelsGenerator.generateRecipesModelWithEmptyList()

        //1- Mock calls
        coEvery { dataRepository.requestAlbums() } returns flow {
            emit(Resource.Success(recipesModel))
        }

        //2-Call
        recipesListViewModel = AlbumsListViewModel(dataRepository)
        recipesListViewModel.getAlbums()
        //active observer for livedata
        recipesListViewModel.albumsLiveData.observeForever { }

        //3-verify
        val isEmptyList = recipesListViewModel.albumsLiveData.value?.data?.albumsList.isNullOrEmpty()
        assertEquals(recipesModel, recipesListViewModel.albumsLiveData.value?.data)
        assertEquals(true, isEmptyList)
    }

    @Test
    fun `get Albums Error`() {
        // Let's do an answer for the liveData
        val error: Resource<Albums> = Resource.DataError(NETWORK_ERROR)

        //1- Mock calls
        coEvery { dataRepository.requestAlbums() } returns flow {
            emit(error)
        }

        //2-Call
        recipesListViewModel = AlbumsListViewModel(dataRepository)
        recipesListViewModel.getAlbums()
        //active observer for livedata
        recipesListViewModel.albumsLiveData.observeForever { }

        //3-verify
        assertEquals(NETWORK_ERROR, recipesListViewModel.albumsLiveData.value?.errorCode)
    }
}