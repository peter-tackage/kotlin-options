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
        val mockAction: (String) -> Unit = mock()

        Option.optionOf(value).ifSome(mockAction)

        verify(mockAction).invoke(value)
    }

    @Test
    fun `ifSome does not perform action with value when None`() {
        val mockAction: (String) -> Unit = mock()

        Option.optionOf(null).ifSome(mockAction)

        verify(mockAction, never()).invoke(any())
    }

    @Test
    fun `ifNone performs action when None`() {
        val mockAction: () -> Unit = mock()

        Option.optionOf(null).ifNone(mockAction)

        verify(mockAction).invoke()
    }

    @Test
    fun `ifNone does not perform action when Some`() {
        val mockAction: () -> Unit = mock()

        Option.optionOf("value").ifNone(mockAction)

        verify(mockAction, never()).invoke()
    }

    @Test
    fun `map invokes function with value when Some`() {
        val value = "value"
        val mockFunc: (String) -> Int = mock()

        Option.optionOf(value).map(mockFunc)

        verify(mockFunc).invoke(value)
    }

    @Test
    fun `map returns function value when Some`() {
        val value = "value"
        val mockFunc: (String) -> Int = { it.length }

        val result = Option.optionOf(value).map(mockFunc)

        assertThat(result).isEqualTo(Option.optionOf(value.length))
        assertThat(result.getUnsafe()).isEqualTo(value.length)
    }

    @Test
    fun `map does not invoke function when None`() {
        val mockFunc: (String) -> Int = mock()

        Option.optionOf(null).map(mockFunc)

        verify(mockFunc, never()).invoke(any())
    }

    @Test
    fun `flatmap returns function value when Some`() {
        val value = "value"
        val mockFunc: (String) -> Option<Int> = { Option.optionOf(it.length) }

        val result = Option.optionOf(value).flatmap(mockFunc)

        assertThat(result).isEqualTo(Option.optionOf(value.length))
    }

    @Test
    fun `flatmap does not invoke function value when None`() {
        val mockFunc: (String) -> Option<Int> = mock()

        Option.optionOf(null).flatmap(mockFunc)

        verify(mockFunc, never()).invoke(any())
    }

    @Test
    fun `flatmap returns None when None`() {
        val mockFunc: (String) -> Option<Int> = mock()

        val result = Option.optionOf(null).flatmap(mockFunc)

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
}