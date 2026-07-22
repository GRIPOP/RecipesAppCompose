package ru.gmpopov.recipeapp.core.network.api


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.test.runTest
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit

@RunWith(AndroidJUnit4::class)
class RecipesApiMockWebServerTest {

    private lateinit var mockWebServer: MockWebServer

    @Before
    fun setup() {
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        mockWebServer.shutdown()
    }

    @Test
    fun categoriesAreCorrectlyDeserializedFromJson() = runTest {

        val json =
            """[{"id":1,"title":"Завтраки","description":"Лёгкие блюда","imageUrl":"breakfast.jpg"}]"""
        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(
                Json.asConverterFactory("application/json".toMediaType())
            )
            .build()
            .create(RecipesApiService::class.java)

        val categories = apiService.getCategories()
        assertEquals(1, categories.size)
        assertEquals("Завтраки", categories.first().title)
    }
}
