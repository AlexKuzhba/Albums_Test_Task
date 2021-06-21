package com.alextruck.cob_test_task.ui.base.listeners

import com.alextruck.cob_test_task.data.dto.albums.AlbumItem

/**
 * Created by AlextrucK
 */

interface RecyclerItemListener {
    fun onItemSelected(recipe : AlbumItem)
}