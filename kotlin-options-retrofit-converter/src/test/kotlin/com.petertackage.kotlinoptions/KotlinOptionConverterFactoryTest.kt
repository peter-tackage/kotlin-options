package com.petertackage.kotlinoptions

import okhttp3.ResponseBody
import okhttp3.mockwebserver.MockResponse
import okhttp3.mockwebserver.MockWebServer
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import retrofit2.Call
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.http.GET
import java.lang.reflect.Type
import kotlin.test.assertNotNull
import kotlin.test.assertNull

/**
 * Based upon https://github.com/square/retrofit/blob/master/retrofit-converters/java8/src/test/java/retrofit/converter/java8/Java8OptionalConverterFactoryTest.java
 */
class KotlinOptionConverterFactoryTest {

    lateinit private var service: KotlinOptionConverterFactoryTest.Service

    @Rule
    @JvmField
    val server = MockWebServer()

    interface Service {
        @GET("/") fun option(): Call<Option<Any>>
        @GET("/") fun `object`(): Call<Any>
    }

    @Before
    fun setUp() {
        val retrofit = Retrofit.Builder()
                .baseUrl(server.url("/"))
                .addConverterFactory(KotlinOptionConverterFactory.create())
                .addConverterFactory(AlwaysNullConverterFactory())
                .build()
        service = retrofit.create(Service::class.java)
    }

    @Test
    fun `returns None when Option and null body`() {
        server.enqueue(MockResponse())

        val option = service.option().execute().body()

        assertNotNull(option)
        assert(option == None)
    }

    @Test
    fun `returns null when non-Option and null body`() {
        server.enqueue(MockResponse())

        val body = service.`object`().execute().body()

        assertNull(body)
    }
}

class AlwaysNullConverterFactory : Converter.Factory() {
    override fun responseBodyConverter(type: Type, annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *>
            = Converter<ResponseBody, Any> { null }
}