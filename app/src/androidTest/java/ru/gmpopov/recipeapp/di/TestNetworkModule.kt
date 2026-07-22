package ru.gmpopov.recipeapp.di

import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import javax.inject.Singleton

@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class],
)

@Module
object TestNetworkModule {

    @Provides
    @Singleton
    fun provideApiService(): RecipesApiService {
        return mockk<RecipesApiService>()
    }

    @Provides
    @Singleton
    fun provideDatabase(): RecipesDatabase {
        return mockk<RecipesDatabase>()
    }
}