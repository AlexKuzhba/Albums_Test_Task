package com.alextruck.cob_test_task

import com.alextruck.cob_test_task.TestUtil.dataStatus
import com.alextruck.cob_test_task.TestUtil.initData
import com.alextruck.cob_test_task.data.DataRepositorySource
import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.data.error.NETWORK_ERROR
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

/**
 * Created by AlextrucK
 */

class TestDataRepository @Inject constructor() : DataRepositorySource {

    override suspend fun requestAlbums(): Flow<Resource<Albums>> {
        return when (dataStatus) {
            DataStatus.Success -> {
                flow { emit(Resource.Success(initData())) }
            }
            DataStatus.Fail -> {
                flow { emit(Resource.DataError<Albums>(errorCode = NETWORK_ERROR)) }
            }
            DataStatus.EmptyResponse -> {
                flow { emit(Resource.Success(Albums(arrayListOf()))) }
            }
        }
    }
}