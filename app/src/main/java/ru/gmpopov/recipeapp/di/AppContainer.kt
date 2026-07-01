package ru.gmpopov.recipeapp.di

import android.content.Context
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.gmpopov.recipeapp.BuildConfig
import ru.gmpopov.recipeapp.core.network.NetworkConfig
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.repository.RecipesRepository
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryImpl
import java.util.concurrent.TimeUnit

class AppContainer(context: Context) {
    val contentType: MediaType = "application/json".toMediaType()

    val okHttpClient = OkHttpClient.Builder()
        .connectTimeout(15, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .writeTimeout(30, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
            level = if (BuildConfig.DEBUG) {
                HttpLoggingInterceptor.Level.BODY
            } else {
                HttpLoggingInterceptor.Level.NONE
            }
        })
        .build()

    val json = Json {
        ignoreUnknownKeys = true
        coerceInputValues = true
    }

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(NetworkConfig.BASE_URL)
        .addConverterFactory(json.asConverterFactory(contentType))
        .client(okHttpClient)
        .build()

    val recipeApi = retrofit.create(RecipesApiService::class.java)

    val recipesDatabase = RecipesDatabase.buildDatabase(context)

    val recipesRepository = RecipesRepositoryImpl(recipeApi, recipesDatabase)
}