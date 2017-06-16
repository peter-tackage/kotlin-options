package com.petertackage.kotlinoptions

fun <T : Any> optionOf(value: T?): Option<T> =
        if (value == null) None else Some(value)

inline fun <T : Any> tryAsOption(function: () -> T?): Option<T> {
    return try {
        optionOf(function())
    } catch (e: Exception) {
        None
    }
}

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

    inline fun ifSome(action: (T) -> Unit): Option<T> {
        return when (this) {
            is Some -> apply { action(value) }
            is None -> this
        }
    }

    inline fun ifNone(action: () -> Unit): Option<T> {
        return when (this) {
            is Some -> this
            is None -> apply { action() }
        }
    }

    inline fun <R : Any> map(mapper: (T) -> R): Option<R> {
        return when (this) {
            is Some -> optionOf(mapper(value))
            is None -> this
        }
    }

    inline fun <R : Any> flatMap(mapper: (T) -> Option<R>): Option<R> {
        return when (this) {
            is Some -> mapper(value)
            is None -> this
        }
    }

    inline fun filter(predicate: (T) -> Boolean): Option<T> {
        return when (this) {
            is Some -> if (predicate(value)) this else None
            is None -> this
        }
    }

    inline fun matchAction(someAction: (T) -> Unit, noneAction: () -> Unit) {
        when (this) {
            is Some -> someAction(value)
            is None -> noneAction()
        }
    }

    inline fun <R> match(someFunction: (T) -> R, noneFunction: () -> R): R {
        return when (this) {
            is Some -> someFunction(value)
            is None -> noneFunction()
        }
    }

    inline fun <R : Any> and(other: Option<R>, action: (T, R) -> Unit) {
        if (this is Some && other is Some) {
            action(this.value, other.value)
        }
    }

    fun <R : Any> asType(clazz: Class<R>): Option<R> {
        return when (this) {
            is Some -> if (clazz.isInstance(value)) Some(clazz.cast(value)) else None
            is None -> this
        }
    }

    fun getUnsafe(): T {
        return when (this) {
            is Some -> value
            is None -> throw IllegalStateException("Attempt to unsafely access value when Option is None")
        }
    }

    fun toNullable(): T? {
        return when (this) {
            is Some -> value
            is None -> null
        }
    }

}
