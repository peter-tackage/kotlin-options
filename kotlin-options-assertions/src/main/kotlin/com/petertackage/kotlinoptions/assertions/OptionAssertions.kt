package com.petertackage.kotlinoptions.assertions

import com.petertackage.kotlinoptions.Option

fun <T : Any> assertThat(actual: Option<T>): OptionAssertions<T> {
    return OptionAssertions(actual)
}

class OptionAssertions<T : Any> internal constructor(private val actual: Option<T>) {

    fun isSome(): OptionAssertions<T> {
        assert(actual.isSome(), { "Expected: <Some> but was: <None>" })
        return this
    }

    fun isNone(): OptionAssertions<T> {
        assert(actual.isNone(), { "Expected: <None> but was: <Some>" })
        return this
    }

    fun hasValue(expected: T): OptionAssertions<T> {
        isSome()
        val actualValue = actual.getUnsafe()
        assert(actualValue == (expected), { " " })
        return this
    }

    fun hasValue(predicate: (T) -> Boolean): OptionAssertions<T> {
        isSome()
        val actualValue = actual.getUnsafe()
        assert(predicate(actualValue), { "Expected predicate did not match: <$actualValue>" })
        return this
    }
}
