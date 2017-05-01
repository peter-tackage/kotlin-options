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
}