package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node

class DummyRepository : Repository {

    private val parentIdRepo = mutableMapOf<Long?, MutableList<Node>>()

    private val idRepo = mutableMapOf<Long, Node>()

    init {
        val multilevel = Node(description = "test", id = 1)

        listOf(
            multilevel,
            Node(description = "second", id = 2),
            Node(multilevel.id, "entering", id = 3)
        ).forEach { saveNode(it) }
    }

    override fun getSubItems(nodeId: Long?) = parentIdRepo[nodeId] ?: mutableListOf()

    override fun getNode(nodeId: Long?) = idRepo[nodeId]

    override fun saveNode(node: Node) {
        parentIdRepo.getOrPut(node.parentId) { mutableListOf() }.add(node)
        idRepo[node.id] = node
    }

    override fun removeNode(nodeId: Long) {
        throw UnsupportedOperationException("not implemented")
    }
}