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
        val thread = Thread {
            var connection: HttpURLConnection? = null

            try {
                val url = URL("https://recipes.androidsprint.ru/api/category")
                connection = url.openConnection() as? HttpURLConnection ?: run {
                    Log.e("network", "Ошибка: openConnection вернула не HttpURLConnection")
                    return@Thread
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

                val body = connection.inputStream.bufferedReader().use { it.readText() }
                Log.d("api_response", "Body: $body")

                val deserializedBody = Json.decodeFromString<List<CategoryDto>>(body)
                Log.d(
                    "Deserialized_body",
                    "Всего категорий: ${deserializedBody.size} ${deserializedBody.map { it.title }}"
                )
            } catch (e: Exception) {
                Log.e("network", "Ошибка: ${e.message}")
            } finally {
                connection?.disconnect()
            }
        }
        thread.start()

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
