package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import javafx.scene.control.Label
import javafx.scene.control.ListCell
import tornadofx.*

class ItemHolder(private val view: ListCell<ListItem<Node>>) : ItemContract.ItemView {

    init {
        view.graphic = view.cache {
            hbox {
                label { addClass("description") }
            }
        }
    }

    override fun setDescription(description: String) {
        findDescriptionField().text = description
    }

    override fun setSelectListener(listener: () -> Unit) {
        view.setOnMouseClicked { listener() }
    }

    override fun setRemovalPermissionListener(listener: () -> Unit) {
        // listener.onDoubleClick { listener() }
    }

    private fun findDescriptionField(): Label = view.select(CssRule.c("description"))
}