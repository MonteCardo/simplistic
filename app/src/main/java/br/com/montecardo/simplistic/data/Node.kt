package br.com.montecardo.simplistic.data

data class Node(val parent: Node? = null, val description: String) {

    fun createLeaf(content: String) = Node(this, content)

    fun createList(description: String) = Node(this, description)
}