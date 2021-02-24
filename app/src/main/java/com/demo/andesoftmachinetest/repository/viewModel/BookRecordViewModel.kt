package com.demo.andesoftmachinetest.repository.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.demo.andesoftmachinetest.repository.BookRecordRepository
import com.demo.andesoftmachinetest.repository.dbOperations.DatabaseOperations
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class BookRecordViewModel(application: Application) : AndroidViewModel(application) {

    val bookRecordLiveData: LiveData<List<BookRecordModel>>
    private val bookRecordRepository: BookRecordRepository

    init {
        val bookRecordDao = DatabaseOperations.getDatabase(application).bookRecordDao()
        bookRecordRepository = BookRecordRepository(bookRecordDao)
        bookRecordLiveData = bookRecordRepository.readAllBookRecordData
    }

    fun addBookRecord(bookRecordData: BookRecordModel) {
        viewModelScope.launch(Dispatchers.IO) {
            bookRecordRepository.addBookRecordData(bookRecordData)
        }
    }
}