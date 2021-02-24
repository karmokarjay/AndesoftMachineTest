package com.demo.andesoftmachinetest.repository.dbOperations

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorDao
import com.demo.andesoftmachinetest.repository.dbOperations.author.AuthorModel
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordDao
import com.demo.andesoftmachinetest.repository.dbOperations.bookRecord.BookRecordModel

@Database(
    entities = [AuthorModel::class, BookRecordModel::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(DataTypeConverter::class)
abstract class DatabaseOperations : RoomDatabase() {
    abstract fun authorDao(): AuthorDao
    abstract fun bookRecordDao(): BookRecordDao

    companion object {
        @Volatile
        private var DB_INSTANCE: DatabaseOperations? = null

        fun getDatabase(context: Context): DatabaseOperations {
            val tempInstance = DB_INSTANCE
            if (tempInstance != null) {
                return tempInstance
            }

            synchronized(this) {
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    DatabaseOperations::class.java,
                    "book_database"
                ).build()
                DB_INSTANCE = instance
                return instance
            }
        }
    }
}