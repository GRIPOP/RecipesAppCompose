package ru.gmpopov.recipeapp.core.network.api

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import app.cash.turbine.test
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import retrofit2.Retrofit
import retrofit2.converter.kotlinx.serialization.asConverterFactory
import ru.gmpopov.recipeapp.data.database.RecipesDatabase
import ru.gmpopov.recipeapp.data.repository.RecipesRepositoryImpl

@RunWith(AndroidJUnit4::class)
class CompleteDataFlowTest {

    private lateinit var mockWebServer: MockWebServer
    private lateinit var database: RecipesDatabase

    @Before
    fun setup() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        database = Room.inMemoryDatabaseBuilder(context, RecipesDatabase::class.java).build()
        mockWebServer = MockWebServer()
        mockWebServer.start()
    }

    @After
    fun tearDown() {
        database.close()
        mockWebServer.shutdown()

    }

    @Test
    fun categoriesAreLoadedFromApiAndStoredInCache() = runTest {

        val json =
            """[{"id":1,"title":"Завтраки","description":"Лёгкие блюда","imageUrl":"breakfast.jpg"}]"""

        mockWebServer.enqueue(MockResponse().setBody(json).setResponseCode(200))

        val apiService = Retrofit.Builder()
            .baseUrl(mockWebServer.url("/"))
            .addConverterFactory(Json.asConverterFactory("application/json".toMediaType()))
            .build()
            .create(RecipesApiService::class.java)

        val repository = RecipesRepositoryImpl(apiService, database)

        repository.getCategories().test {
            assertTrue(awaitItem().isEmpty())

            val loaded = awaitItem()
            assertEquals(1, loaded.size)

            val cached = database.categoryDao().getAllCategories().first()
            assertEquals(1, cached.size)
            assertEquals("Завтраки", cached[0].name)

            cancelAndIgnoreRemainingEvents()
        }
    }
}
