package com.alextruck.cob_test_task.data.remote

import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.Albums

/**
 * Created by AlextrucK
 */

internal interface RemoteDataSource {
    suspend fun requestAlbums(): Resource<Albums>
}