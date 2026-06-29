package ru.gmpopov.recipeapp.di

import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.features.categories.presentation.CategoriesViewModel

class CategoriesViewModelFactory(val repository: RecipesRepository) : Factory<CategoriesViewModel> {
    override fun create(): CategoriesViewModel = CategoriesViewModel(repository)
}