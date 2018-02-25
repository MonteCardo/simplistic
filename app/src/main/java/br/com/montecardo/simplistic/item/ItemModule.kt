package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.di.ActivityScoped
import br.com.montecardo.simplistic.di.FragmentScoped
import dagger.Binds
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ItemModule {
    @FragmentScoped
    @ContributesAndroidInjector
    internal abstract fun itemFragment(): ItemFragment

    @ActivityScoped
    @Binds
    internal abstract fun itemPagePresenter(presenter: ItemPagePresenterInjector)
        : ItemContract.PagePresenter
}