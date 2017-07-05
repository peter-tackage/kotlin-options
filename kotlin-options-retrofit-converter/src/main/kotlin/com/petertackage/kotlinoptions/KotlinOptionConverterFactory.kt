package com.petertackage.kotlinoptions

import okhttp3.ResponseBody
import retrofit2.Converter
import retrofit2.Retrofit
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

class KotlinOptionConverterFactory : Converter.Factory() {

    companion object {
        fun create(): KotlinOptionConverterFactory {
            return KotlinOptionConverterFactory()
        }
    }

    override fun responseBodyConverter(type: Type,
                                       annotations: Array<Annotation>,
                                       retrofit: Retrofit): Converter<ResponseBody, *>? {
        if (Converter.Factory.getRawType(type) != Option::class.java) {
            return null
        }

        val innerType = Converter.Factory.getParameterUpperBound(0, type as ParameterizedType)
        val delegate: Converter<ResponseBody, Any> = retrofit.nextResponseBodyConverter(this, innerType, annotations)
        return KotlinOptionConverter(delegate)
    }
}