package br.com.montecardo.simplistic

import br.com.montecardo.simplistic.data.source.PostgresDatabaseSetup
import br.com.montecardo.simplistic.item.ItemFragment
import javafx.scene.paint.Color
import tornadofx.*

class SimplisticApp : App(Workspace::class, Styles::class) {
    override fun init() {
        PostgresDatabaseSetup.setup()
    }

    override fun onBeforeShow(view: UIComponent) {
        workspace.dock<ItemFragment>()
    }
}

class Styles : Stylesheet() {
    init {
        val noPadding = box(Dimension(0.0, Dimension.LinearUnits.px))
        listCell {
            form {
                backgroundColor = MultiValue(arrayOf(Color.TRANSPARENT))
                padding = noPadding

                textField {
                    backgroundColor = MultiValue(arrayOf(Color.TRANSPARENT))
                    padding = noPadding
                }
            }
        }
    }
}