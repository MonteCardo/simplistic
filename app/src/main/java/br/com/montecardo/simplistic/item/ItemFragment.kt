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
import kotlinx.android.synthetic.main.fragment_item.*

class ItemFragment : Fragment(), ItemContract.PageView {

    interface ItemSelectionListener { fun onItemSelection(node: Node) }

    private lateinit var listener: ItemSelectionListener

    lateinit var presenter: ItemContract.PagePresenter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_item, container, false)
    }

    override fun setListPresenter(presenter: ItemContract.ListPresenter) {
        val adapter = ItemAdapter(presenter)

        list_view.layoutManager = LinearLayoutManager(context)
        list_view.adapter = adapter
        presenter.adapter = adapter
    }

    override fun select(node: Node) {
        listener.onItemSelection(node)
    }

    override fun onResume() {
        super.onResume()
        presenter.subscribe()
    }

    override fun onPause() {
        super.onPause()
        presenter.unsubscribe()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)

        listener = context as? ItemSelectionListener?:
            throw IllegalArgumentException("Can only be attached to ${ItemSelectionListener::class.simpleName}")
    }

    companion object {
        @JvmStatic
        fun newInstance(pagePresenter: ItemContract.PagePresenter): ItemFragment {
            val frag = ItemFragment().apply {
                presenter = pagePresenter
            }

            pagePresenter.view = frag
            return frag
        }
    }
}
