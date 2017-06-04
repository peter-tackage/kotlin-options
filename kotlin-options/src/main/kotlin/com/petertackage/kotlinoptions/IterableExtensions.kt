package com.petertackage.kotlinoptions

fun <T : Any> Iterable<Option<T>>.filterIfSome(): Iterable<T> {
    return filter { it is Some }.map { it.toNullable()!! }
}

fun <T : Any> Iterable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Iterable<T> {
    return filter { it is Some }.map { it.toNullable()!! }.filter(predicate)
}
