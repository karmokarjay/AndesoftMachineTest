package com.demo.andesoftmachinetest.ui.homeScreen

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.demo.andesoftmachinetest.R
import com.demo.andesoftmachinetest.ui.base.BaseAdapter
import com.demo.andesoftmachinetest.ui.base.BaseViewHolder
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel

class BookRecordsListingAdapter :
    BaseAdapter<BookRecordModel, BaseViewHolder<BookRecordModel>>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): BaseViewHolder<BookRecordModel> {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.book_record_single_row_item, parent, false)
        return BookRecordsViewHolder(view)
    }

    override fun onBindViewHolder(holder: BaseViewHolder<BookRecordModel>, position: Int) {
        holder.loadData(listOfItems[position] as BookRecordModel,position)
        if (position != RecyclerView.NO_POSITION) {
            holder.itemClickCallbackWithPosAndData = itemClickCallbackWithPosAndData
        }
    }

    override fun getItemCount(): Int = listSize

}