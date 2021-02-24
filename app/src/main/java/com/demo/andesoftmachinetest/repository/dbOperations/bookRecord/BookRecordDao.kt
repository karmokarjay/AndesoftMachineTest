package com.demo.andesoftmachinetest.repository.dbOperations.bookRecord

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface BookRecordDao {

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun addBookRecordInDB(bookRecord: BookRecordModel)

    @Query("SELECT * FROM book_record_table ORDER BY bookId ASC")
     fun readAllBookRecordDataFromDB(): LiveData<List<BookRecordModel>>
}