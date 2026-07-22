package ru.gmpopov.recipeapp.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import io.mockk.mockk
import org.junit.Test
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
    fun provideDatabase(@ApplicationContext context: Context): RecipesDatabase {
        return Room.inMemoryDatabaseBuilder(
            context = context,
            RecipesDatabase::class.java
        ).build()
    }
}