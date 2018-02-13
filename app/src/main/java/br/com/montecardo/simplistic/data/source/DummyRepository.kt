package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node

/**
 * Dummy repository only meant to test the concept
 *
 * Created by gabryel on 01/02/18.
 */
class DummyRepository : Repository {

    private val itemsMap: MutableMap<Node?, MutableList<Node>> = mutableMapOf()

    init {
        val neverSawItComing = Node(description = "Awesome SubList")

        val multilevelFirstSubList = Node(description = "Multilevel 1st SubList")

        val multilevelSecond = multilevelFirstSubList.createChild("Multilevel 2.5nd SubList")

        listOf(
            Node(description = "Empty List"),
            Node(description = "Test"),

            multilevelFirstSubList,
            multilevelFirstSubList.createChild("Multilevel 2nd SubList"),

            multilevelSecond,
            multilevelSecond.createChild("Hoisted!"),

            neverSawItComing,
            neverSawItComing.createChild("You'll never see it coming"),
            neverSawItComing.createChild("You'll see that my mind is too fast for eyes"),
            neverSawItComing.createChild("You're done in"),
            neverSawItComing.createChild("By the time it's hit you, your last surprise")
        ).forEach { saveNode(it) }
    }

    override fun getSubItems(node: Node?): List<Node> = itemsMap[node]?: emptyList()

    override fun saveNode(node: Node) {
        itemsMap.getOrPut(node.parent) { mutableListOf() }.add(node)
    }
}