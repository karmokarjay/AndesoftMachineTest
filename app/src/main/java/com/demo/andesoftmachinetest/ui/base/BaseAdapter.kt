package com.demo.andesoftmachinetest.ui.base

import androidx.recyclerview.widget.RecyclerView

abstract class BaseAdapter<V : Any, T : RecyclerView.ViewHolder> : RecyclerView.Adapter<T>() {

    val listOfItems = ArrayList<Any>()
    val listSize: Int
        get() = listOfItems.size

    var itemClickCallback: BaseViewHolder.ItemClickedCallbackWithData<V>? = null
    var itemClickCallbackWithPosition: BaseViewHolder.ItemClickedCallbackWithPosition? = null
    var itemClickCallbackWithPosAndData: BaseViewHolder.ItemClickedCallbackWithPositionAndData<V>? =
        null

    fun addAll(list: ArrayList<V>) {
        this.listOfItems.clear()
        this.listOfItems.addAll(list)
    }

    fun add(singleItem: V) {
        listOfItems.add(singleItem)
    }

    fun appendList(list: ArrayList<V>) {
        this.listOfItems.addAll(list)
    }

    fun clearData() {
        this.listOfItems.clear()
        notifyChange()
    }

    @Synchronized
    fun notifyChange() {
        super.notifyDataSetChanged()
    }
}