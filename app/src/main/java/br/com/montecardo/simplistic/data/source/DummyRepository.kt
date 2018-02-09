package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Node

/**
 * Dummy repository only meant to test the concept
 *
 * Created by gabryel on 01/02/18.
 */
class DummyRepository : Repository {

    private val itemsMap: Map<Node, List<Node>>

    private val root = Node(description = "")

    init {
        val neverSawItComing = root.createChild("Awesome SubList")

        val emptyList = root.createChild("Empty List")

        val multilevelFirstSubList = root.createChild("Multilevel 1st SubList")

        val multilevelSecSubList = multilevelFirstSubList.createChild("Multilevel 2nd SubList")

        val multilevelFirstSubListItems = listOf(multilevelSecSubList)

        val rootItems = listOf(multilevelFirstSubList,
            neverSawItComing, emptyList,
            root.createChild("Test"))

        val neverSawItComingItems = listOf(
                neverSawItComing.createChild("You'll never see it coming"),
                neverSawItComing.createChild("You'll see that my mind is too fast for eyes"),
                neverSawItComing.createChild("You're done in"),
                neverSawItComing.createChild("By the time it's hit you, your last surprise"))

        itemsMap = mapOf(root to rootItems,
                neverSawItComing to neverSawItComingItems,
                multilevelFirstSubList to multilevelFirstSubListItems,
                multilevelSecSubList to listOf(multilevelSecSubList.createChild("Hoisted!")))
    }

    override fun getRootItems() = getSubItems(root)

    override fun getSubItems(node: Node): List<Node> = itemsMap[node]?: emptyList()
}