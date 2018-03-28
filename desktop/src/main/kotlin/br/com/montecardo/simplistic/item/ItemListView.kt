package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import javafx.event.EventTarget
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.input.KeyCode
import javafx.scene.layout.Priority
import tornadofx.*

class ItemListView(private val listener: Listener, private val presenter: ItemContract.ListPresenter) :
    ListView<ListItem<Node>>(), ItemContract.ListView {

    interface Listener : EventTarget {
        fun createNode(node: ItemContract.PagePresenter.NodeData)
    }

    init {
        isEditable = true
        attachTo(listener)
        cellFormat {
            when (it) {
                is ListItem.Existent -> presenter.bind(ItemHolder(this), it.item)
                is ListItem.Creator -> {
                    graphic = form {
                        textfield("+ Add item") {
                            setOnMouseClicked { text = "" }
                            setOnKeyPressed {
                                if (it.code == KeyCode.ENTER) {
                                    listener.createNode(NodeData(text))
                                }
                            }
                        }
                    }
                }
            }
        }

        hgrow = Priority.ALWAYS
    }

    override fun replaceData(items: List<Node>) = setItems(convertToList(items).observable())

    private fun convertToList(items: List<Node>): List<ListItem<Node>> =
        items.map { ListItem.Existent(it) } + ListItem.Creator()

    class ItemHolder(private val view: ListCell<ListItem<Node>>) : ItemContract.ItemView {

        private val descriptionField: Label

        init {
            view.graphic = view.cache {
                hbox {
                    label { addClass("description") }
                }
            }

            descriptionField = view.select(CssRule.c("description"))
        }

        override fun setDescription(description: String) {
            descriptionField.text = description
        }

        override fun setSelectListener(listener: () -> Unit) {
            view.setOnMouseClicked { listener() }
        }

        override fun setRemovalPermissionListener(listener: () -> Unit) {
            // listener.onDoubleClick { listener() }
        }
    }
}