package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node
import kdbc.*

class SelectNode : Query<Node>() {
    init {
        select(NodeTable)
        from(NodeTable)
    }

    fun getRootNodes() = list {
        where { + "parent_id is null" }
    }

    fun getSubNodes(nodeId: Long) = list {
        where { NodeTable.parentId eq nodeId }
    }

    fun byId(id: Long) = firstOrNull { whereIdEquals(id) }

    override fun get() = Node(
        id = NodeTable.id()!!,
        parentId = NodeTable.parentId(),
        description = NodeTable.description()!!
    )
}

class DeleteNode(nodeId: Long) : Delete() {
    init {
        delete(NodeTable) { whereIdEquals(nodeId) }
    }
}

class InsertNode(node: Node) : Insert() {
    init { insert(NodeTable) { setNode(node) } }
}

class UpdateNode(node: Node) : Update() {
    init {
        update(NodeTable) {
            setNode(node)
            whereIdEquals(node.id)
        }
    }
}

private fun Expr.setNode(node: Node) {
    node.parentId?.let { NodeTable.parentId eq node.parentId }
    NodeTable.description eq node.description
}

private fun Expr.whereIdEquals(nodeId: Long) {
    where { NodeTable.id eq nodeId }
}