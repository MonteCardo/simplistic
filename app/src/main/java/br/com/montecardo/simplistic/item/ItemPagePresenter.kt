package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository

class ItemPagePresenter(private val repository: Repository,
                        private val currentNode: Node? = null) :
    ItemContract.PagePresenter {

    private var presenter: ItemContract.ListPresenter? = null

    private var view: ItemContract.PageView? = null

    override fun subscribe(view: ItemContract.PageView) {
        val listContent =
            if (currentNode != null) repository.getSubItems(currentNode)
            else repository.getRootItems()

        val presenter = ItemListPresenter(listContent)
        this.presenter = presenter
        this.view = view

        view.setListPresenter(presenter)
        view.setNodeDescription(currentNode?.description)
    }

    override fun unsubscribe() {
        presenter?.unsubscribe()
        view = null
    }

    override fun load(node: Node) {
        view?.select(node)
    }

    inner class ItemListPresenter(private var items: List<Node>) :
        ItemContract.ListPresenter {

        private var view: ItemContract.ListView? = null

        override fun bind(holder: ItemContract.ItemView, position: Int) {
            val item = items[position]

            holder.setDescription(item.description)
            holder.setOnClickListener { load(item) }
        }

        override fun replaceData(items: List<Node>) {
            this.items = items
            view?.reportChange()
        }

        override fun subscribe(view: ItemContract.ListView) { this.view = view }

        override fun unsubscribe() { view = null }

        override fun getRowCount() = items.size
    }
}