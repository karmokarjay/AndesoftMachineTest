package com.demo.andesoftmachinetest.ui

import android.view.View
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.demo.andesoftmachinetest.R
import com.demo.andesoftmachinetest.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.image_slider_row_item.view.*

class ImageSliderViewHolder(itemView: View) : BaseViewHolder<String>(itemView) {
    override fun loadData(receivedData: String) {

        val uri = receivedData.toUri()
        Glide.with(itemView.context).load(uri)
            .placeholder(R.drawable.ic_book_thumbnail_placeholder)
            .into(itemView.iv_book_image_item)
    }
}