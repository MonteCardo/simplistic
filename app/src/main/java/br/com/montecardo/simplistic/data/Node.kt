package br.com.montecardo.simplistic.data

data class Node(val parent: Node? = null, val description: String) {
    fun createChild(description: String) = Node(this, description)
}