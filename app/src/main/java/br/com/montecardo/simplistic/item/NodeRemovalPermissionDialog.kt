package br.com.montecardo.simplistic.item

import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.support.v7.app.AlertDialog
import br.com.montecardo.simplistic.R
import kotlinx.android.synthetic.main.dialog_node_removal.view.*

/**
 * Fragment used to represent a component that can add movements
 *
 * Created by gabryel on 12/02/18.
 */
class NodeRemovalPermissionDialog : DialogFragment() {
    interface Listener {
        fun removeNode(nodeId: Long)
    }

    private lateinit var listener: Listener

    private val nodeId: Long by lazy {
        arguments?.getLong(NODE_ID_KEY)?: throw IllegalStateException("No node id was given")
    }

    private val nodeName: String by lazy {
        arguments?.getString(NODE_NAME_KEY)?: throw IllegalStateException("No node name was given")
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val activity = this.activity?: throw IllegalStateException()
        val view = activity.layoutInflater.inflate(R.layout.dialog_node_removal, null)

        view.dlg_node_removal_text.text =
            getString(R.string.node_removal_confirmation_template, nodeName)

        return AlertDialog.Builder(activity).setView(view)
            .setNegativeButton(getString(R.string.button_cancel), { _, _ -> } )
            .setPositiveButton(getString(R.string.button_confirm), { _, _ -> listener.removeNode(nodeId) } )
            .create()
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)

        listener = try {
            context as Listener
        } catch (ex: ClassCastException) {
            throw ClassCastException("${context::class.simpleName} must implement Listener")
        }
    }

    companion object {
        private val NODE_ID_KEY = "NODE_ID"

        private val NODE_NAME_KEY = "NODE_NAME"

        @JvmStatic
        fun newInstance(nodeName: String, nodeId: Long): NodeRemovalPermissionDialog {
            return NodeRemovalPermissionDialog().apply {
                arguments = Bundle().apply {
                    putLong(NODE_ID_KEY, nodeId)
                    putString(NODE_NAME_KEY, nodeName)
                }
            }
        }
    }
}