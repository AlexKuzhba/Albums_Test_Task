package com.alextruck.cob_test_task.utils

import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.alextruck.cob_test_task.data.remote.moshiFactories.MyStandardJsonAdapters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.File
import java.lang.reflect.Type

/**
 * Created by AlextrucK
 */

class TestModelsGenerator {
    private var albums: Albums = Albums(arrayListOf())

    init {
        val moshi = Moshi.Builder()
            .add(MyKotlinJsonAdapterFactory())
            .add(MyStandardJsonAdapters.FACTORY)
            .build()
        val type: Type = Types.newParameterizedType(List::class.java, AlbumItem::class.java)
        val adapter: JsonAdapter<List<AlbumItem>> = moshi.adapter(type)
        val jsonString = getJson("AlbumsApiResponse.json")
        adapter.fromJson(jsonString)?.let {
            albums = Albums(ArrayList(it))
        }
        print("this is $albums")
    }

    fun generateRecipes(): Albums {
        return albums
    }

    fun generateRecipesModelWithEmptyList(): Albums {
        return Albums(arrayListOf())
    }

    fun generateRecipesItemModel(): AlbumItem {
        return albums.albumsList[0]
    }

    fun getStubSearchTitle(): String {
        return albums.albumsList[0].title
    }


    /**
     * Helper function which will load JSON from
     * the path specified
     *
     * @param path : Path of JSON file
     * @return json : JSON from file at given path
     */

    private fun getJson(path: String): String {
        // Load the JSON response
        val uri = this.javaClass.classLoader?.getResource(path)
        val file = File(uri?.path)
        return String(file.readBytes())
    }
}