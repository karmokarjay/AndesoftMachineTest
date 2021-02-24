package com.demo.andesoftmachinetest.repository.dbOperations.author

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AuthorDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun addAuthorInDB(authorData: AuthorModel)

    @Query("SELECT * FROM author_table")
     fun readAllAuthorDataFromDB(): LiveData<List<AuthorModel>>
}