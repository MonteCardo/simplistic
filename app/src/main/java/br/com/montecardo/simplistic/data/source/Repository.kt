package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node

interface Repository {
    /**
     * @param nodeId Id to search children of
     * @return List of nodes that are immediately bellow the given id
     */
    fun getSubItems(nodeId: Long?): List<Node>

    /**
     * @param nodeId Id to search children of
     * @return Optional node of the id
     */
    fun getNode(nodeId: Long?): Node?

    /**
     * Creates a new node with the given info if the node is new, otherwise updates the old one
     *
     * @param node Node to be persisted
     */
    fun saveNode(node: Node)
}