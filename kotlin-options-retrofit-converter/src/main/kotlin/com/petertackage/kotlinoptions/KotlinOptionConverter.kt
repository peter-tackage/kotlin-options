package com.petertackage.kotlinoptions

import okhttp3.ResponseBody
import retrofit2.Converter

class KotlinOptionConverter<T : Any>(private val delegate: Converter<ResponseBody, T>) :
        Converter<ResponseBody, Option<T>> {
    override fun convert(value: ResponseBody?): Option<T> {
        return optionOf(delegate.convert(value))
    }
}