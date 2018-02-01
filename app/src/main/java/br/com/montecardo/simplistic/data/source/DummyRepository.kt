package br.com.montecardo.simplistic.data.source

import br.com.montecardo.simplistic.data.Item

/**
 * Dummy repository only meant to test the concept
 *
 * Created by gabryel on 01/02/18.
 */
class DummyRepository : Repository {

    private val itemsMap: Map<Item.Listing, List<Item>>

    private val root = Item.Listing(description = "")

    init {
        val neverSawItComing = root.createList("Awesome SubList")

        val emptyList = root.createList("Empty List")

        val multilevelFirstSubList = root.createList("Multilevel 1st SubList")

        val multilevelSecSubList = multilevelFirstSubList.createList("Multilevel 2nd SubList")

        val multilevelFirstSubListItems = listOf(multilevelSecSubList)

        val rootItems = listOf(multilevelFirstSubList,
            neverSawItComing, emptyList,
            root.createLeaf("Test"))

        val neverSawItComingItems = listOf(
                neverSawItComing.createLeaf("You'll never see it coming"),
                neverSawItComing.createLeaf("You'll see that my mind is too fast for eyes"),
                neverSawItComing.createLeaf("You're done in"),
                neverSawItComing.createLeaf("By the time it's hit you, your last surprise"))

        itemsMap = mapOf(root to rootItems,
                neverSawItComing to neverSawItComingItems,
                multilevelFirstSubList to multilevelFirstSubListItems,
                multilevelSecSubList to listOf(multilevelSecSubList.createLeaf("Hoisted!")))
    }

    override fun getRootItem() = itemsMap[root]?: emptyList()

    override fun getSubItems(listing: Item.Listing) = itemsMap[listing]
}