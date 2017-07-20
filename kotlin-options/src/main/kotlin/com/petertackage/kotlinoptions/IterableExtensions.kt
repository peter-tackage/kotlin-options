package com.petertackage.kotlinoptions

/**
 * @return an [Iterable] containing all elements of the original collection which are [Some].
 */
fun <T : Any> Iterable<Option<T>>.filterIfSome(): Iterable<T> {
    return filter { it is Some }.map { it.toNullable()!! }
}

/**
 * @return an [Iterable] containing all elements of the original collection which are [Some] and that satisfy [predicate].
 */
fun <T : Any> Iterable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Iterable<T> {
    return filter { it is Some }.map { it.toNullable()!! }.filter(predicate)
}
