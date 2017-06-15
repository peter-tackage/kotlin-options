package com.petertackage.kotlinoption

import com.petertackage.kotlinoptions.None
import com.petertackage.kotlinoptions.Option
import com.petertackage.kotlinoptions.optionOf
import com.petertackage.kotlinoptions.samples.OptionAdapterFactory
import com.squareup.moshi.KotlinJsonAdapterFactory
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import org.junit.Before
import org.junit.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class MoshiJsonAdaptersTest {

    private lateinit var moshi: Moshi

    data class Data(val optionField: Option<Int>)

    data class DataWithDefault(val optionField: Option<Int> = None)

    @Before
    fun setUp() {
        // Read about Kotlin handling: https://github.com/square/moshi/issues/143
        moshi = Moshi.Builder()
                .add(OptionAdapterFactory()) // must be first!
                .add(KotlinJsonAdapterFactory()) // needed to enforce missing fields as required
                .build()
    }

    @Test
    fun `deserializes present field as optionOf value`() {
        val jsonAdapter = moshi.adapter(Data::class.java)
        val json = """ { "optionField" : 1 } """

        val result = jsonAdapter.fromJson(json)!!

        assertEquals(optionOf(1), result.optionField)
    }

    @Test
    fun `deserializes null field as None`() {
        val jsonAdapter = moshi.adapter(Data::class.java)
        val json = """ { "optionField" : null } """

        val result = jsonAdapter.fromJson(json)!!

        assertEquals(None, result.optionField)
    }

    @Test
    fun `deserializes missing field as None when default value`() {
        val jsonAdapter = moshi.adapter(DataWithDefault::class.java)
        val json = """ { } """

        val result = jsonAdapter.fromJson(json)!!

        assertEquals(None, result.optionField)
    }

    @Test
    fun `factory returns non-null instance when parameterized Option type`() {
        val adapterFactory = OptionAdapterFactory()
        val type = Types.newParameterizedType(Option::class.java, Any::class.java)

        val factory = adapterFactory.create(type, mutableSetOf(), moshi)

        assertNotNull(factory)
    }

    @Test
    fun `factory returns null instance when someother type`() {
        val adapterFactory = OptionAdapterFactory()

        val factory = adapterFactory.create(String::class.java, mutableSetOf(), moshi)

        assertNull(factory)
    }

}
