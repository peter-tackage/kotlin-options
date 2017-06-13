package com.petertackage.kotlinoptions

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Rule
import org.junit.Test
import org.junit.rules.ExpectedException

class OptionTest {

    @Rule
    @JvmField
    var thrown = ExpectedException.none()

    @Test
    fun `optionOf returns None when value is null`() {
        assertThat(optionOf(null) is None).isTrue()
    }

    @Test
    fun `optionOf returns Some when value is non-null`() {
        assertThat(optionOf("abc") is Some).isTrue()
    }

    @Test
    fun `optionOf None is single instance`() {
        assertThat(optionOf(null)).isSameAs(optionOf(null))
    }

    @Test
    fun `optionOf Some is not single instance`() {
        val value = "abc"
        assertThat(optionOf(value)).isNotSameAs(optionOf(value))
    }

    @Test
    fun `ifSome returns self`() {
        val option = optionOf("value")

        val result = option.ifSome { }

        assertThat(result).isEqualTo(option)
    }

    @Test
    fun `ifSome performs action with value when Some`() {
        val value = "value"
        val action: (String) -> Unit = mock()

        Some(value).ifSome(action)

        verify(action).invoke(value)
    }

    @Test
    fun `ifSome does not perform action with value when None`() {
        val action: (String) -> Unit = mock()

        None.ifSome(action)

        verify(action, never()).invoke(any())
    }

    @Test
    fun `ifNone returns self`() {
        val result = None.ifNone { }

        assertThat(result).isEqualTo(None)
    }

    @Test
    fun `ifNone performs action when None`() {
        val action: () -> Unit = mock()

        None.ifNone(action)

        verify(action).invoke()
    }

    @Test
    fun `ifNone does not perform action when Some`() {
        val action: () -> Unit = mock()

        Some("value").ifNone(action)

        verify(action, never()).invoke()
    }

    @Test
    fun `map invokes function with value when Some`() {
        val value = "value"
        val function: (String) -> Int = mock()

        Some(value).map(function)

        verify(function).invoke(value)
    }

    @Test
    fun `map returns function value when Some`() {
        val value = "value"
        val function: (String) -> Int = { it.length }

        val result = Some(value).map(function)

        assertThat(result).isEqualTo(optionOf(value.length))
        assertThat(result.getUnsafe()).isEqualTo(value.length)
    }

    @Test
    fun `map does not invoke function when None`() {
        val function: (String) -> Int = mock()

        None.map(function)

        verify(function, never()).invoke(any())
    }

    @Test
    fun `flatmap returns function value when Some`() {
        val value = "value"
        val function: (String) -> Option<Int> = { Some(it.length) }

        val result = Some(value).flatMap(function)

        assertThat(result).isEqualTo(Some(value.length))
    }

    @Test
    fun `flatmap does not invoke function value when None`() {
        val function: (String) -> Option<Int> = mock()

        None.flatMap(function)

        verify(function, never()).invoke(any())
    }

    @Test
    fun `flatmap returns None when None`() {
        val function: (String) -> Option<Int> = mock()

        val result = None.flatMap(function)

        assertThat(result is None).isTrue()
    }

    @Test
    fun `filter returns Some when matches filter`() {
        val filterPred: (String) -> Boolean = { it == "value" }

        val result = Some("value").filter(filterPred)

        assertThat(result is Some).isTrue()
    }

    @Test
    fun `filter returns value when matches filter`() {
        val value = "value"
        val filterPred: (String) -> Boolean = { it == value }

        val result = Some(value).filter(filterPred)

        assertThat(result).isEqualTo(optionOf(value))
        assertThat(result.getUnsafe()).isEqualTo(value)
    }

    @Test
    fun `filter returns None when does not match filter`() {
        val filterPred: (String) -> Boolean = { it == "not value" }

        val result = Some("value").filter(filterPred)

        assertThat(result is None).isTrue()
    }

    @Test
    fun `matchAction invokes someAction with value not noneAction when Some`() {
        val value = "value"
        val someAction: (String) -> Unit = mock()
        val noneAction: () -> Unit = mock()

        Some(value).matchAction(someAction, noneAction)

        verify(someAction).invoke(value)
        verify(noneAction, never()).invoke()
    }

    @Test
    fun `matchAction invokes noneAction not someAction when None`() {
        val someAction: (String) -> Unit = mock()
        val noneAction: () -> Unit = mock()

        None.matchAction(someAction, noneAction)

        verify(noneAction).invoke()
        verify(someAction, never()).invoke(any())
    }

    @Test
    fun `match invokes someFunction with value not noneFunction when Some`() {
        val value = "value"
        val someFunction: (String) -> Int = mock()
        val noneFunction: () -> Int = mock()

        Some(value).match(someFunction, noneFunction)

        verify(someFunction).invoke(value)
        verify(noneFunction, never()).invoke()
    }

    @Test
    fun `match returns someFunction result when Some`() {
        val someFunction: (String) -> Int = { 6 }
        val noneFunction: () -> Int = mock()

        val result = Some("value").match(someFunction, noneFunction)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `match invokes noneFunction not someFunction when None`() {
        val someFunction: (String) -> Int = mock()
        val noneFunction: () -> Int = mock()

        None.match(someFunction, noneFunction)

        verify(noneFunction).invoke()
        verify(someFunction, never()).invoke(any())
    }

    @Test
    fun `match returns noneFunction result when None`() {
        val someFunction: (String) -> Int = mock()
        val noneFunction: () -> Int = { 6 }

        val result = None.match(someFunction, noneFunction)

        assertThat(result).isEqualTo(6)
    }

    @Test
    fun `and invokes action with values when both Options are Some`() {
        val option1 = optionOf("1")
        val option2 = optionOf("2")
        val action: (String, String) -> Unit = mock()

        option1.and(option2, action)

        verify(action).invoke("1", "2")
    }

    @Test
    fun `and does not invoke action when first Option is None`() {
        val option1 = None
        val option2 = optionOf("2")
        val action: (String, String) -> Unit = mock()

        option1.and(option2, action)

        verify(action, never()).invoke(any(), any())
    }

    @Test
    fun `and does not invoke action when second Option is None`() {
        val option1 = optionOf("1")
        val option2 = None
        val action: (String, String) -> Unit = mock()

        option1.and(option2, action)

        verify(action, never()).invoke(any(), any())
    }

    @Test
    fun `getUnsafe returns value when Some`() {
        val value = "value"

        val result = optionOf(value).getUnsafe()

        assertThat(result).isEqualTo(value)
    }

    @Test
    fun `getUnsafe throws IllegalStateException when None`() {
        thrown.expect(IllegalStateException::class.java)
        thrown.expectMessage("Attempt to unsafely access value when Option is None")

        None.getUnsafe()
    }

    @Test
    fun `equals returns true when self`() {
        val option1 = None

        assertThat(option1 == option1).isTrue()
    }

    @Test
    fun `equals returns true when both None`() {
        val option1 = None
        val option2 = None

        assertThat(option1 == option2).isTrue()
    }

    @Test
    fun `equals returns false when this Some other None`() {
        val option1 = optionOf("value")
        val option2 = None

        assertThat(option1 == option2).isFalse()
    }

    @Test
    fun `equals returns false when this None other Some`() {
        val option1 = None
        val option2 = optionOf("value")

        assertThat(option1 == option2).isFalse()
    }

    @Test
    fun `equals returns true when both same value`() {
        val option1 = Some("value")
        val option2 = Some("value")

        assertThat(option1 == option2).isTrue()
    }

    @Test
    fun `equals returns true when both same Some instance`() {
        val value = "value"
        val option = Some(value)

        assertThat(option == option).isTrue()
    }

    @Test
    fun `equals returns true when both same value instance`() {
        val value = "value"
        val option1 = Some(value)
        val option2 = Some(value)

        assertThat(option1 == option2).isTrue()
    }

    @Test
    fun `equals returns true when both same None instance`() {
        val option = None

        assertThat(option == option).isTrue()
    }

    @Test
    fun `equals returns false when different types`() {
        val option1 = optionOf("value")
        val option2 = optionOf(1)

        assertThat(option1 == option2).isFalse()
    }

    @Test
    fun `hashCode is value hashCode when Some`() {
        val value = "value"

        val hashCode = Some(value).hashCode()

        assertThat(hashCode).isEqualTo(value.hashCode())
    }

    @Test
    fun `hashCode is constant when None`() {
        val hashCode1 = None.hashCode()
        val hashCode2 = None.hashCode()

        assertThat(hashCode1).isEqualTo(hashCode2)
    }

    @Test
    fun `toString is value toString when Some`() {
        val value = 1

        val toString = Some(value).toString()

        assertThat(toString).isEqualTo("Some(value=1)")
    }

    @Test
    fun `toString is None when None`() {
        val toString = None.toString()

        assertThat(toString).isEqualTo("None")
    }

    @Test
    fun `toNullable returns value when Some`() {
        val expected: String? = "value"

        val nullable: String? = Some("value").toNullable()

        assertThat(nullable).isEqualTo(expected)
    }

    @Test
    fun `toNullable returns null when None`() {
        val nullable: String? = None.toNullable()

        assertThat(nullable).isNull()
    }

    @Test
    fun `tryAsOption returns Some if non-null value returned`() {
        val expected = "result"

        val result = tryAsOption { expected }

        assertThat(result.getUnsafe()).isEqualTo(expected)
    }

    @Test
    fun `tryAsOption returns None if null value returned`() {
        val result = tryAsOption { null }

        assertThat(result is None).isTrue()
    }

    @Test
    fun `tryAsOption returns None if function throws`() {
        val result = tryAsOption { throw RuntimeException("") }

        assertThat(result is None).isTrue()
    }
}
