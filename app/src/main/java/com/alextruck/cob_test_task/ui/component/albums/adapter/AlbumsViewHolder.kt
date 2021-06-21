package com.alextruck.cob_test_task.ui.component.albums.adapter

import androidx.recyclerview.widget.RecyclerView
import com.alextruck.cob_test_task.data.dto.albums.AlbumItem
import com.alextruck.cob_test_task.databinding.AlbumItemBinding
import com.alextruck.cob_test_task.ui.base.listeners.RecyclerItemListener
import com.alextruck.cob_test_task.utils.toVisible

class AlbumsViewHolder(private val itemBinding: AlbumItemBinding) : RecyclerView.ViewHolder(itemBinding.root) {

    fun bind(albumItem: AlbumItem, recyclerItemListener: RecyclerItemListener) {
        itemBinding.tvUserId.text = albumItem.userId.toString()
        itemBinding.tvTitle.text = albumItem.title
        itemBinding.viewDivider.toVisible()
    }
}