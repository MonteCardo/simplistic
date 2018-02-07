package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node

interface Repository {
    fun getRootItems(): List<Node>

    fun getSubItems(node: Node): List<Node>
}