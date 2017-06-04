package com.petertackage.kotlinoptions

fun <T : Any> optionOf(value: T?): Option<T> =
        if (value == null) None else Some(value)

data class Some<out T : Any> internal constructor(val value: T) : Option<T>()
object None : Option<Nothing>() {
    override fun toString(): String {
        return "None"
    }
}

sealed class Option<out T : Any> {

    fun ifSome(action: (T) -> Unit) {
        when (this) {
            is Some -> action(value)
        }
    }

    fun ifNone(action: () -> Unit) {
        when (this) {
            is None -> action()
        }
    }

    fun <R : Any> map(mapper: (T) -> R): Option<R> {
        return when (this) {
            is Some -> optionOf(mapper(value))
            is None -> this
        }
    }

    fun <R : Any> flatMap(mapper: (T) -> Option<R>): Option<R> {
        return when (this) {
            is Some -> mapper(value)
            is None -> this
        }
    }

    fun filter(predicate: (T) -> Boolean): Option<T> {
        return when (this) {
            is Some -> if (predicate(value)) this else None
            is None -> this
        }
    }

    fun matchAction(someAction: (T) -> Unit, noneAction: () -> Unit) {
        when (this) {
            is Some -> someAction(value)
            is None -> noneAction()
        }
    }

    fun <R> match(someFunction: (T) -> R, noneFunction: () -> R): R {
        return when (this) {
            is Some -> someFunction(value)
            is None -> noneFunction()
        }
    }

    fun <R : Any> and(other: Option<R>, action: (T, R) -> Unit) {
        when (this) {
            is Some -> when (other) {
                is Some -> action(this.value, other.value)
            }
        }
    }

    fun <R : Any> asType(clazz: Class<R>): Option<R> {
        return when (this) {
            is Some -> if (clazz.isInstance(value)) Some(clazz.cast(value)) else None
            is None -> this
        }
    }

    fun <R : Any> tryAsOption(function: () -> R): Option<R> {
        try {
            return optionOf(function())
        } catch (e: Exception) {
            return None
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
