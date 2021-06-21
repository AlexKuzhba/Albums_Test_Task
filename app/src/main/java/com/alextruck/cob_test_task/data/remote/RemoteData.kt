package com.alextruck.cob_test_task.data.remote

import android.util.Log
import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.data.error.NETWORK_ERROR
import com.alextruck.cob_test_task.data.error.NO_INTERNET_CONNECTION
import com.alextruck.cob_test_task.data.local.LocalData
import com.alextruck.cob_test_task.data.remote.service.AlbumsService
import com.alextruck.cob_test_task.utils.NetworkConnectivity
import com.google.gson.Gson
import retrofit2.Response
import java.io.IOException
import javax.inject.Inject
import kotlin.reflect.javaType
import kotlin.reflect.typeOf

/**
 * Created by AlextrucK
 */

@ExperimentalStdlibApi
class RemoteData @Inject
constructor(private val serviceGenerator: ServiceGenerator, private val localRepository: LocalData, private val networkConnectivity: NetworkConnectivity) : RemoteDataSource {
    override suspend fun requestAlbums(): Resource<Albums> {
        val albumsService = serviceGenerator.createService(AlbumsService::class.java)
        return when (val response = processCall(albumsService::fetchAlbums)) {
            is List<*> -> {
                val list = response as ArrayList<AlbumItem>
                list.sortBy { it.title }
                val albums = Albums(list)
                Resource.Success(data = albums)
            }
            else -> {
                Resource.DataError(errorCode = response as Int)
            }
        }
    }

    private suspend fun processCall(responseCall: suspend () -> Response<*>): Any? {
        if (!networkConnectivity.isConnected()) {
            val content = localRepository.getCachedAlbums()
            if (!content.isNullOrEmpty() && !content.contains("Failed")) {
                val gson = Gson()
                val type = typeOf<ArrayList<AlbumItem>>().javaType
                val array = gson.fromJson<ArrayList<AlbumItem>>(content, type)
                return array
            }
            return NO_INTERNET_CONNECTION
        }
        return try {
            val response = responseCall.invoke()
            val responseCode = response.code()
            if (response.isSuccessful) {
                val gson = Gson()
                val json = gson.toJson(response.body())
                localRepository.cacheAlbums(json)
                response.body()
            } else {
                responseCode
            }
        } catch (e: IOException) {
            NETWORK_ERROR
        }
    }
}