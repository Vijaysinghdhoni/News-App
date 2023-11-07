package com.vijaydhoni.newsapp.data.api

import com.google.common.truth.Truth.assertThat
import kotlinx.coroutines.runBlocking
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import okio.buffer
import okio.source
import org.junit.After
import org.junit.Before
import org.junit.Test
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class NewsAPiServiceTest {

    private lateinit var service: NewsAPiService
    private lateinit var server: MockWebServer


    @Before
    fun setUp() {
        server = MockWebServer()

        service = Retrofit.Builder().baseUrl(server.url(""))
            .addConverterFactory(GsonConverterFactory.create()).build()
            .create(NewsAPiService::class.java)


    }

    @After
    fun tearDown() {
        server.shutdown()
    }

    @Test
    fun getTopHeadlines_sentRquest_RecivedExcepted() {

        runBlocking {
            enqueueMockresponse("newResponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val request = server.takeRequest()

            assertThat(responseBody).isNotNull()

            assertThat(request.path).isEqualTo("/v2/top-headlines?country=us&page=1&apiKey=c884f597677b46bea7a7018cd6c02061")

        }
    }

    @Test
    fun getTopHeadlines_recivedResponse_correctPageSize() {
        runBlocking {
            enqueueMockresponse("newResponse.json")
            val responseBody = service.getTopHeadlines("us", 1).body()
            val articalList = responseBody!!.articles

            assertThat(articalList.size).isEqualTo(20)

        }
    }


    private fun enqueueMockresponse(
        fileName: String
    ) {
        val inputStream = javaClass.classLoader!!.getResourceAsStream(fileName)
        val source = inputStream.source().buffer()
        val mockResponse = MockResponse()

        mockResponse.setBody(source.readString(Charsets.UTF_8))
        server.enqueue(mockResponse)
    }
}