package br.com.montecardo.simplistic.item

import android.content.Context
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.montecardo.simplistic.R
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.di.ActivityScoped
import br.com.montecardo.simplistic.ext.getNullableLong
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import dagger.android.support.DaggerFragment
import kotlinx.android.synthetic.main.fragment_item.*
import javax.inject.Inject

@ActivityScoped
class ItemFragment : DaggerFragment(), ItemContract.PageView {

    interface ItemFragmentListener {
        var removalListener: (Long) -> Unit

        var creationListener: (NodeData) -> Unit

        fun askForRemovalConfirmation(node: Node)

        fun onItemSelection(nodeId: Long)

        fun setTabName(name: String?)
    }

    private lateinit var listener: ItemFragmentListener

    @Inject lateinit var presenter: PagePresenter

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
        val nodeId = arguments.getNullableLong(NODE_ID_KEY)

        presenter.onAttach(this, ItemContract.PageState(nodeId))
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
        val NODE_ID_KEY = "NODE_ID"

        @JvmStatic
        fun newInstance(nodeId: Long?): ItemFragment {
            return ItemFragment().apply {
                arguments = Bundle().apply {
                    if (nodeId != null) putLong(NODE_ID_KEY, nodeId)
                }
            }
        }
    }
}
