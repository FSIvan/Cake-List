package com.ivanwooll.cakelist

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.lifecycle.Observer
import androidx.room.Room
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.google.gson.Gson
import com.ivanwooll.cakelist.data.ApiResponse
import com.ivanwooll.cakelist.data.CakeRepository
import com.ivanwooll.cakelist.data.api.ApiService
import com.ivanwooll.cakelist.data.api.models.ApiCake
import com.ivanwooll.cakelist.data.room.AppDatabase
import com.ivanwooll.cakelist.data.room.Cake
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.times
import com.nhaarman.mockito_kotlin.verify
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.ResponseBody
import okhttp3.ResponseBody.Companion.toResponseBody
import org.junit.*
import org.junit.runner.RunWith
import org.mockito.Mockito.`when`
import retrofit2.Call
import retrofit2.Response

@RunWith(AndroidJUnit4::class)
class CakeRepositoryTest {

    @get:Rule
    var instantTaskExecutorRule = InstantTaskExecutorRule()

    private lateinit var appDatabase: AppDatabase

    lateinit var observer: Observer<List<Cake>>

    lateinit var cakeRepository: CakeRepository

    lateinit var apiService: ApiService

    val lemonCheescake =
        Cake(
            desc = "A cheesecake made of lemon",
            title = "Lemon Cheesecake",
            image = "https://s3-eu-west-1.amazonaws.com/s3.mediafileserver.co.uk/carnation/WebFiles/RecipeImages/lemoncheesecake_lg.jpg"
        )

    val victoriaSponge =
        Cake(
            desc = "sponge with jam",
            title = "victoria sponge",
            image = "https://upload.wikimedia.org/wikipedia/commons/0/05/111rfyh.jpg"
        )

    val carrotCake =
        Cake(
            desc = "Bugs Bunnys favourite",
            title = "Carrot cake",
            image = "https://hips.hearstapps.com/del.h-cdn.co/assets/18/08/1519321610-carrot-cake-vertical.jpg"
        )

    val banannaCake =
        Cake(
            desc = "Donkey kongs favourite",
            title = "Banana Cake",
            image = "http://ukcdn.ar-cdn.com/recipes/xlarge/ff22df7f-dbcd-4a09-81f7-9c1d8395d936.jpg"
        )

    val birthdayCake =
        Cake(
            desc = "A yearly treat",
            title = "Birthday Cake",
            image = "https://www.frenchvillagebakery.co.uk/databaseimages/prd_8594342__painted_pink_and_gold_cake_512x640.jpg"
        )

    @Before
    fun setup() {
        appDatabase = Room.inMemoryDatabaseBuilder(
            InstrumentationRegistry.getInstrumentation().context,
            AppDatabase::class.java
        ).build()
        observer = mock()
        apiService = mock()
        cakeRepository = CakeRepository(apiService, appDatabase)
        cakeRepository.cakesLiveData().observeForever(observer)
    }

    @Test
    fun testClearCakesTable() {
        appDatabase.cakeDao().insert(arrayListOf(Cake(desc = "sas", title = "", image = "")))
        cakeRepository.clearCakesTable()
        verify(observer, times(2)).onChanged(emptyList())
    }

    @Test
    fun testInsertCakes() {
        val cakes = arrayListOf(Cake(desc = "sas", title = "", image = ""))
        appDatabase.cakeDao().insert(cakes)
        verify(observer, times(1)).onChanged(cakes)
    }

    @Test
    fun testApiCallSuccessEmptyCakeList() {
        val mockCall = mock<Call<List<ApiCake>>> {
            on { execute() } doReturn Response.success(emptyList())
        }
        `when`(apiService.fetchCakes()).thenReturn(mockCall)

        val response = cakeRepository.fetchCakes()
        Assert.assertEquals(response, ApiResponse.Success<List<ApiCake>>(emptyList()))
        verify(observer, times(1)).onChanged(emptyList())
    }

    @Test
    fun testApiCallSuccessCakesReturned() {
        val cakes = listOf(lemonCheescake, banannaCake)
        val apiCakes = cakes.map { ApiCake(desc = it.desc, image = it.image, title = it.title) }

        val mockCall = mock<Call<List<ApiCake>>> {
            on { execute() } doReturn Response.success(apiCakes)
        }

        `when`(apiService.fetchCakes()).thenReturn(mockCall)

        val response = cakeRepository.fetchCakes()
        Assert.assertEquals(response, ApiResponse.Success(apiCakes))
    }

    @Test
    fun testApiCallFails() {
        val mockCall = mock<Call<List<ApiCake>>> {
            on { execute() } doReturn Response.error(
                400,
                "{}".toResponseBody("".toMediaTypeOrNull())
            )
        }
        `when`(apiService.fetchCakes()).thenReturn(mockCall)
        val response = cakeRepository.fetchCakes()
        Assert.assertTrue(response is ApiResponse.Error)
    }

    @After
    fun closeDb() {
        appDatabase.close()
    }

}