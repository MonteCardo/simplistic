package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.Repository
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData

class ItemPagePresenter(private val repository: Repository) :
    ItemContract.PagePresenter {

    private var presenter: ItemContract.ListPresenter? = null

    private var view: ItemContract.PageView? = null

    private var nodeId: Long? = null

    override fun onAttach(view: ItemContract.PageView, state: ItemContract.PageState) {
        this.view = view
        nodeId = state.nodeId
        val node = repository.getNode(nodeId)
        view.setDescription(node?.description)

        with(ItemListPresenter(repository.getSubItems(nodeId))) {
            presenter = this
            view.bindListPresenter(this)
        }
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
        view?.changeFocusAfterCreation()
    }

    override fun removeNode(nodeId: Long) {
        repository.removeNode(nodeId)
        refresh()
    }

    inner class ItemListPresenter(private var items: List<Node> = emptyList()) : ItemContract.ListPresenter {

        private var view: ItemContract.ListView? = null

        override fun bind(holder: ItemContract.ItemView, item: Node) {
            holder.setDescription(item.description)
            holder.setSelectListener { load(item.id) }
            holder.setRemovalPermissionListener { askToRemove(item) }
        }

        private fun refresh() = view?.replaceData(items)

        override fun replaceData(items: List<Node>) {
            this.items = items
            refresh()
        }

        override fun onAttach(view: ItemContract.ListView) {
            this.view = view
            refresh()
        }

        override fun onDetach() { view = null }
    }
}