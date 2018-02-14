package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.base.Presenter
import br.com.montecardo.simplistic.base.View
import br.com.montecardo.simplistic.data.Node

object ItemContract {
    interface PageView : View {
        /**
         * Binds list presenter to the view
         *
         * @param presenter Presenter to be bound to view
         */
        fun bindListPresenter(presenter: ItemContract.ListPresenter)

        /**
         * Set the given description to the view
         *
         * @param description Description to be used
         */
        fun setDescription(description: String?)

        /**
         * Acts over teh selection of a given node
         *
         * @param nodeId Id of the selected node
         */
        fun select(nodeId: Long)
    }

    interface PagePresenter : Presenter<PageView> {
        /**
         * Data class that declares the needed info to create a new node
         */
        data class NodeData(val nodeDescription: String)

        /**
         * Persists a node
         *
         * @param data Information to create the new node
         */
        fun generateNode(data: NodeData)
    }

    interface ListPresenter : Presenter<ListView> {
        /**
         * Bind the item on the given position to the given view
         *
         * @param holder View that will carry the needed info
         * @param position Position of the item internally
         */
        fun bind(holder: ItemView, position: Int)

        /**
         * Replaces the current list with a new one
         *
         * @param items New list
         */
        fun replaceData(items: List<Node>)

        /**
         * @return Count of items in the presenter
         */
        fun getRowCount(): Int
    }

    interface ListView : View {
        /**
         * Reports that the view should reset its contents
         */
        fun reportChange()
    }

    interface ItemView : View {
        /**
         * Set the given description to the view
         *
         * @param description Description to be used
         */
        fun setDescription(description: String)

        /**
         * Prepares the action that will be executed when the item is clicked
         *
         * @param listener Action to be executed on item selection
         */
        fun setOnClickListener(listener: () -> Unit)
    }
}