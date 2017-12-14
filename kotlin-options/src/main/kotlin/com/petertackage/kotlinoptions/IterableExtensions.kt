package com.petertackage.kotlinoptions

/**
 * @return an [Iterable] containing all elements of the original [Iterable] which are [Some].
 */
fun <T : Any> Iterable<Option<T>>.filterNotNone(): Iterable<T> {
    return mapNotNull(Option<T>::toNullable)
}

/** @Deprecated
 * @return an [Iterable] containing all elements of the original [Iterable] which are [Some].
 */
@Deprecated("Does not alight with kotlin naming convention",
        ReplaceWith("filterNotNone()"))
fun <T : Any> Iterable<Option<T>>.filterIfSome(): Iterable<T> {
    return filterNotNone()
}

/**
 * @return an [Iterable] containing all elements of the original [Iterable] which are [Some] and that satisfy [predicate].
 */
fun <T : Any> Iterable<Option<T>>.filterNotNone(predicate: (T) -> Boolean): Iterable<T> {
    return mapNotNull(Option<T>::toNullable).filter(predicate)
}

/** @Deprecated
 * @return an [Iterable] containing all elements of the original [Iterable] which are [Some] and that satisfy [predicate].
 */
@Deprecated("Does not alight with kotlin naming convention",
        ReplaceWith("filterNotNone(predicate)"))
fun <T : Any> Iterable<Option<T>>.filterIfSome(predicate: (T) -> Boolean): Iterable<T> {
    return filterNotNone(predicate)
}
