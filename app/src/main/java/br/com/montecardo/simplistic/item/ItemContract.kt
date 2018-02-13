package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.base.Presenter
import br.com.montecardo.simplistic.base.View
import br.com.montecardo.simplistic.data.Node

object ItemContract {
    interface PageView : View {
        fun setListPresenter(presenter: ItemContract.ListPresenter)

        fun setNodeDescription(description: String?)

        fun select(node: Node)
    }

    interface PagePresenter : Presenter<PageView> {
        data class NodeCreationData(val nodeDescription: String)

        fun generateNode(data: NodeCreationData)
    }

    interface ListPresenter : Presenter<ListView> {
        fun bind(holder: ItemView, position: Int)

        fun replaceData(items: List<Node>)

        fun getRowCount(): Int
    }

    interface ListView : View {
        fun reportChange()
    }

    interface ItemView : View {
        fun setDescription(description: String)

        fun setOnClickListener(onClickListener: () -> Unit)
    }
}