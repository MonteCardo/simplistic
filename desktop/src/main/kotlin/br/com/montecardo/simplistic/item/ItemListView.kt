package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import javafx.event.ActionEvent
import javafx.event.EventTarget
import javafx.scene.control.ListView
import javafx.scene.input.KeyCode
import javafx.scene.layout.Priority
import tornadofx.*

class ItemListView(private val listener: Listener, private val presenter: ItemContract.ListPresenter) :
    ListView<ListItem<Node>>(), ItemContract.ListView {

    private var shouldFocusOnCreation = false

    interface Listener : EventTarget {
        fun createNode(node: ItemContract.PagePresenter.NodeData)
    }

    init {
        isEditable = true
        attachTo(listener)
        cellFormat {
            when (it) {
                is ListItem.Existent -> presenter.bind(ItemHolder(this), it.item)
                is ListItem.Creator -> graphic = createAddNewButton()
            }
        }

        hgrow = Priority.ALWAYS
    }

    private fun createAddNewButton() =
        form {
            textfield("+ Add item") {
                var firstTime = true
                setOnMouseClicked { fireEvent(ActionEvent()) }

                setOnAction {
                    if (firstTime) text = ""
                    firstTime = false
                    requestFocus()
                }

                setOnKeyPressed {
                    if (it.code == KeyCode.ENTER) {
                        listener.createNode(NodeData(text))
                    }
                }

                if (shouldFocusOnCreation) {
                    fireEvent(ActionEvent())
                }
            }
        }

    fun giveFocusToNewItem() { shouldFocusOnCreation = true }

    override fun replaceData(items: List<Node>) = setItems(convertToList(items).observable())

    private fun convertToList(items: List<Node>): List<ListItem<Node>> =
        items.map { ListItem.Existent(it) } + ListItem.Creator()
}