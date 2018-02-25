package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import javafx.event.EventTarget
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import javafx.scene.control.ListView
import javafx.scene.layout.Priority
import tornadofx.*

class ItemListView(view: EventTarget, private val presenter: ItemContract.ListPresenter) :
    ListView<Node>(), ItemContract.ListView {

    init {
        attachTo(view)
        cellFormat {
            val holder = ItemHolder(this)
            presenter.bind(holder, it)
        }

        hgrow = Priority.ALWAYS
    }

    override fun replaceData(items: List<Node>) = setItems(items.observable())

    class ItemHolder(private val view: ListCell<Node>) : ItemContract.ItemView {

        private lateinit var descriptionField: Label

        init {
            view.graphic = view.cache {
                hbox {
                    descriptionField = label("sd")
                }
            }
        }

        override fun setDescription(description: String) {
            descriptionField.text = description
        }

        override fun setSelectListener(listener: () -> Unit) {
            view.setOnMouseClicked { listener() }
        }

        override fun setRemovalPermissionListener(listener: () -> Unit) {
            // view.onDoubleClick { listener() }
        }
    }
}