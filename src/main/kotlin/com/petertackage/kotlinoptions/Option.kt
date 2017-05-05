package com.petertackage.kotlinoptions

sealed class Option<out T : Any> {

    companion object {
        fun <T : Any> optionOf(value: T?): Option<T> {
            return if (value == null) Option.None else Option.Some(value)
        }
    }

    private data class Some<out T : Any>(val value: T) : Option<T>()
    private object None : Option<Nothing>() {
        override fun toString(): String {
            return "None"
        }
    }

    fun isSome(): Boolean {
        return when (this) {
            is Option.Some -> true
            is Option.None -> false
        }
    }

    fun isNone(): Boolean {
        return !isSome()
    }

    fun ifSome(action: (T) -> Unit) {
        when (this) {
            is Option.Some -> action(value)
        }
    }

    fun ifNone(action: () -> Unit) {
        when (this) {
            is Option.None -> action()
        }
    }

    fun <R : Any> map(mapper: (T) -> R): Option<R> {
        return when (this) {
            is Option.Some -> optionOf(mapper(value))
            is Option.None -> this
        }
    }

    fun <R : Any> flatmap(mapper: (T) -> Option<R>): Option<R> {
        return when (this) {
            is Option.Some -> mapper(value)
            is Option.None -> this
        }
    }

    fun filter(predicate: (T) -> Boolean): Option<T> {
        return when (this) {
            is Option.Some -> if (predicate(value)) this else Option.None
            is Option.None -> this
        }
    }

    fun matchAction(someAction: (T) -> Unit, noneAction: () -> Unit) {
        when (this) {
            is Option.Some -> someAction(value)
            is Option.None -> noneAction()
        }
    }

    fun <R> match(someFunction: (T) -> R, noneFunction: () -> R): R {
        return when (this) {
            is Option.Some -> someFunction(value)
            is Option.None -> noneFunction()
        }
    }

    fun <R : Any> and(other: Option<R>, action: (T, R) -> Unit) {
        when (this) {
            is Option.Some -> when (other) {
                is Option.Some -> action(this.value, other.value)
            }
        }
    }

    fun <R : Any> asType(clazz: Class<R>): Option<R> {
        return when (this) {
            is Option.Some -> if (clazz.isInstance(value)) Option.Some(clazz.cast(value)) else Option.None
            is Option.None -> this
        }
    }

    fun <R : Any> tryAsOption(function: () -> R): Option<R> {
        try {
            return optionOf(function())
        } catch (e: Exception) {
            return Option.None
        }
    }

    fun id(): Option<T> {
        return this
    }

    fun getUnsafe(): T {
        return when (this) {
            is Option.Some -> value
            is Option.None -> throw IllegalStateException("Option is NONE")
        }
    }
//
//    override fun equals(other: Any?): Boolean {
//        return when (other) {
//            is Option.Some<*> -> when (this) {
//                is Option.Some -> other.asType(Some::class.java).getUnsafe() == other.value
//                is Option.None -> false
//                else -> false
//            }
//            is Option.None -> this === Option.None
//            else -> false
//        }
//    }
//
//    override fun hashCode(): Int {
//        return when (this) {
//            is Option.Some -> value.hashCode()
//            is Option.None -> 0
//        }
//    }
//
//    override fun toString(): String {
//        return when (this) {
//            is Option.Some -> value.toString()
//            is Option.None -> "None"
//        }
//    }

    // TODO Fix generic issues

//    fun orOption(optionFunc: () -> Option<T>): Option<T> {
//        return when (this) {
//            is Option.Some -> this
//            is Option.None -> optionFunc()
//        }
//    }
//
//    fun orOption(option: Option<T>): Option<T> {
//        return when (this) {
//            is Option.Some -> this
//            is Option.None -> option
//        }
//    }
//
//    fun orDefault(default: () -> T): T {
//        return when (this) {
//            is Option.Some -> this.value
//            is Option.None -> default()
//        }
//    }
//
//    fun orDefault(default: T): T {
//        return when (this) {
//            is Option.Some -> this
//            is Option.None -> default
//        }
//    }

}
