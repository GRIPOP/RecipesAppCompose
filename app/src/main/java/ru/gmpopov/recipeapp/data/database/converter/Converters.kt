package ru.gmpopov.recipeapp.data.database.converter

import androidx.room.TypeConverter


class Converters {
    @TypeConverter
    fun fromString(data: String): List<String> {
        return if (data.isEmpty()) {
            emptyList()
        } else {
            data.split("|||")
        }
    }

    @TypeConverter
    fun fromList(data: List<String>): String {
        return data.joinToString("|||")
    }
}