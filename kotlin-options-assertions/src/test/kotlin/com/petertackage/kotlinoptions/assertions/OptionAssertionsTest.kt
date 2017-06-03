package com.petertackage.kotlinoptions.assertions

import com.petertackage.kotlinoptions.Option
import com.petertackage.kotlinoptions.Option.Companion.optionOf
import org.junit.Test
import kotlin.test.assertFailsWith

class OptionAssertionsTest {

    @Test
    fun `isSome passes when isSome`() {
        val some = optionOf("abc")

        assertThat(some).isSome()
    }

    @Test
    fun `isSome fails when isNone`() {
        assertFailsWith(AssertionError::class,
                message = "Expected: <Some> but was: <None>") {
            assertThat(Option.None).isSome()
        }
    }

    @Test
    fun `isNone passes when isNone`() {
        assertThat(Option.None).isNone()
    }

    @Test
    fun `isNone fails when isSome`() {
        val some = optionOf("abc")

        assertFailsWith(AssertionError::class,
                message = "Expected: <None> but was: <Some>") {
            assertThat(some).isNone()
        }
    }

    @Test
    fun `hasValue passes when value equals`() {
        val some = optionOf("abc")

        assertThat(some).hasValue("abc")
    }

    @Test
    fun `hasValue fails when value not equals`() {
        val some = optionOf("abc")

        assertFailsWith(AssertionError::class,
                message = "Expected value: <abc> but was: <xyz>") {
            assertThat(some).hasValue("xyz")
        }
    }

    @Test
    fun `hasValue fails when isNone`() {
        val none = optionOf(null as String?)

        assertFailsWith(AssertionError::class,
                message = "Expected: <Some> but was: <None>") {
            assertThat(none).hasValue("does not matter")
        }
    }

    @Test
    fun `hasValue passes when value matches predicate`() {
        val some = optionOf("abc")
        val predicate: (String) -> Boolean = { true }

        assertThat(some).hasValue(predicate)
    }

    @Test
    fun `hasValue fails when value does not match predicate`() {
        val some = optionOf("abc")
        val predicate: (String) -> Boolean = { false }

        assertFailsWith(AssertionError::class,
                message = "Expected predicate did not match: <abc>)") {
            assertThat(some).hasValue(predicate)
        }
    }

    @Test
    fun `hasValue with predicate fails when isNone`() {
        val none = optionOf(null as String?)
        val predicate: (String) -> Boolean = { it.isEmpty() }

        assertFailsWith(AssertionError::class,
                message = "Expected: <Some> but was: <None>") {
            assertThat(none).hasValue(predicate)
        }
    }
}