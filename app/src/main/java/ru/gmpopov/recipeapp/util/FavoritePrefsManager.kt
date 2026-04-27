package ru.gmpopov.recipeapp.util

import android.content.Context
import android.content.SharedPreferences
import ru.gmpopov.recipeapp.KEY_FAVORITE_RECIPE_IDS
import androidx.core.content.edit
import ru.gmpopov.recipeapp.PREFS_FILE_NAME

class FavoritePrefsManager(context: Context) {
    private val sharedPreferences: SharedPreferences =
        context.getSharedPreferences(PREFS_FILE_NAME, Context.MODE_PRIVATE)

    fun isFavorite(recipeId: Int): Boolean {
        val currentFavorites = sharedPreferences.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet())
        return currentFavorites?.contains(recipeId.toString()) ?: false
    }

    fun addFavorites(recipeId: Int) {
        val currentFavorites =
            sharedPreferences.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet()) ?: emptySet()
        val updateFavorites = currentFavorites.toMutableSet()
        updateFavorites.add(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(KEY_FAVORITE_RECIPE_IDS, updateFavorites)
        }
    }

    fun removeFromFavorites(recipeId: Int) {
        val currentFavorites =
            sharedPreferences.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet()) ?: emptySet()
        val updateFavorites = currentFavorites.toMutableSet()
        updateFavorites.remove(recipeId.toString())

        sharedPreferences.edit {
            putStringSet(KEY_FAVORITE_RECIPE_IDS, updateFavorites)
        }
    }

    fun getAllFavorites(): Set<String> {
        return sharedPreferences.getStringSet(KEY_FAVORITE_RECIPE_IDS, emptySet()) ?: emptySet()
    }
}