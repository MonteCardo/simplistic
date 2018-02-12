package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import br.com.montecardo.simplistic.data.Node

object ItemContract {
    interface PageView {
        fun setListPresenter(presenter: ItemContract.ListPresenter)

        fun setNodeDescription(description: String?)

        fun select(node: Node)
    }

    interface PagePresenter {
        fun load(node: Node)

        fun unsubscribe()

        fun subscribe(view: PageView)
    }

    interface ListPresenter {
        fun bind(holder: ItemView, position: Int)

        fun replaceData(items: List<Node>)

        fun getRowCount(): Int

        fun unsubscribe()

        fun subscribe(view: ListView)
    }

    interface ListView {
        fun reportChange()
    }

    interface ItemView {
        fun setDescription(description: String)

        fun setOnClickListener(listener: (android.view.View) -> Unit)
    }
}