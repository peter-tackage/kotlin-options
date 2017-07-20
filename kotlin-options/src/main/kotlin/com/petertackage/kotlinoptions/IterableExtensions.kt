package com.petertackage.kotlinoptions

/**
 * @return A new instance of the [Iterable] only containing [Some] elements.
 */
fun <T : Any> Iterable<Option<T>>.filterIfSome(): Iterable<T> {
    return filter { it is Some }.map { it.toNullable()!! }
}

/**
 * @return A new instance of the [Iterable] only containing the [Some] elements that satisfy [predicate].
 */
fun <T : Any> Iterable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Iterable<T> {
    return filter { it is Some }.map { it.toNullable()!! }.filter(predicate)
}
