package ru.gmpopov.recipeapp.di

import android.app.Application
import ru.gmpopov.recipeapp.features.favorites.presentation.FavoritesViewModel

class FavoritesViewModelFactory(
    val application: Application,
): Factory<FavoritesViewModel> {
    override fun create(): FavoritesViewModel = FavoritesViewModel(application)
}