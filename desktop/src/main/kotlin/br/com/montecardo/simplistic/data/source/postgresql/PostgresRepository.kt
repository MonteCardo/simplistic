package br.com.montecardo.simplistic.data.source.postgresql

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository
import java.sql.PreparedStatement
import java.sql.ResultSet

/**
 * @author Gabryel Monteiro (Last Modified By: $Author: gabryel $)
 * @version $Id: v 1.1 Apr 02, 2018 gabryel Exp $
 */
class PostgresRepository(host: String, database: String, user: String, password: String) : Repository {

    private val connection = PostgresDatabase(host, database, user, password).connection

    override fun getSubItems(nodeId: Long?): List<Node> {
        (nodeId?.let { getSubNodes(it) } ?: getRootNodes()).use {
            it.execute()
            val set = it.resultSet

            return generateSequence {
                if (!set.next()) null
                else generateNode(set)
            }.toList()
        }
    }

    override fun getNode(nodeId: Long?): Node? {
        return nodeId?.let { id ->
            connection.prepareStatement("select * from node where id = ?").use {
                it.setLong(1, id)
                val set = it.executeQuery()
                set.next()

                generateNode(set)
            }
        }
    }

    override fun saveNode(node: Node) {
        if (node.id == 0L) {
            createCreateStatement(node)
        } else {
            createUpdateStatement(node)
        }.use { it.execute() }
    }

    override fun removeNode(nodeId: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    private fun getRootNodes(): PreparedStatement =
        connection.prepareStatement("SELECT * FROM node WHERE parent_id IS NULL")

    private fun getSubNodes(nodeId: Long): PreparedStatement =
        connection.prepareStatement("SELECT * FROM node WHERE parent_id = ?").apply {
            setLong(1, nodeId)
        }

    private fun createCreateStatement(node: Node): PreparedStatement =
        connection.prepareStatement("INSERT INTO node (description, parent_id) values (?, ?)").apply {
            setString(1, node.description)
            node.parentId?.let { setLong(2, it) } ?: setNull(2, java.sql.Types.BIGINT)
        }

    private fun createUpdateStatement(node: Node): PreparedStatement =
        connection.prepareStatement("UPDATE node SET description = ?, parent_id = ? WHERE id = ?").apply {
            setString(1, node.description)

            node.parentId?.let { setLong(2, it) } ?: setNull(2, java.sql.Types.BIGINT)
            setLong(3, node.id)
        }

    private fun generateNode(set: ResultSet): Node {
        return Node(id = set.getLong("id"),
            description = set.getString("description"),
            parentId = set.getLong("parent_id"))
    }
}