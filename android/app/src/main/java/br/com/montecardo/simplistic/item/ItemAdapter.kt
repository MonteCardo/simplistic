package br.com.montecardo.simplistic.item

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import br.com.montecardo.simplistic.R
import br.com.montecardo.simplistic.data.Node
import kotlinx.android.synthetic.main.view_item.view.*

class ItemAdapter(private val presenter: ItemContract.ListPresenter) :
    RecyclerView.Adapter<ItemAdapter.ItemHolder>(), ItemContract.ListView {

    private var list: List<Node> = emptyList()

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        presenter.bind(holder, list[position])
    }

    override fun getItemCount() = list.size

    override fun replaceData(items: List<Node>) {
        list = items
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        val rowView = LayoutInflater.from(parent.context)
            .inflate(R.layout.view_item, parent, false)

        return ItemHolder(rowView)
    }

    class ItemHolder(view: View) : RecyclerView.ViewHolder(view), ItemContract.ItemView {

        override fun setDescription(description: String) {
            itemView.item_description.text = description
        }

        override fun setSelectListener(listener: () -> Unit) {
            itemView.setOnClickListener { listener() }
        }

        override fun setRemovalPermissionListener(listener: () -> Unit) {
            itemView.setOnLongClickListener {
                listener()
                true
            }
        }
    }
}