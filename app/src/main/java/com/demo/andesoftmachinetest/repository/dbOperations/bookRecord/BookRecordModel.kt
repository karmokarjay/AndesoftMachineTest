package com.demo.andesoftmachinetest.repository.dbOperations.bookRecord

import androidx.room.Entity
import androidx.room.PrimaryKey
import androidx.room.TypeConverters
import com.demo.andesoftmachinetest.repository.dbOperations.DataTypeConverter

@Entity(tableName = "book_record_table")
data class BookRecordModel(

    @PrimaryKey(autoGenerate = true)
    val bookId: Int,
    val bookName: String,
    val bookAuthorName: String,
    val bookPrice: Int,
    val bookDateOfIssue: String,

    @TypeConverters(DataTypeConverter::class)
    val bookThumbnailImageUriArrayList: List<String>
)