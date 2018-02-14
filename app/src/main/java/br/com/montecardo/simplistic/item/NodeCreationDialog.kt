package br.com.montecardo.simplistic.item

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import br.com.montecardo.simplistic.R
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import kotlinx.android.synthetic.main.dialog_node.*

/**
 * Fragment used to represent a component that can add movements
 *
 * Created by gabryel on 12/02/18.
 */
class NodeCreationDialog : DialogFragment() {
    interface Listener {
        fun onCreateNode(nodeData: NodeData)
    }

    private lateinit var listener: Listener

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = this.activity?: throw IllegalStateException()
        val view = activity.layoutInflater.inflate(R.layout.dialog_node, null)

        return AlertDialog.Builder(activity).setView(view)
            .setNegativeButton(getString(R.string.button_cancel), { _, _ -> } )
            .setPositiveButton(getString(R.string.button_confirm), { _, _ -> confirm() } )
            .create()
    }

    private fun confirm() {
        val desc = dialog.node_dlg_description.text.toString()

        listener.onCreateNode(NodeData(desc))
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = try {
            context as Listener
        } catch (ex: ClassCastException) {
            throw ClassCastException("${context::class.simpleName} must implement Listener")
        }
    }
}