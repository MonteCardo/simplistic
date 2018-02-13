package br.com.montecardo.simplistic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.montecardo.simplistic.data.Node
import br.com.montecardo.simplistic.data.source.DummyRepository
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeCreationData
import br.com.montecardo.simplistic.item.ItemFragment
import br.com.montecardo.simplistic.item.ItemPagePresenter
import br.com.montecardo.simplistic.item.NodeCreationDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ItemFragment.ItemFragmentListener,
    NodeCreationDialog.NodeCreationListener {

    override var creationListener: (NodeCreationData) -> Unit = { }

    private val repository = DummyRepository()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.placeholder, ItemFragment.newInstance(ItemPagePresenter(repository)))
            .commit()

        add_fab.setOnClickListener {
            NodeCreationDialog().show(supportFragmentManager, NodeCreationDialog::class.simpleName)
        }
    }

    override fun onItemSelection(node: Node) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, ItemFragment.newInstance(ItemPagePresenter(repository, node)))
            .addToBackStack(null)
            .commit()
    }

    override fun onDialogPositiveClick(nodeData: NodeCreationData) {
        creationListener(nodeData)
    }

    override fun onDialogNegativeClick() { }

    override fun setTabName(name: String?) {
        toolbar.title = name?: applicationContext.getString(R.string.title_activity_main)
    }
}
