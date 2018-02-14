package br.com.montecardo.simplistic.item

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.montecardo.simplistic.R
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : Fragment(), ItemContract.PageView {

    interface ItemFragmentListener {
        var removalListener: (Long) -> Unit

        var creationListener: (NodeData) -> Unit

        fun askForRemovalConfirmation(node: Node)

        fun onItemSelection(nodeId: Long)

        fun setTabName(name: String?)
    }

    private lateinit var listener: ItemFragmentListener

    lateinit var presenter: PagePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun bindListPresenter(presenter: ItemContract.ListPresenter) {
        val adapter = ItemAdapter(presenter)

        list_view.layoutManager = LinearLayoutManager(context)
        list_view.adapter = adapter
        presenter.onAttach(adapter)
    }

    override fun setDescription(description: String?) = listener.setTabName(description)

    override fun select(nodeId: Long) = listener.onItemSelection(nodeId)

    override fun showRemovalDialog(node: Node) = listener.askForRemovalConfirmation(node)

    override fun onResume() {
        super.onResume()
        presenter.onAttach(this)
        listener.removalListener = presenter::removeNode
        listener.creationListener = presenter::generateNode
    }

    override fun onPause() {
        super.onPause()
        presenter.onDetach()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = context as? ItemFragmentListener ?:
            throw IllegalArgumentException("Can only be attached to ${ItemFragmentListener::class.simpleName}")
    }

    companion object {
        @JvmStatic
        fun newInstance(pagePresenter: PagePresenter): ItemFragment {
            return ItemFragment().apply {
                presenter = pagePresenter
            }
        }
    }
}
