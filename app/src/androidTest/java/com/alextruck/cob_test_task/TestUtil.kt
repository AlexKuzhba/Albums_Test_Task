package com.alextruck.cob_test_task

import android.content.Context
import androidx.test.platform.app.InstrumentationRegistry
import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.alextruck.cob_test_task.data.dto.albums.Albums
import com.alextruck.cob_test_task.data.remote.moshiFactories.MyKotlinJsonAdapterFactory
import com.alextruck.cob_test_task.data.remote.moshiFactories.MyStandardJsonAdapters
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import java.io.InputStream
import java.lang.reflect.Type

/**
 * Created by AlextrucK
 */

object TestUtil {
    var dataStatus: DataStatus = DataStatus.Success
    var recipes: Albums = Albums(arrayListOf())
    fun initData(): Albums {
        val moshi = Moshi.Builder()
            .add(MyKotlinJsonAdapterFactory())
            .add(MyStandardJsonAdapters.FACTORY)
            .build()
        val type: Type = Types.newParameterizedType(List::class.java, AlbumItem::class.java)
        val adapter: JsonAdapter<List<AlbumItem>> = moshi.adapter(type)
        val jsonString = getJson("AlbumsApiResponse.json")
        adapter.fromJson(jsonString)?.let {
            recipes = Albums(ArrayList(it))
            return recipes
        }
        return Albums(arrayListOf())
    }

    private fun getJson(path: String): String {
        val ctx: Context = InstrumentationRegistry.getInstrumentation().targetContext
        val inputStream: InputStream = ctx.classLoader.getResourceAsStream(path)
        return inputStream.bufferedReader().use { it.readText() }
    }
}