package ru.gmpopov.recipeapp.util

import android.content.Context
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.first

class FavoriteDataStoreManager(private val context: Context) {

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = context.dataStore.data.first()
        val favoritesId = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoritesId.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavoriteRecipes = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS]  = currentFavoriteRecipes + recipeId.toString()
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavoriteRecipes = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] = currentFavoriteRecipes - recipeId.toString()
        }
    }
}