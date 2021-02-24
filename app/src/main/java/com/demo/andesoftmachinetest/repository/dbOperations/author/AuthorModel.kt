package com.demo.andesoftmachinetest.repository.dbOperations.author

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "author_table")
data class AuthorModel(
    @PrimaryKey
    val authorName: String
)