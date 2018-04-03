package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository
import com.oracle.util.Checksums.update
import kdbc.*

class PostgresRepository : Repository {

    override fun getSubItems(nodeId: Long?): List<Node> {
        nodeId?: return SelectNode().getRootNodes()
        return SelectNode().getSubNodes(nodeId)
    }

    override fun getNode(nodeId: Long?): Node? {
        nodeId?: return null
        return SelectNode().byId(nodeId)
    }

    override fun saveNode(node: Node) {
        if (node.id == 0L) {
            InsertNode(node)
        } else {
            UpdateNode(node)
        }.execute()
    }

    override fun removeNode(nodeId: Long) {
        DeleteNode(nodeId).execute()
    }
}