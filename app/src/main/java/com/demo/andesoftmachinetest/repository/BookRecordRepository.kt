package com.demo.andesoftmachinetest.repository

import androidx.lifecycle.LiveData
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordDao
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel

class BookRecordRepository(private val bookRecordDao: BookRecordDao) {

    val readAllBookRecordData: LiveData<List<BookRecordModel>> =
        bookRecordDao.readAllBookRecordDataFromDB()

    suspend fun addBookRecordData(bookRecordModel: BookRecordModel) {
        bookRecordDao.addBookRecordInDB(bookRecordModel)
    }
}