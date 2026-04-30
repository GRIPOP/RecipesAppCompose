package ru.gmpopov.recipeapp.util

import android.content.Context
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map

class FavoriteDataStoreManager(private val context: Context) {

    val preferencesFlow: Flow<Preferences> = context.dataStore.data

    fun getFavoriteIdsFlow(): Flow<Set<String>> {
        return preferencesFlow.map { preferences ->
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        }
    }

    fun isFavoriteFlow(recipeId: Int): Flow<Boolean> {
        return getFavoriteIdsFlow().map { favoriteIds ->
            favoriteIds.contains(recipeId.toString())
        }
    }

    fun getFavoriteCountFlow(): Flow<Int> {
        return getFavoriteIdsFlow().map {
            it.size
        }
    }

    suspend fun isFavorite(recipeId: Int): Boolean {
        val preferences = context.dataStore.data.first()
        val favoritesId = preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
        return favoritesId.contains(recipeId.toString())
    }

    suspend fun addFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavoriteRecipes =
                preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] =
                currentFavoriteRecipes + recipeId.toString()
        }
    }

    suspend fun removeFavorite(recipeId: Int) {
        context.dataStore.edit { preferences ->
            val currentFavoriteRecipes =
                preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] ?: emptySet()
            preferences[PreferencesKeys.FAVORITE_RECIPE_IDS] =
                currentFavoriteRecipes - recipeId.toString()
        }
    }
}