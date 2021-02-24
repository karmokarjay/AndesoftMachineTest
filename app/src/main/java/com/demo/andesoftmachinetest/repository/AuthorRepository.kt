package com.demo.andesoftmachinetest.repository

import androidx.lifecycle.LiveData
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorDao
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorModel

class AuthorRepository(private val authorDao: AuthorDao) {

    val readAllAuthorData: LiveData<List<AuthorModel>> = authorDao.readAllAuthorDataFromDB()

    suspend fun addAuthorData(authorModel: AuthorModel) {
        authorDao.addAuthorInDB(authorModel)
    }
}