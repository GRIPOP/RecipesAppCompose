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
import ru.gmpopov.recipeapp.data.model.CategoryDto
import java.net.HttpURLConnection
import java.net.URL
import kotlinx.serialization.json.Json
import ru.gmpopov.recipeapp.data.model.RecipeDto
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)
    private val threadPool: ExecutorService = Executors.newFixedThreadPool(10)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(
            "MainActivityThread",
            "Метод onCreate выполняется на потоке: ${Thread.currentThread().name}"
        )

        threadPool.execute {
            var connection: HttpURLConnection? = null

            try {
                val categoryUrl = URL("https://recipes.androidsprint.ru/api/category")
                connection = categoryUrl.openConnection() as? HttpURLConnection ?: run {
                    Log.e("network", "Ошибка: openConnection вернула не HttpURLConnection")
                    return@execute
                }
                connection.connect()

                val code = connection.responseCode
                val message = connection.responseMessage

                Log.d(
                    "api_response",
                    "Code: $code, Message: $message"
                )

                Log.d(
                    "NetworkThread",
                    "Выполняю запрос на потоке: ${Thread.currentThread().name}"
                )

                val categoryBody = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("api_response", "Body: $categoryBody")

                val deserializedCategory = Json.decodeFromString<List<CategoryDto>>(categoryBody)

                deserializedCategory.forEach { category ->
                    threadPool.execute {
                        var connection: HttpURLConnection? = null

                        try {
                            val recipeUrl =
                                URL("https://recipes.androidsprint.ru/api/category/${category.id}/recipes")

                            connection = recipeUrl.openConnection() as? HttpURLConnection ?: run {
                                Log.e(
                                    "network",
                                    "Ошибка: openConnection вернула не HttpURLConnection"
                                )
                                return@execute
                            }
                            connection.connect()

                            val recipeBody =
                                connection.inputStream.bufferedReader().use { it.readText() }

                            val deserializedRecipe =
                                Json.decodeFromString<List<RecipeDto>>(recipeBody)
                            Log.d(
                                "response_recipes", "" +
                                        "Имя потока:${Thread.currentThread().name}, " +
                                        "Категория:${category.title}, " +
                                        "Всего рецептов:${deserializedRecipe.size}"
                            )
                        } catch (e: Exception) {
                            Log.e("network", "Ошибка: ${e.message}")
                        } finally {
                            connection?.disconnect()
                        }
                    }
                }
                Log.d(
                    "Deserialized_body",
                    "Всего категорий: " +
                            "${deserializedCategory.size} ${deserializedCategory.map { it.title }}"
                )

            } catch (e: Exception) {
                Log.e("network", "Ошибка: ${e.message}")
            } finally {
                connection?.disconnect()
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

    override fun onDestroy() {
        super.onDestroy()
        threadPool.shutdown()
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}
