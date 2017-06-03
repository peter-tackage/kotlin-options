package com.petertackage.kotlinoptions.assertions

import com.petertackage.kotlinoptions.Option

fun <T : Any> assertThat(actual: Option<T>): OptionAssertions<T> {
    return OptionAssertions(actual)
}

fun <T : Any> Option<T>.assertIsSome(): Option<T> {
    return apply { assertThat(this).isSome() }
}

fun <T : Any> Option<T>.assertIsNone(): Option<T> {
    return apply { assertThat(this).isNone() }
}

fun <T : Any> Option<T>.assertHasValue(expected: T): Option<T> {
    return apply { assertThat(this).hasValue(expected) }
}

fun <T : Any> Option<T>.assertHasValue(predicate: (T) -> Boolean): Option<T> {
    return apply { assertThat(this).hasValue(predicate) }
}

class OptionAssertions<T : Any> internal constructor(private val actual: Option<T>) {

    fun isSome(): OptionAssertions<T> {
        return apply {
            assert(actual.isSome(),
                    { "Expected: <Some> but was: <None>" })
        }
    }

    fun isNone(): OptionAssertions<T> {
        return apply {
            assert(actual.isNone(),
                    { "Expected: <None> but was: <Some>" })
        }
    }

    fun hasValue(expected: T): OptionAssertions<T> {
        isSome()
        val actualValue: T = actual.getUnsafe()
        return apply { assert(expected == actualValue, { "Expected value: <$expected> but was: <$actualValue>" }) }
    }

    fun hasValue(predicate: (T) -> Boolean): OptionAssertions<T> {
        isSome()
        val actualValue = actual.getUnsafe()
        return apply { assert(predicate(actualValue), { "Expected predicate did not match: <$actualValue>" }) }
    }
}
