package com.alextruck.cob_test_task.data.local

import android.content.Context
import android.content.SharedPreferences
import com.alextruck.cob_test_task.ALBUMS_KEY
import com.alextruck.cob_test_task.SHARED_PREFERENCES_FILE_NAME
import com.alextruck.cob_test_task.data.Resource
import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.google.gson.Gson
import javax.inject.Inject

/**
 * Created by AlextrucK
 */

class LocalData @Inject constructor(val context: Context) {

    fun getCachedAlbums(): String {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val content = sharedPref.getString(ALBUMS_KEY, null) ?: return "Failed"
        return content
    }

    fun cacheAlbums(content: String): Resource<Boolean> {
        val sharedPref = context.getSharedPreferences(SHARED_PREFERENCES_FILE_NAME, 0)
        val editor: SharedPreferences.Editor = sharedPref.edit()
        editor.putString(ALBUMS_KEY, content)
        editor.apply()
        val isSuccess = editor.commit()
        return Resource.Success(isSuccess)
    }
}