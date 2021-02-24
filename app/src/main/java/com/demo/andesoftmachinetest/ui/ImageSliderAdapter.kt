package com.demo.andesoftmachinetest.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import com.demo.andesoftmachinetest.R
import com.demo.andesoftmachinetest.ui.base.BaseAdapter
import com.demo.andesoftmachinetest.ui.base.BaseViewHolder

class ImageSliderAdapter : BaseAdapter<String, BaseViewHolder<String>>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<String> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.image_slider_row_item, parent, false)
        return ImageSliderViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<String>, position: Int) {
        holder.loadData(listOfItems[position] as String)
    }

    override fun getItemCount(): Int = listSize
}