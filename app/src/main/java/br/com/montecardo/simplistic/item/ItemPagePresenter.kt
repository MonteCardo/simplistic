package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository

class ItemPagePresenter(private val repository: Repository,
                        private val currentNode: Node? = null) :
    ItemContract.PagePresenter {

    private lateinit var presenter: ItemContract.ListPresenter

    override lateinit var view: ItemContract.PageView

    override fun subscribe() {
        val listContent =
            if (currentNode != null) repository.getSubItems(currentNode)
            else repository.getRootItems()

        presenter = ItemListPresenter(listContent)
        view.setListPresenter(presenter)

        view.setNodeDescription(currentNode?.description)
    }

    override fun unsubscribe() {
        // TODO Clear resources
    }

    override fun load(node: Node) {
        view.select(node)
    }

    inner class ItemListPresenter(private var items: List<Node>) :
        ItemContract.ListPresenter {

        override fun bind(holder: ItemContract.ItemView, position: Int) {
            val item = items[position]

            holder.setDescription(item.description)
            holder.setOnClickListener { load(item) }
        }

        override fun replaceData(items: List<Node>) {
            this.items = items
            adapter.notifyDataSetChanged()
        }

        override fun getRowCount() = items.size

        override lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemHolder>
    }
}