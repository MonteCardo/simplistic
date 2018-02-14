package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData

class ItemPagePresenter(private val repository: Repository,
                        private val nodeId: Long? = null) :
    ItemContract.PagePresenter {

    private var presenter: ItemContract.ListPresenter? = null

    private var view: ItemContract.PageView? = null

    private val currentNode = repository.getNode(nodeId)

    override fun onAttach(view: ItemContract.PageView) {
        val presenter = ItemListPresenter(repository.getSubItems(nodeId))
        this.presenter = presenter
        this.view = view

        view.bindListPresenter(presenter)
        view.setDescription(currentNode?.description)
    }

    override fun onDetach() {
        presenter?.onDetach()
        view = null
    }

    private fun load(nodeId: Long) {
        view?.select(nodeId)
    }

    private fun askToRemove(node: Node) {
        view?.showRemovalDialog(node)
    }

    private fun refresh() {
        presenter?.replaceData(repository.getSubItems(nodeId))
    }

    override fun generateNode(data: NodeData) {
        repository.saveNode(Node(nodeId, data.nodeDescription))
        refresh()
    }

    override fun removeNode(nodeId: Long) {
        repository.removeNode(nodeId)
        refresh()
    }

    inner class ItemListPresenter(private var items: List<Node>) :
        ItemContract.ListPresenter {

        private var view: ItemContract.ListView? = null

        override fun bind(holder: ItemContract.ItemView, position: Int) {
            val item = items[position]

            holder.setDescription(item.description)
            holder.setSelectListener { load(item.id) }
            holder.setRemovalPermissionListener { askToRemove(item) }
        }

        override fun replaceData(items: List<Node>) {
            this.items = items
            view?.reportChange()
        }

        override fun onAttach(view: ItemContract.ListView) { this.view = view }

        override fun onDetach() { view = null }

        override fun getRowCount() = items.size
    }
}