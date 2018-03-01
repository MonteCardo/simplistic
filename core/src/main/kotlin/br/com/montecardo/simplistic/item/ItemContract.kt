package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.base.PersistentPresenter
import br.com.montecardo.simplistic.base.Presenter
import br.com.montecardo.simplistic.base.State
import br.com.montecardo.simplistic.base.View
import br.com.montecardo.simplistic.data.Node

object ItemContract {

    data class PageState(val nodeId: Long?) : State

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
         * Acts over the selection of a given node
         *
         * @param nodeId Id of the selected node
         */
        fun select(nodeId: Long)

        /**
         * Asks for confirmation of deletion of given node
         *
         * @param node Node to ask for removal
         */
        fun showRemovalDialog(node: Node)
    }

    interface PagePresenter : PersistentPresenter<PageView, PageState> {
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

        /**
         * Removes a node
         *
         * @param nodeId Id of node to be deleted
         */
        fun removeNode(nodeId: Long)
    }

    interface ListPresenter : Presenter<ListView> {

        /**
         * Binds the given item to the given view
         *
         * @param holder View that will carry the needed info
         * @param item Item to be bound to view
         */
        fun bind(holder: ItemView, item: Node)

        /**
         * Replaces the current list with a new one
         *
         * @param items List to be used from now on
         */
        fun replaceData(items: List<Node>)
    }

    interface ListView : View {

        /**
         * Replaces the current list with a new one
         *
         * @param items List to be used from now on
         */
        fun replaceData(items: List<Node>)
    }

    interface ItemView : View {
        /**
         * Set the given description to the view
         *
         * @param description Description to be used
         */
        fun setDescription(description: String)

        /**
         * Prepares the action that will be executed when the item is selected
         *
         * @param listener Action to be executed on item selection
         */
        fun setSelectListener(listener: () -> Unit)

        /**
         * Prepares the action that will be executed when the item is selected for removal
         *
         * @param listener Action to be executed on item selection for removal
         */
        fun setRemovalPermissionListener(listener: () -> Unit)
    }
}