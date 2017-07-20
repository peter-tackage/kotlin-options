package com.petertackage.kotlinoptions

/**
 * Create an [Option] from the nullable [value].
 *
 * @return an instance of [Some] if [value] is non-null, or [None] otherwise.
 */
fun <T : Any> optionOf(value: T?): Option<T> =
        if (value == null) None else Some(value)

/**
 * Create an [Option] from the result of [function].
 *
 * @return return [None] if [function] throws an [Exception].
 */
inline fun <T : Any> tryAsOption(function: () -> T?): Option<T> {
    return try {
        optionOf(function())
    } catch (e: Exception) {
        None
    }
}

/**
 * @return the [Option]'s value or the evaluated [default], when [None].
 */
inline fun <T : Any> Option<T>.orDefault(default: () -> T): T {
    return when (this) {
        is Some -> value
        is None -> default()
    }
}

data class Some<out T : Any> internal constructor(val value: T) : Option<T>()
object None : Option<Nothing>() {
    override fun toString(): String {
        return "None"
    }
}

sealed class Option<out T : Any> {

    /**
     * Perform [action] when [Some], otherwise do nothing.
     *
     * @return `this`.
     */
    inline fun ifSome(action: (T) -> Unit): Option<T> {
        return when (this) {
            is Some -> apply { action(value) }
            is None -> this
        }
    }

    /**
     * Perform [action] when [None], otherwise do nothing.
     *
     * @returns `this`.
     */
    inline fun ifNone(action: () -> Unit): Option<T> {
        return when (this) {
            is Some -> this
            is None -> apply { action() }
        }
    }

    /**
     * @return The wrapped [Option] result of [mapper] when [Some], otherwise do nothing and return [None].
     */
    inline fun <R : Any> map(mapper: (T) -> R): Option<R> {
        return when (this) {
            is Some -> optionOf(mapper(value))
            is None -> this
        }
    }

    /**
     * @return The result of [mapper] when [Some], otherwise do nothing and return [None].
     */
    inline fun <R : Any> flatMap(mapper: (T) -> Option<R>): Option<R> {
        return when (this) {
            is Some -> mapper(value)
            is None -> this
        }
    }

    /**
     * @return `this` if [predicate] returns true when [Some], otherwise [None].
     */
    inline fun filter(predicate: (T) -> Boolean): Option<T> {
        return when (this) {
            is Some -> if (predicate(value)) this else None
            is None -> this
        }
    }

    /**
     * Perform [someAction] when [Some] or perform [noneAction] when [None].
     */
    inline fun matchAction(someAction: (T) -> Unit, noneAction: () -> Unit) {
        when (this) {
            is Some -> someAction(value)
            is None -> noneAction()
        }
    }

    /**
     * @return The result of [someFunction] when [Some] or [noneFunction] when [None].
     */
    inline fun <R> match(someFunction: (T) -> R, noneFunction: () -> R): R {
        return when (this) {
            is Some -> someFunction(value)
            is None -> noneFunction()
        }
    }

    /**
     * Perform [action] using the value of both `this` and [other] when both are [Some].
     */
    inline fun <R : Any> and(other: Option<R>, action: (T, R) -> Unit) {
        if (this is Some && other is Some) {
            action(this.value, other.value)
        }
    }

    /**
     * @return an [Option] of the value cast to [R] when [Some] and possible. Otherwise [None].
     */
    inline fun <reified R : Any> asType(): Option<R> {
        return when (this) {
            is Some -> if (value is R) optionOf(value as R) else None
            is None -> this
        }
    }

    /**
     * @return A non-null representation of the value.
     *
     * @throws [Exception] when `this` is [None].
     */
    fun getUnsafe(): T {
        return when (this) {
            is Some -> value
            is None -> throw IllegalStateException("Attempt to unsafely access value when Option is None")
        }
    }

    /**
     * @return A nullable representation of the value.
     */
    fun toNullable(): T? {
        return when (this) {
            is Some -> value
            is None -> null
        }
    }

}
