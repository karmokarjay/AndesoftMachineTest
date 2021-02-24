package com.demo.andesoftmachinetest.repository.dbOperations

import androidx.room.TypeConverter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.Serializable
import java.lang.reflect.Type

class DataTypeConverter : Serializable {

    @TypeConverter // note this annotation
    fun fromListToJson(uriList: List<String?>?): String? {
        if (uriList == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.toJson(uriList, type)
    }

    @TypeConverter // note this annotation
    fun fromJsonToList(uriListString: String?): List<String?>? {
        if (uriListString == null) {
            return null
        }
        val gson = Gson()
        val type: Type = object : TypeToken<List<String?>?>() {}.type
        return gson.fromJson<List<String>>(uriListString, type)
    }

}