package ru.gmpopov.recipeapp.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import kotlinx.coroutines.flow.Flow
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity

@Dao
interface CategoryDao {
    @Query("SELECT * FROM categories ORDER BY name")
    fun getAllCategories(): Flow<List<CategoryEntity>>
}