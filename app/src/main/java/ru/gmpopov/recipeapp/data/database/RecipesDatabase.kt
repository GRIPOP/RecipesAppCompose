package ru.gmpopov.recipeapp.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import ru.gmpopov.recipeapp.data.database.dao.CategoryDao
import ru.gmpopov.recipeapp.data.database.entity.CategoryEntity

@Database(
    entities = [CategoryEntity::class],
    version = 1,
    exportSchema = false,
)
abstract class RecipesDatabase : RoomDatabase() {
    abstract fun categoryDao(): CategoryDao

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