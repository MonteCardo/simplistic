package br.com.montecardo.simplistic.data

sealed class Item {

    abstract val parent: Listing?

    abstract val description: String

    // TODO Think in a better name, though List, but it would be confusing
    // to use them alongside java.util.List
    data class Listing(override val parent: Listing? = null,
                       override val description: String) : Item() {

        fun createLeaf(content: String) = Leaf(this, content)

        fun createList(description: String) = Listing(this, description)
    }

    data class Leaf(override val parent: Listing, val content: String) : Item() {
        override val description = content
    }
}