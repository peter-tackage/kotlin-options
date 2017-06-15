package com.petertackage.kotlinoptions.assertions

import com.petertackage.kotlinoptions.None
import com.petertackage.kotlinoptions.Option
import com.petertackage.kotlinoptions.optionOf
import org.junit.Test
import kotlin.test.assertFailsWith

class OptionAssertionsTest {

    @Test
    fun `isSome passes when Some`() {
        val some = optionOf("abc")

        assertThat(some).isSome()
    }

    @Test
    fun `isSome fails when None`() {
        val actualException = assertFailsWith(AssertionError::class) {
            assertThat(None).isSome()
        }
        assert(actualException.message == "Expected: <Some> but was: <None>")
    }

    @Test
    fun `isNone passes when None`() {
        assertThat(None).isNone()
    }

    @Test
    fun `isNone fails when Some`() {
        val some = optionOf("abc")

        val actualException = assertFailsWith(AssertionError::class) {
            assertThat(some).isNone()
        }
        assert(actualException.message == "Expected: <None> but was: <Some>")
    }

    @Test
    fun `hasValue passes when value equals`() {
        val some = optionOf("abc")

        assertThat(some).hasValue("abc")
    }

    @Test
    fun `hasValue fails when value not equals`() {
        val some = optionOf("abc")

        val actualException = assertFailsWith(AssertionError::class) {
            assertThat(some).hasValue("xyz")
        }
        assert(actualException.message == "Expected value: <xyz> but was: <abc>")
    }

    @Test
    fun `hasValue fails when None`() {
        val none: Option<*> = None

        val actualException = assertFailsWith(AssertionError::class) {
            assertThat(none).hasValue("does not matter")
        }
        assert(actualException.message == "Expected: <Some> but was: <None>")
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

        val actualException = assertFailsWith(AssertionError::class) {
            assertThat(some).hasValue(predicate)
        }
        assert(actualException.message == "Expected predicate did not match: <abc>")
    }

    @Test
    fun `hasValue with predicate fails when None`() {
        val none: Option<*> = None
        val predicate: (String) -> Boolean = { true }

        val actualException = assertFailsWith(AssertionError::class) {
            assertThat(none).hasValue(predicate)
        }
        assert(actualException.message == "Expected: <Some> but was: <None>")
    }

    /*
     * Tests for Extension methods
     */

    @Test
    fun `assertIsSome passes when Some`() {
        val some = optionOf("abc")

        some.assertIsSome()
    }

    @Test
    fun `assertIsSome fails when None`() {
        val actualException = assertFailsWith(AssertionError::class) {
            None.assertIsSome()
        }
        assert(actualException.message == "Expected: <Some> but was: <None>")
    }

    @Test
    fun `assertIsNone passes when None`() {
        None.assertIsNone()
    }

    @Test
    fun `assertIsNone fails when Some`() {
        val some = optionOf("abc")

        val actualException = assertFailsWith(AssertionError::class) {
            some.assertIsNone()
        }
        assert(actualException.message == "Expected: <None> but was: <Some>")
    }

    @Test
    fun `assertHasValue passes when value equals`() {
        val some = optionOf("abc")

        some.assertHasValue("abc")
    }

    @Test
    fun `assertHasValue fails when value not equals`() {
        val some = optionOf("abc")

        val actualException = assertFailsWith(AssertionError::class) {
            some.assertHasValue("xyz")
        }
        assert(actualException.message == "Expected value: <xyz> but was: <abc>")
    }

    @Test
    fun `assertHasValue fails when None`() {
        val none: Option<*> = None

        val actualException = assertFailsWith(AssertionError::class) {
            none.assertHasValue("does not matter")
        }
        assert(actualException.message == "Expected: <Some> but was: <None>")
    }

    @Test
    fun `assertHasValue passes when value matches predicate`() {
        val some = optionOf("abc")
        val predicate: (String) -> Boolean = { true }

        some.assertHasValue(predicate)
    }

    @Test
    fun `assertHasValue fails when value does not match predicate`() {
        val some = optionOf("abc")
        val predicate: (String) -> Boolean = { false }

        val actualException = assertFailsWith(AssertionError::class) {
            some.assertHasValue(predicate)
        }
        assert(actualException.message == "Expected predicate did not match: <abc>")
    }

    @Test
    fun `assertHasValue with predicate fails when None`() {
        val none: Option<*> = None
        val predicate: (String) -> Boolean = { true }

        val actualException = assertFailsWith(AssertionError::class) {
            none.assertHasValue(predicate)
        }
        assert(actualException.message == "Expected: <Some> but was: <None>")
    }
}
