package com.petertackage.kotlinoptions

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single
import org.junit.Test

class RxJava2ExtensionsTest {

    /*
     * filterIfSome
     */

    @Test
    fun `filterIfSome on Observable includes when Some`() {
        val value = "abc"
        Observable.just(optionOf(value))
                .filterIfSome()
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome on Observable excludes when None`() {
        val option = optionOf(null)

        Observable.just(option)
                .filterIfSome()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome on Flowable includes when Some`() {
        val value = "abc"
        Flowable.just(optionOf(value))
                .filterIfSome()
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome on Flowable excludes when None`() {
        val option = optionOf(null)

        Flowable.just(option)
                .filterIfSome()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome on Single includes when Some`() {
        val value = "abc"
        Single.just(optionOf(value))
                .filterIfSome()
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome on Single excludes when None`() {
        Single.just(optionOf(null))
                .filterIfSome()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome on Maybe includes when Some`() {
        val value = "abc"
        Maybe.just(optionOf(value))
                .filterIfSome()
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome on Maybe excludes when None`() {
        Maybe.just(optionOf(null))
                .filterIfSome()
                .test()
                .assertNoValues()
    }

    /*
     * filterIfNone
     */

    @Test
    fun `filterIfNone on Observable includes when None`() {
        Observable.just(optionOf(null))
                .filterIfNone()
                .test()
                .assertValue(Unit)
    }

    @Test
    fun `filterIfNone on Observable excludes when Some`() {
        Observable.just(optionOf("abc"))
                .filterIfNone()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfNone on Flowable includes when None`() {
        Flowable.just(optionOf(null))
                .filterIfNone()
                .test()
                .assertValue(Unit)
    }

    @Test
    fun `filterIfNone on Flowable excludes when Some`() {
        Flowable.just(optionOf("abc"))
                .filterIfNone()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfNone on Single includes when None`() {
        Single.just(optionOf(null))
                .filterIfNone()
                .test()
                .assertValue(Unit)
    }

    @Test
    fun `filterIfNone on Single excludes when Some`() {
        Single.just(optionOf("abc"))
                .filterIfNone()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfNone on Maybe includes when None`() {
        Maybe.just(optionOf(null))
                .filterIfNone()
                .test()
                .assertValue(Unit)
    }

    @Test
    fun `filterIfNone on Maybe excludes when Some`() {
        Maybe.just(optionOf("abc"))
                .filterIfNone()
                .test()
                .assertNoValues()
    }

}