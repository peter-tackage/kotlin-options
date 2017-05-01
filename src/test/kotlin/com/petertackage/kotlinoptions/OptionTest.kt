package com.petertackage.kotlinoptions

import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import org.assertj.core.api.Assertions.assertThat
import org.junit.Test

class OptionTest {

    @Test
    fun `isSome returns true when no-nnull value`() {
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

}