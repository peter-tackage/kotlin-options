package com.moac.kotlinoptions

sealed class Option<out T : Any> {

    data class Some<out T : Any>(val element: T) : Option<T>()
    object None : Option<Nothing>()

    fun isSome(): Boolean {
        return when (this) {
            is Option.Some -> true
            is Option.None -> false
        }
    }

    fun isNone(): Boolean {
        return when (this) {
            is Option.Some -> false
            is Option.None -> true
        }
    }

    fun ifSome(action: (T) -> Unit) {
        when (this) {
            is Option.Some -> action(element)
        }
    }

    fun ifNone(action: () -> Unit) {
        when (this) {
            is Option.None -> action()
        }
    }

    fun <R : Any> map(mapper: (T) -> R): Option<R> {
        return when (this) {
            is Option.Some -> optionOf(mapper(element))
            is Option.None -> this
        }
    }

    fun <R : Any> flatmap(mapper: (T) -> Option<R>): Option<R> {
        return when (this) {
            is Option.Some -> mapper(element)
            is Option.None -> this
        }
    }

    fun filter(predicate: (T) -> Boolean): Option<T> {
        return when (this) {
            is Option.Some -> if (predicate(element)) this else Option.None
            is Option.None -> this
        }
    }

    fun matchAction(someAction: (T) -> Unit, noneAction: () -> Unit) {
        when (this) {
            is Option.Some -> someAction(element)
            is Option.None -> noneAction()
        }
    }

    fun <R> match(someFunction: (T) -> R, noneFunction: () -> R) {
        when (this) {
            is Option.Some -> someFunction(element)
            is Option.None -> noneFunction()
        }
    }

    fun <R : Any> and(other: Option<R>, action: (T, R) -> Unit) {
        when (this) {
            is Option.Some -> when (other) {
                is Option.Some -> action(this.element, other.element)
            }
        }
    }

    fun <R : Any> asType(clazz: Class<R>): Option<R> {
        return when (this) {
            is Option.Some -> if (clazz.isInstance(element)) Option.Some(clazz.cast(element)) else Option.None
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

    fun getUnsafe(): T {
        return when (this) {
            is Option.Some -> element
            is Option.None -> throw IllegalStateException("Option is NONE")
        }
    }

    override fun equals(other: Any?): Boolean {

        val otherOption: Option<Any> = optionOf(other)

        return when (otherOption) {
            is Option.Some -> when (this) {
                is Option.Some -> otherOption.asType(Some::class.java).getUnsafe() == otherOption.element
                is Option.None -> false
            }
            is Option.None -> this == Option.None
        }
    }

    fun id(): Option<T> {
        return this
    }

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
//            is Option.Some -> this.element
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

    override fun toString(): String {
        return when (this) {
            is Option.Some -> element.toString()
            is Option.None -> "None"
        }
    }

    override fun hashCode(): Int {
        return when (this) {
            is Option.Some -> this.element.hashCode()
            is Option.None -> 0
        }
    }

}

fun <T : Any> optionOf(value: T?): Option<T> {
    return if (value == null) Option.None else Option.Some(value)
}


fun tester() {
    val thingo: String? = null
    optionOf(thingo)
            .map { it + "ending" }
            .filter { it.length > 5 }
            .ifSome { System.out.println(it) }
}
