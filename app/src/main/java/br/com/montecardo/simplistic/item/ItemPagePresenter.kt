package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import br.com.montecardo.simplistic.data.Item
import br.com.montecardo.simplistic.data.source.Repository

class ItemPagePresenter(private val repository: Repository) : ItemContract.PagePresenter {

    private lateinit var presenter: ItemContract.ListPresenter

    override lateinit var view: ItemContract.PageView

    override fun subscribe() {
        presenter = ItemListPresenter(repository.getRootItem())
        view.setListPresenter(presenter)
    }

    override fun unsubscribe() {
        // TODO Clear resources
    }

    override fun load(listing: Item.Listing) {
        val subItems = repository.getSubItems(listing)?: emptyList()
        presenter.replaceData(subItems)
    }

    inner class ItemListPresenter(private var items: List<Item> = emptyList()) :
        ItemContract.ListPresenter {

        override fun bind(holder: ItemContract.ItemView, position: Int) {
            val item = items[position]

            holder.setDescription(item.description)

            when(item) {
                is Item.Listing -> holder.setOnClickListener { load(item) }
                is Item.Leaf -> holder.setOnClickListener {  }
            }
        }

        override fun replaceData(items: List<Item>) {
            this.items = items
            adapter.notifyDataSetChanged()
        }

        override fun getRowCount() = items.size

        override lateinit var adapter: RecyclerView.Adapter<ItemAdapter.ItemHolder>
    }
}