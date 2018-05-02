package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.PostgresRepository
import tornadofx.Fragment
import tornadofx.hbox

class ItemFragment : Fragment(), ItemContract.PageView, ItemListView.Listener {

    private val presenter = ItemPagePresenter(PostgresRepository())

    private var listView: ItemListView? = null

    private val nodeId: Long? by param()

    override val root = hbox { }

    override fun onDock() {
        super.onDock()
        presenter.onAttach(this, ItemContract.PageState(nodeId))
    }

    override fun onUndock() {
        super.onUndock()
        presenter.onDetach()
    }

    override fun bindListPresenter(presenter: ItemContract.ListPresenter) {
        val view = listView ?: ItemListView(this, presenter)
        listView = view
        presenter.onAttach(view)
    }

    override fun setDescription(description: String?) {
        title = description ?: "Simplistic"
    }

    override fun changeFocusAfterCreation() {
        listView?.giveFocusToNewItem()
    }

    override fun select(nodeId: Long) =
        workspace.dockInNewScope<ItemFragment>(mapOf(ItemFragment::nodeId to nodeId))

    override fun showRemovalDialog(node: Node) =
        throw UnsupportedOperationException("not implemented")

    override fun createNode(node: ItemContract.PagePresenter.NodeData) =
        presenter.generateNode(node)
}
