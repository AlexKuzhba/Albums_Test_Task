package com.alextruck.cob_test_task.data

import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.data.local.LocalData
import com.alextruck.cob_test_task.data.remote.RemoteData
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * Created by AlextrucK
 */

@ExperimentalStdlibApi
class DataRepository @Inject constructor(private val remoteRepository: RemoteData, private val localRepository: LocalData, private val ioDispatcher: CoroutineContext) : DataRepositorySource {
    override suspend fun requestAlbums(): Flow<Resource<Albums>> {
        return flow {
            emit(remoteRepository.requestAlbums())
        }.flowOn(ioDispatcher)
    }
}