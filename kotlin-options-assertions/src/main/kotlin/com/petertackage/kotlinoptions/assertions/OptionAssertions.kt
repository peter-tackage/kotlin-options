package com.petertackage.kotlinoptions.assertions

import com.petertackage.kotlinoptions.None
import com.petertackage.kotlinoptions.Option
import com.petertackage.kotlinoptions.Some

/**
 * Creates a new instance of [OptionAssertions] to allow asserting on the properties of the instance under test: [actual].
 */
fun <T : Any> assertThat(actual: Option<T>): OptionAssertions<T> {
    return OptionAssertions(actual)
}

/**
 * Asserts that the instance under test is [Some].
 */
fun <T : Any> Option<T>.assertIsSome(): Option<T> {
    return apply { assertThat(this).isSome() }
}

/**
 * Asserts that the instance under test is [None].
 */
fun <T : Any> Option<T>.assertIsNone(): Option<T> {
    return apply { assertThat(this).isNone() }
}

/**
 * Asserts that the instance under test is [Some] and has the value [expected].
 */
fun <T : Any> Option<T>.assertHasValue(expected: T): Option<T> {
    return apply { assertThat(this).hasValue(expected) }
}
/**
 * Asserts that the instance under test is [Some] and has the value returned by function [expectedPredicate].
 */
fun <T : Any> Option<T>.assertHasValue(expectedPredicate: (T) -> Boolean): Option<T> {
    return apply { assertThat(this).hasValue(expectedPredicate) }
}

class OptionAssertions<T : Any> internal constructor(private val actual: Option<T>) {

    /**
     * Asserts that the instance under test is [Some].
     */
    fun isSome(): OptionAssertions<T> {
        return apply {
            assert(actual is Some,
                    { "Expected: <Some> but was: <None>" })
        }
    }

    /**
     * Asserts that the instance under test is [None].
     */
    fun isNone(): OptionAssertions<T> {
        return apply {
            assert(actual is None,
                    { "Expected: <None> but was: <Some>" })
        }
    }
    /**
     * Asserts that the instance under test is [Some] and has the value [expected].
     */
    fun hasValue(expected: T): OptionAssertions<T> {
        isSome()
        val actualValue: T = actual.getUnsafe()
        return apply { assert(expected == actualValue, { "Expected value: <$expected> but was: <$actualValue>" }) }
    }
    /**
     * Asserts that the instance under test is [Some] and has the value returned by function [expectedPredicate].
     */
    fun hasValue(expectedPredicate: (T) -> Boolean): OptionAssertions<T> {
        isSome()
        val actualValue = actual.getUnsafe()
        return apply { assert(expectedPredicate(actualValue), { "Expected predicate did not match: <$actualValue>" }) }
    }
}
