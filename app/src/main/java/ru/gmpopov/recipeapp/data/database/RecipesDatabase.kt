package ru.gmpopov.recipeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import ru.gmpopov.recipeapp.data.database.converter.Converters
import ru.gmpopov.recipeapp.data.database.dao.CategoryDao
import ru.gmpopov.recipeapp.data.database.dao.RecipeDao
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity
import ru.gmpopov.recipeapp.data.database.entity.RecipeEntity

@TypeConverters(Converters::class)
@Database(
    entities = [
        CategoryEntity::class,
        RecipeEntity::class
    ],
    version = 2,
    exportSchema = false,
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

    abstract fun recipeDao(): RecipeDao

    companion object {
        fun buildDatabase(context: Context): RecipesDatabase {
            val dataBase =
                Room.databaseBuilder(
                    context = context,
                    klass = RecipesDatabase::class.java,
                    name = "recipes_database"
                )
                    .fallbackToDestructiveMigration(true)
                    .build()
            return dataBase
        }
    }
}