package br.com.montecardo.simplistic

import br.com.montecardo.simplistic.item.ItemFragment
import javafx.application.Application
import tornadofx.*

class SimplisticApp : App(Workspace::class) {
    override fun onBeforeShow(view: UIComponent) {
        with(workspace) {
            dock<ItemFragment>()

            listOf(saveButton, deleteButton, createButton)
                .forEach { it.removeFromParent() }

            saveButton.removeFromParent()
        }
    }

    companion object {
        @JvmStatic
        fun main(varargs: Array<String>) {
            Application.launch(SimplisticApp::class.java)
        }
    }
}