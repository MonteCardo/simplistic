package br.com.montecardo.simplistic

import android.content.Context
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.AttributeSet
import android.view.View
import br.com.montecardo.simplistic.data.source.DummyRepository
import br.com.montecardo.simplistic.item.ItemContract
import br.com.montecardo.simplistic.item.ItemFragment
import br.com.montecardo.simplistic.item.ItemPagePresenter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        setSupportActionBar(toolbar)

        supportFragmentManager
            .beginTransaction()
            .add(R.id.placeholder, ItemFragment.newInstance(ItemPagePresenter(DummyRepository())))
            .commit()
    }
}
