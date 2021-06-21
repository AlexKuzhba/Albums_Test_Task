package com.alextruck.cob_test_task.data

import com.alextruck.cob_test_task.data.dto.albums.Albums
import kotlinx.coroutines.flow.Flow

/**
 * Created by AlextrucK
 */

interface DataRepositorySource {
    suspend fun requestAlbums(): Flow<Resource<Albums>>
}