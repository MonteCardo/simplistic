package br.com.montecardo.simplistic

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import br.com.montecardo.simplistic.data.source.room.RoomRepository
import br.com.montecardo.simplistic.item.ItemContract.PagePresenter.NodeData
import br.com.montecardo.simplistic.item.ItemFragment
import br.com.montecardo.simplistic.item.ItemPagePresenter
import br.com.montecardo.simplistic.item.NodeCreationDialog
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(),
    ItemFragment.ItemFragmentListener,
    NodeCreationDialog.NodeCreationListener {

    override var creationListener: (NodeData) -> Unit = { }

    private val repository = RoomRepository(this)

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

    override fun onItemSelection(nodeId: Long) {
        supportFragmentManager
            .beginTransaction()
            .replace(R.id.placeholder, ItemFragment.newInstance(ItemPagePresenter(repository, nodeId)))
            .addToBackStack(null)
            .commit()
    }

    override fun onDialogPositiveClick(nodeData: NodeData) {
        creationListener(nodeData)
    }

    override fun onDialogNegativeClick() { }

    override fun setTabName(name: String?) {
        toolbar.title = name?: applicationContext.getString(R.string.title_activity_main)
    }
}
