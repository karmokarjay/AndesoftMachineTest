package com.demo.andesoftmachinetest.repository.viewModel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import com.demo.andesoftmachinetest.repository.AuthorRepository
import com.demo.andesoftmachinetest.repository.dbOperations.DatabaseOperations
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class AuthorViewModel(application: Application) : AndroidViewModel(application) {

    val authorDataLiveData: LiveData<List<AuthorModel>>
    private val authorRepository: AuthorRepository

    init {
        val authorDao = DatabaseOperations.getDatabase(application).authorDao()
        authorRepository = AuthorRepository(authorDao)
        authorDataLiveData = authorRepository.readAllAuthorData
    }

    fun addAuthor(authorData: AuthorModel) {
        viewModelScope.launch(Dispatchers.IO) {
            authorRepository.addAuthorData(authorData)
        }
    }
}