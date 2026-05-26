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
import kotlinx.serialization.decodeFromString

class MainActivity : ComponentActivity() {

    private var deepLinkIntent by mutableStateOf<Intent?>(null)

    override fun onCreate(savedInstanceState: Bundle?) {
        Log.d(
            "MainActivityThread",
            "Метод onCreate выполняется на потоке: ${Thread.currentThread().name}"
        )
        val thread = Thread {
            val url = URL("https://recipes.androidsprint.ru/api/category")
            val connection = url.openConnection() as HttpURLConnection
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

            val body = connection.inputStream.bufferedReader().readText()
            Log.d("api_response", "Body: $body")

            val deserializedBody = Json.decodeFromString<List<CategoryDto>>(body)
            Log.d(
                "Deserialized_body",
                "Всего категорий: ${deserializedBody.size} ${deserializedBody.map { it.title }}"
            )
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

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        intent.data?.let {
            deepLinkIntent = intent
        }
        setIntent(intent)
    }
}