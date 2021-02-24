package com.demo.andesoftmachinetest.ui.homeScreen

import android.view.View
import androidx.core.net.toUri
import com.bumptech.glide.Glide
import com.demo.andesoftmachinetest.R
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel
import com.demo.andesoftmachinetest.ui.base.BaseViewHolder
import kotlinx.android.synthetic.main.book_record_single_row_item.view.*

class BookRecordsViewHolder(itemView: View) : BaseViewHolder<BookRecordModel>(itemView) {
    override fun loadData(receivedData: BookRecordModel) {}
    override fun loadData(data: BookRecordModel, position: Int) {
        val uri = data.bookThumbnailImageUriArrayList[0].toUri()
        Glide.with(itemView.context).load(uri)
            .placeholder(R.drawable.ic_book_thumbnail_placeholder)
            .into(itemView.iv_book_cover_photo)

        itemView.tv_book_name_value.text = data.bookName
        itemView.tv_author_name_value.text = data.bookAuthorName
        itemView.tv_book_price_value.text = data.bookPrice.toString()
        itemView.tv_book_date_of_issue_value.text = data.bookDateOfIssue

        itemView.iv_book_cover_photo.setOnClickListener {
            itemClickCallbackWithPosAndData?.onItemClickedWithPositionAndData(
                position,
                data
            )
        }
    }
}