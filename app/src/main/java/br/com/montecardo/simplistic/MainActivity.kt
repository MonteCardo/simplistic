package br.com.montecardo.simplistic

import android.os.Bundle
import android.support.design.widget.Snackbar
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import br.com.montecardo.simplistic.item.ItemFragment
import br.com.montecardo.simplistic.item.NodeCreationDialog
import br.com.montecardo.simplistic.item.NodeRemovalPermissionDialog
import dagger.android.support.DaggerAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : DaggerAppCompatActivity(),
    ItemFragment.ItemFragmentListener,
    NodeCreationDialog.Listener,
    NodeRemovalPermissionDialog.Listener {

    override var creationListener: (NodeData) -> Unit = { }

    override var removalListener: (Long) -> Unit = { }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        if (supportFragmentManager.findFragmentById(R.id.placeholder) == null) {
            supportFragmentManager
                .beginTransaction()
                .add(R.id.placeholder, ItemFragment.newInstance(null))
                .commit()
        }

        add_fab.setOnClickListener {
            NodeCreationDialog()
                .show(supportFragmentManager, NodeCreationDialog::class.simpleName)
        }
    }

    override fun askForRemovalConfirmation(node: Node) {
        NodeRemovalPermissionDialog.newInstance(node.description, node.id)
            .show(supportFragmentManager, NodeRemovalPermissionDialog::class.simpleName)
    }

    override fun onItemSelection(nodeId: Long) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, ItemFragment.newInstance(nodeId))
            .addToBackStack(null)
            .commit()
    }

    override fun onCreateNode(nodeData: NodeData) = creationListener(nodeData)

    override fun removeNode(nodeId: Long) {
        removalListener(nodeId)
        Snackbar.make(main_layout,
            getString(R.string.node_removal_complete),
            Snackbar.LENGTH_SHORT).show()
    }

    override fun setTabName(name: String?) {
        toolbar.title = name?: applicationContext.getString(R.string.title_activity_main)
    }
}
