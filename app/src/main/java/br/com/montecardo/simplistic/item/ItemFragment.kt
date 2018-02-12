package br.com.montecardo.simplistic.item

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.montecardo.simplistic.R
import br.com.montecardo.simplistic.data.Node
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : Fragment(), ItemContract.PageView {

    interface ItemFragmentListener {
        fun onItemSelection(node: Node)

        fun setTabName(name: String?)
    }

    private lateinit var listener: ItemFragmentListener

    lateinit var presenter: ItemContract.PagePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun setListPresenter(presenter: ItemContract.ListPresenter) {
        val adapter = ItemAdapter(presenter)

        list_view.layoutManager = LinearLayoutManager(context)
        list_view.adapter = adapter
        presenter.subscribe(adapter)
    }

    override fun setNodeDescription(description: String?) = listener.setTabName(description)

    override fun select(node: Node) = listener.onItemSelection(node)

    override fun onResume() {
        super.onResume()
        presenter.subscribe(this)
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = context as? ItemFragmentListener ?:
            throw IllegalArgumentException("Can only be attached to ${ItemFragmentListener::class.simpleName}")
    }

    companion object {
        @JvmStatic
        fun newInstance(pagePresenter: ItemContract.PagePresenter): ItemFragment {
            return ItemFragment().apply {
                presenter = pagePresenter
            }
        }
    }
}
