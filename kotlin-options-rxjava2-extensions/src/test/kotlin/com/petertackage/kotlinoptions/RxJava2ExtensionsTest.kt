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
        Observable.just(optionOf(null))
                .filterIfSome()
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Observable includes when Some and predicate True`() {
        val value = "abc"
        Observable.just(optionOf(value))
                .filterIfSome { true }
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome with predicate on Observable excludes when Some and predicate False`() {
        Observable.just(optionOf("abc"))
                .filterIfSome { false }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Observable excludes when None and predicate True`() {
        Observable.just(optionOf(null))
                .filterIfSome { true }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Observable excludes when None and predicate False`() {
        Observable.just(optionOf("abc"))
                .filterIfSome { false }
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
    fun `filterIfSome with predicate on Flowable includes when Some and predicate True`() {
        val value = "abc"
        Flowable.just(optionOf(value))
                .filterIfSome { true }
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome with predicate on Flowable excludes when Some and predicate False`() {
        Flowable.just(optionOf("abc"))
                .filterIfSome { false }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Flowable excludes when None and predicate True`() {
        Flowable.just(optionOf(null))
                .filterIfSome { true }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Flowable excludes when None and predicate False`() {
        Flowable.just(optionOf("abc"))
                .filterIfSome { false }
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
    fun `filterIfSome with predicate on Single includes when Some and predicate True`() {
        val value = "abc"
        Single.just(optionOf(value))
                .filterIfSome { true }
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome with predicate on Single excludes when Some and predicate False`() {
        Single.just(optionOf("abc"))
                .filterIfSome { false }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Single excludes when None and predicate True`() {
        Single.just(optionOf(null))
                .filterIfSome { true }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Single excludes when None and predicate False`() {
        Single.just(optionOf("abc"))
                .filterIfSome { false }
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

    @Test
    fun `filterIfSome with predicate on Maybe includes when Some and predicate True`() {
        val value = "abc"
        Maybe.just(optionOf(value))
                .filterIfSome { true }
                .test()
                .assertValue(value)
    }

    @Test
    fun `filterIfSome with predicate on Maybe excludes when Some and predicate False`() {
        Maybe.just(optionOf("abc"))
                .filterIfSome { false }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Maybe excludes when None and predicate True`() {
        Maybe.just(optionOf(null))
                .filterIfSome { true }
                .test()
                .assertNoValues()
    }

    @Test
    fun `filterIfSome with predicate on Maybe excludes when None and predicate False`() {
        Maybe.just(optionOf("abc"))
                .filterIfSome { false }
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