package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository

class ItemPagePresenter(private val repository: Repository) : ItemContract.PagePresenter {

    private lateinit var presenter: ItemContract.ListPresenter

    override lateinit var view: ItemContract.PageView

    override fun subscribe() {
        presenter = ItemListPresenter(repository.getRootItems())
        view.setListPresenter(presenter)
    }

    override fun unsubscribe() {
        // TODO Clear resources
    }

    override fun load(listing: Node) {
        val subItems = repository.getSubItems(listing)
        presenter.replaceData(subItems)
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