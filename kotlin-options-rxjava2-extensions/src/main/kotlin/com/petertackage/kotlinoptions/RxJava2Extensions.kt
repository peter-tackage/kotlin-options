package com.petertackage.kotlinoptions

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

fun <T : Any> Observable<Option<T>>.filterIfSome(): Observable<T> {
    return this.filter { it.isSome() }.map { it.getUnsafe() }
}

fun <T : Any> Flowable<Option<T>>.filterIfSome(): Flowable<T> {
    return this.filter { it.isSome() }.map { it.getUnsafe() }
}

fun <T : Any> Single<Option<T>>.filterIfSome(): Maybe<T> {
    return this.filter { it.isSome() }.map { it.getUnsafe() }
}

fun <T : Any> Observable<Option<T>>.filterIfNone(): Observable<Unit> {
    return this.filter { it.isNone() }.map { Unit }
}

fun <T : Any> Flowable<Option<T>>.filterIfNone(): Flowable<Unit> {
    return this.filter { it.isNone() }.map { Unit }
}

fun <T : Any> Single<Option<T>>.filterIfNone(): Maybe<Unit> {
    return this.filter { it.isNone() }.map { Unit }
}