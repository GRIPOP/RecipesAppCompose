package ru.gmpopov.recipeapp.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryImpl


@Module
@InstallIn(SingletonComponent::class)

abstract class RepositoryModule {
    @Binds
    abstract fun bindRepository(recipesRepositoryImpl: RecipesRepositoryImpl): RecipesRepository
}