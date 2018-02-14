package br.com.montecardo.simplistic.data.source.room

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.ForeignKey
import android.arch.persistence.room.PrimaryKey
import br.com.montecardo.simplistic.data.Node

@Entity(
    tableName = "node",
    foreignKeys = arrayOf(ForeignKey(entity = RoomNode::class,
    parentColumns = arrayOf("id"),
    childColumns = arrayOf("parent_id"),
    onDelete = ForeignKey.CASCADE)))
data class RoomNode(@ColumnInfo(name = "id") @PrimaryKey(autoGenerate = true) var id: Long = 0,

               @ColumnInfo(name = "parent_id") var parentId: Long?,
               @ColumnInfo(name = "description") var description: String)

fun RoomNode.toCore() = Node(id = id, parentId = parentId, description = description)

fun Node.toRoom() = RoomNode(id = id, parentId = parentId, description = description)