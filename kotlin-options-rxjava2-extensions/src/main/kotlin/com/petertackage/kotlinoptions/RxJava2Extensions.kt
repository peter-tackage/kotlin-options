package com.petertackage.kotlinoptions

import io.reactivex.Flowable
import io.reactivex.Maybe
import io.reactivex.Observable
import io.reactivex.Single

/*
 * filterNotNone
 */

/////////////////////////////
///////// Observable ////////
/////////////////////////////

fun <T : Any> Observable<Option<T>>.filterNotNone(): Observable<T> =
        filter { it is Some }.map { it.getUnsafe() }

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone()"))
fun <T : Any> Observable<Option<T>>.filterIfSome(): Observable<T> = filterNotNone()

fun <T : Any> Observable<Option<T>>.filterNotNone(predicate: (T) -> Boolean): Observable<T> =
        filter { it is Some }.map { it.getUnsafe() }.filter(predicate)

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone(predicate)"))
fun <T : Any> Observable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Observable<T> =
        filterNotNone(predicate)

/////////////////////////////
///////// Flowable //////////
/////////////////////////////

fun <T : Any> Flowable<Option<T>>.filterNotNone(): Flowable<T> =
        filter { it is Some }.map { it.getUnsafe() }

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone()"))
fun <T : Any> Flowable<Option<T>>.filterIfSome(): Flowable<T> = filterNotNone()

fun <T : Any> Flowable<Option<T>>.filterNotNone(predicate: (T) -> Boolean): Flowable<T> =
        filter { it is Some }.map { it.getUnsafe() }.filter(predicate)

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone(predicate)"))
fun <T : Any> Flowable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Flowable<T> =
        filterNotNone(predicate)

/////////////////////////////
///////// Single ////////////
/////////////////////////////

fun <T : Any> Single<Option<T>>.filterNotNone(): Maybe<T> =
        filter { it is Some }.map { it.getUnsafe() }

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone()"))
fun <T : Any> Single<Option<T>>.filterIfSome(): Maybe<T> = filterNotNone()

fun <T : Any> Single<Option<T>>.filterNotNone(predicate: (T) -> Boolean): Maybe<T> =
        filterNotNone().filter(predicate)

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone(predicate)"))
fun <T : Any> Single<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Maybe<T> =
        filterNotNone(predicate)

/////////////////////////////
///////// Maybe /////////////
/////////////////////////////

fun <T : Any> Maybe<Option<T>>.filterNotNone(): Maybe<T> =
        filter { it is Some }.map { it.getUnsafe() }

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone()"))
fun <T : Any> Maybe<Option<T>>.filterIfSome(): Maybe<T> = filterNotNone()

fun <T : Any> Maybe<Option<T>>.filterNotNone(predicate: (T) -> Boolean): Maybe<T> =
        filterNotNone().filter(predicate)

@Deprecated("Does not conform to Kotlin naming convention",
        ReplaceWith("filterNotNone(predicate)"))
fun <T : Any> Maybe<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Maybe<T> =
        filterNotNone(predicate)

/*
 * filterIfNone
 */

fun <T : Any> Observable<Option<T>>.filterIfNone(): Observable<Unit> =
        filter { it is None }.map { Unit }

fun <T : Any> Flowable<Option<T>>.filterIfNone(): Flowable<Unit> =
        filter { it is None }.map { Unit }

fun <T : Any> Single<Option<T>>.filterIfNone(): Maybe<Unit> = filter { it is None }.map { Unit }

fun <T : Any> Maybe<Option<T>>.filterIfNone(): Maybe<Unit> = filter { it is None }.map { Unit }
