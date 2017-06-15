package com.petertackage.kotlinoptions

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/*
 * filterIfSome
 */

fun <T : Any> Observable<Option<T>>.filterIfSome(): Observable<T> {
    return filter { it is Some }.map { it.getUnsafe() }
}

fun <T : Any> Observable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Observable<T> {
    return filterIfSome().filter(predicate)
}

fun <T : Any> Flowable<Option<T>>.filterIfSome(): Flowable<T> {
    return filter { it is Some }.map { it.getUnsafe() }
}

fun <T : Any> Flowable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Flowable<T> {
    return filterIfSome().filter(predicate)
}

fun <T : Any> Single<Option<T>>.filterIfSome(): Maybe<T> {
    return filter { it is Some }.map { it.getUnsafe() }
}

fun <T : Any> Single<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Maybe<T> {
    return filterIfSome().filter(predicate)
}

fun <T : Any> Maybe<Option<T>>.filterIfSome(): Maybe<T> {
    return filter { it is Some }.map { it.getUnsafe() }
}

fun <T : Any> Maybe<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Maybe<T> {
    return filterIfSome().filter(predicate)
}

/*
 * filterIfNone
 */

fun <T : Any> Observable<Option<T>>.filterIfNone(): Observable<Unit> {
    return filter { it is None }.map { Unit }
}

fun <T : Any> Flowable<Option<T>>.filterIfNone(): Flowable<Unit> {
    return filter { it is None }.map { Unit }
}

fun <T : Any> Single<Option<T>>.filterIfNone(): Maybe<Unit> {
    return filter { it is None }.map { Unit }
}

fun <T : Any> Maybe<Option<T>>.filterIfNone(): Maybe<Unit> {
    return filter { it is None }.map { Unit }
}
