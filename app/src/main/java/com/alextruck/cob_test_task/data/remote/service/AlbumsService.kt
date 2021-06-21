package com.alextruck.cob_test_task.data.remote.service

import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import retrofit2.Response
import retrofit2.http.GET

/**
 * Created by AlextrucK
 */

interface AlbumsService {
    @GET("albums")
    suspend fun fetchAlbums(): Response<List<AlbumItem>>
}