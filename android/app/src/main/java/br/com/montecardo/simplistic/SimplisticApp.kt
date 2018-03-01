package br.com.montecardo.simplistic

import br.com.montecardo.simplistic.di.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class SimplisticApp : DaggerApplication() {
    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
        return DaggerAppComponent.builder().application(this).build()
    }
}