package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node

interface Repository {
    fun getSubItems(node: Node?): List<Node>

    fun saveNode(node: Node)
}