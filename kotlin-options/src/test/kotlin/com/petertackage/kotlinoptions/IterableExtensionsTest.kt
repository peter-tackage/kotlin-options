package com.petertackage.kotlinoptions

import org.junit.Test

class IterableExtensionsTest {

    @Test
    fun `filterIfSome includes when Some`() {
        val option = optionOf("abc")
        val list: Iterable<Option<String>> = listOf(option)

        val filteredList: Iterable<String> = list.filterIfSome()

        assert(filteredList.count() == list.count())
        assert(filteredList.contains(option.toNullable()!!))
    }

    @Test
    fun `filterIfSome excludes when None`() {
        val option = None
        val list: Iterable<Option<String>> = listOf(option)

        val filteredList: Iterable<String> = list.filterIfSome()

        assert(filteredList.count() == 0)
    }

    @Test
    fun `filterIfSome with predicate includes when Some and predicate True`() {
        val option = optionOf("abc")
        val list: Iterable<Option<String>> = listOf(option)
        val predicate: (String) -> Boolean = { true }

        val filteredList: Iterable<String> = list.filterIfSome(predicate)

        assert(filteredList.count() == list.count())
        assert(filteredList.contains(option.toNullable()!!))
    }

    @Test
    fun `filterIfSome with predicate excludes when Some and predicate False`() {
        val option = optionOf("abc")
        val list: Iterable<Option<String>> = listOf(option)
        val predicate: (String) -> Boolean = { false }

        val filteredList: Iterable<String> = list.filterIfSome(predicate)

        assert(filteredList.count() == 0)
    }

    @Test
    fun `filterIfSome with predicate excludes when None and predicate True`() {
        val list: Iterable<Option<String>> = listOf(None)
        val predicate: (String) -> Boolean = { true }

        val filteredList: Iterable<String> = list.filterIfSome(predicate)

        assert(filteredList.count() == 0)
    }

    @Test
    fun `filterIfSome with predicate excludes when None and predicate False`() {
        val list: Iterable<Option<String>> = listOf(None)
        val predicate: (String) -> Boolean = { false }

        val filteredList: Iterable<String> = list.filterIfSome(predicate)

        assert(filteredList.count() == 0)
    }
}