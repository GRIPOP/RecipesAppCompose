package ru.gmpopov.recipeapp

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.lifecycleScope
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import kotlinx.serialization.json.Json
import okhttp3.MediaType
import retrofit2.Retrofit
import ru.gmpopov.recipeapp.core.network.NetworkConfig
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import okhttp3.MediaType.Companion.toMediaType
import ru.gmpopov.recipeapp.core.network.api.RecipesApiService

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(
            "MainActivityThread",
            "Метод onCreate выполняется на потоке: ${Thread.currentThread().name}"
        )
        val contentType: MediaType = "application/json".toMediaType()
        val json = Json {
            coerceInputValues = true
            ignoreUnknownKeys = true
        }

        val retrofit: Retrofit = Retrofit.Builder()
            .baseUrl(NetworkConfig.BASE_URL)
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

        val apiService = retrofit.create(RecipesApiService::class.java)

        lifecycleScope.launch {
            try {
                val categories = apiService.getCategories()
                categories.forEach { category ->
                    lifecycleScope.launch {
                        try {
                            val recipes = apiService.getRecipesByCategory(category.id)

                            Log.d(
                                "response_recipes", "" +
                                        "Имя потока:${Thread.currentThread().name}, " +
                                        "Категория:${category.title}, " +
                                        "Всего рецептов:${recipes.size}"
                            )
                        } catch (e: Exception) {
                            Log.e("network", "Ошибка: ${e.message}")
                        }
                    }
                }

                Log.d(
                    "Deserialized_body",
                    "Всего категорий: " +
                            "${categories.size} ${categories.map { it.title }}"
                )
            } catch (e: Exception) {
                Log.e("network", "Ошибка: ${e.message}")
            }
        }

        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        intent.data?.let {
            deepLinkIntent = intent
        }
        setContent {
            RecipesApp(deepLinkIntent = deepLinkIntent)
        }
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
