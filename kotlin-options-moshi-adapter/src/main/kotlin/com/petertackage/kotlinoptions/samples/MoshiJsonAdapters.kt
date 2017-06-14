package com.petertackage.kotlinoptions.samples

import com.petertackage.kotlinoptions.Option
import com.petertackage.kotlinoptions.optionOf
import com.squareup.moshi.*
import java.lang.reflect.ParameterizedType
import java.lang.reflect.Type

private class OptionAdapter<T : Any>(private val adapter: JsonAdapter<T>) : JsonAdapter<Option<T>>() {

    override fun toJson(writer: JsonWriter?, value: Option<T>?) {
        // Write null when None
        adapter.toJson(writer, value?.toNullable())
    }

    override fun fromJson(reader: JsonReader): Option<T> {
        // Delegate to the parameterized type's adapter
        return optionOf(adapter.fromJson(reader))
    }
}

class OptionAdapterFactory : JsonAdapter.Factory {

    override fun create(type: Type, annotations: MutableSet<out Annotation>, moshi: Moshi): JsonAdapter<*>? {
        val rawType = Types.getRawType(type)
        if (rawType == Option::class.java && type is ParameterizedType) {
            val subType = type.actualTypeArguments.first()
            val adapter: JsonAdapter<Any> = moshi.adapter(subType)
            return OptionAdapter(adapter)
        }
        return null
    }

}

