package ru.gmpopov.recipeapp.data.database.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "recipes")
data class RecipeEntity(
    @PrimaryKey val id: Int,
    val title: String,
    @ColumnInfo("category_id")
    val categoryId: Int,
    val imageUrl: String,
    val ingredients: String,
    val method: String,
)