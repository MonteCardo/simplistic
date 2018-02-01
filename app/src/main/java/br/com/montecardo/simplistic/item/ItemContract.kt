package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import br.com.montecardo.simplistic.data.Item

object ItemContract {
    interface PageView {
        fun setListPresenter(presenter: ItemContract.ListPresenter)
    }

    interface PagePresenter {
        fun subscribe()

        fun unsubscribe()

        fun load(listing: Item.Listing)

        var view: PageView
    }

    interface ListPresenter {
        fun bind(holder: ItemView, position: Int)

        fun replaceData(items: List<Item>)

        fun getRowCount(): Int

        // TODO Remove this link to Android library
        var adapter: RecyclerView.Adapter<ItemAdapter.ItemHolder>
    }

    interface ItemView {
        fun setDescription(description: String)

        fun setOnClickListener(listener: (android.view.View) -> Unit)
    }
}