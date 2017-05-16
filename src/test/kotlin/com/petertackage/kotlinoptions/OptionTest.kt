package com.petertackage.kotlinoptions

import com.nhaarman.mockito_kotlin.*
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class OptionTest {

    @Rule
    @JvmField
    var thrown = ExpectedException.none()

    @Test
    fun `none is single instance`() {
        assertThat(Option.None).isSameAs(Option.None)
    }

    @Test
    fun `isSome returns true when non-null value`() {
        assertThat(Option.optionOf("value").isSome()).isTrue()
    }

    @Test
    fun `isSome returns false when null value`() {
        assertThat(Option.optionOf(null).isSome()).isFalse()
    }

    @Test
    fun `isNone returns true when null value`() {
        assertThat(Option.optionOf(null).isNone()).isTrue()
    }

    @Test
    fun `isNone returns false when non-null value`() {
        assertThat(Option.optionOf("value").isNone()).isFalse()
    }

    @Test
    fun `ifSome performs action with value when Some`() {
        val value = "value"
        val action: (String) -> Unit = mock()

        Option.optionOf(value).ifSome(action)

        verify(action).invoke(value)
    }

    @Test
    fun `ifSome does not perform action with value when None`() {
        val action: (String) -> Unit = mock()

        Option.optionOf(null).ifSome(action)

        verify(action, never()).invoke(any())
    }

    @Test
    fun `ifNone performs action when None`() {
        val action: () -> Unit = mock()

        Option.optionOf(null).ifNone(action)

        verify(action).invoke()
    }

    @Test
    fun `ifNone does not perform action when Some`() {
        val action: () -> Unit = mock()

        Option.optionOf("value").ifNone(action)

        verify(action, never()).invoke()
    }

    @Test
    fun `map invokes function with value when Some`() {
        val value = "value"
        val function: (String) -> Int = mock()

        Option.optionOf(value).map(function)

        verify(function).invoke(value)
    }

    @Test
    fun `map returns function value when Some`() {
        val value = "value"
        val function: (String) -> Int = { it.length }

        val result = Option.optionOf(value).map(function)

        assertThat(result).isEqualTo(Option.optionOf(value.length))
        assertThat(result.getUnsafe()).isEqualTo(value.length)
    }

    @Test
    fun `map does not invoke function when None`() {
        val function: (String) -> Int = mock()

        Option.optionOf(null).map(function)

        verify(function, never()).invoke(any())
    }

    @Test
    fun `flatmap returns function value when Some`() {
        val value = "value"
        val function: (String) -> Option<Int> = { Option.optionOf(it.length) }

        val result = Option.optionOf(value).flatmap(function)

        assertThat(result).isEqualTo(Option.optionOf(value.length))
    }

    @Test
    fun `flatmap does not invoke function value when None`() {
        val function: (String) -> Option<Int> = mock()

        Option.optionOf(null).flatmap(function)

        verify(function, never()).invoke(any())
    }

    @Test
    fun `flatmap returns None when None`() {
        val function: (String) -> Option<Int> = mock()

        val result = Option.optionOf(null).flatmap(function)

        assertThat(result.isNone()).isTrue()
    }

    @Test
    fun `filter returns Some when matches filter`() {
        val filterPred: (String) -> Boolean = { it == "value" }

        val result = Option.optionOf("value").filter(filterPred)

        assertThat(result.isSome()).isTrue()
    }

    @Test
    fun `filter returns value when matches filter`() {
        val value = "value"
        val filterPred: (String) -> Boolean = { it == value }

        val result = Option.optionOf(value).filter(filterPred)

        assertThat(result).isEqualTo(Option.optionOf(value))
        assertThat(result.getUnsafe()).isEqualTo(value)
    }

    @Test
    fun `filter returns None when does not match filter`() {
        val filterPred: (String) -> Boolean = { it == "not value" }

        val result = Option.optionOf("value").filter(filterPred)

        assertThat(result.isNone()).isTrue()
    }

    @Test
    fun `matchAction invokes someAction with value not noneAction when Some`() {
        val value = "value"
        val someAction: (String) -> Unit = mock()
        val noneAction: () -> Unit = mock()

        Option.optionOf(value).matchAction(someAction, noneAction)

        verify(someAction).invoke(value)
        verify(noneAction, never()).invoke()
    }

    @Test
    fun `matchAction invokes noneAction not someAction when None`() {
        val someAction: (String) -> Unit = mock()
        val noneAction: () -> Unit = mock()

        Option.optionOf(null).matchAction(someAction, noneAction)

        verify(noneAction).invoke()
        verify(someAction, never()).invoke(any())
    }

    @Test
    fun `match invokes someFunction with value not noneFunction when Some`() {
        val value = "value"
        val someFunction: (String) -> Int = mock()
        val noneFunction: () -> Int = mock()

        Option.optionOf(value).match(someFunction, noneFunction)

        verify(someFunction).invoke(value)
        verify(noneFunction, never()).invoke()
    }

    @Test
    fun `match returns someFunction result when Some`() {
        val someFunction: (String) -> Int = { 6 }
        val noneFunction: () -> Int = mock()

        val result = Option.optionOf("value").match(someFunction, noneFunction)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `match invokes noneFunction not someFunction when None`() {
        val someFunction: (String) -> Int = mock()
        val noneFunction: () -> Int = mock()

        Option.optionOf(null).match(someFunction, noneFunction)

        verify(noneFunction).invoke()
        verify(someFunction, never()).invoke(any())
    }

    @Test
    fun `match returns noneFunction result when None`() {
        val someFunction: (String) -> Int = mock()
        val noneFunction: () -> Int = { 6 }

        val result = Option.optionOf(null).match(someFunction, noneFunction)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `and invokes action with values when both Options are Some`() {
        val option1 = Option.optionOf("1")
        val option2 = Option.optionOf("2")
        val action: (String, String) -> Unit = mock()

        option1.and(option2, action)

        verify(action).invoke("1", "2")
    }

    @Test
    fun `and does not invoke action when first Option is None`() {
        val option1 = Option.optionOf(null)
        val option2 = Option.optionOf("2")
        val action: (String, String) -> Unit = mock()

        option1.and(option2, action)

        verify(action, never()).invoke(any(), any())
    }

    @Test
    fun `and does not invoke action when second Option is None`() {
        val option1 = Option.optionOf("1")
        val option2 = Option.optionOf(null)
        val action: (String, String) -> Unit = mock()

        option1.and(option2, action)

        verify(action, never()).invoke(any(), any())
    }

    @Test
    fun `id returns Option this when Some`() {
        val result = Option.optionOf("value").id()

        assertThat(result.id()).isEqualTo(result)
    }

    @Test
    fun `id returns Option this when None`() {
        val result = Option.optionOf(null).id()

        assertThat(result.id()).isEqualTo(result)
    }

    @Test
    fun `getUnsafe returns value when Some`() {
        val value = "value"

        val result = Option.optionOf(value).getUnsafe()

        assertThat(result).isEqualTo(value)
    }

    @Test
    fun `getUnsafe throws IllegalStateException when None`() {
        thrown.expect(IllegalStateException::class.java)
        thrown.expectMessage("Attempt to unsafely access value when Option is None")

        Option.optionOf(null).getUnsafe()
    }

    @Test
    fun `equals returns true when self`() {
        val option1 = Option.optionOf(null)

        assertThat(option1 == option1).isTrue()
    }

    @Test
    fun `equals returns true when both None`() {
        val option1 = Option.optionOf(null)
        val option2 = Option.optionOf(null)

        assertThat(option1 == option2).isTrue()
    }

    @Test
    fun `equals returns false when this Some other None`() {
        val option1 = Option.optionOf("value")
        val option2 = Option.optionOf(null)

        assertThat(option1 == option2).isFalse()
    }

    @Test
    fun `equals returns false when this None other Some`() {
        val option1 = Option.optionOf(null)
        val option2 = Option.optionOf("value")

        assertThat(option1 == option2).isFalse()
    }

    @Test
    fun `equals returns true when both same value`() {
        val option1 = Option.optionOf("value")
        val option2 = Option.optionOf("value")

        assertThat(option1 == option2).isTrue()
    }

    @Test
    fun `equals returns true when both same Some instance`() {
        val value = "value"
        val option = Option.optionOf(value)

        assertThat(option == option).isTrue()
    }

    @Test
    fun `equals returns true when both same value instance`() {
        val value = "value"
        val option1 = Option.optionOf(value)
        val option2 = Option.optionOf(value)

        assertThat(option1 == option2).isTrue()
    }

    @Test
    fun `equals returns true when both same None instance`() {
        val option = Option.optionOf(null)

        assertThat(option == option).isTrue()
    }

    @Test
    fun `equals returns false when different types`() {
        val option1 = Option.optionOf("value")
        val option2 = Option.optionOf(1)

        assertThat(option1 == option2).isFalse()
    }

    @Test
    fun `hashCode is value hashCode when Some`() {
        val value = "value"

        val hashCode = Option.optionOf(value).hashCode()

        assertThat(hashCode).isEqualTo(value.hashCode())
    }

    @Test
    fun `hashCode is constant when None`() {
        val hashCode1 = Option.optionOf(null).hashCode()
        val hashCode2 = Option.optionOf(null).hashCode()

        assertThat(hashCode1).isEqualTo(hashCode2)
    }

    @Test
    fun `toString is value toString when Some`() {
        val value = 1

        val toString = Option.optionOf(value).toString()

        assertThat(toString).isEqualTo("Some(value=1)")
    }

    @Test
    fun `toString is None when None`() {
        val toString = Option.optionOf(null).toString()

        assertThat(toString).isEqualTo("None")
    }
}