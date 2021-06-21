package com.alextruck.cob_test_task.data.dto.albums

import android.os.Parcelable
import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import kotlinx.android.parcel.Parcelize

@JsonClass(generateAdapter = false)
@Parcelize
data class AlbumItem(
    @Json(name = "id")
    val id: Int = 0,
    @Json(name = "userId")
    val userId: Int = 0,
    @Json(name = "title")
    val title: String = "Untitled"
) : Parcelable