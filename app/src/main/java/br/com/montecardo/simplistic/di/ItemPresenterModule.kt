package br.com.montecardo.simplistic.di

import br.com.montecardo.simplistic.base.PersistentPresenter
import br.com.montecardo.simplistic.data.source.Repository
import br.com.montecardo.simplistic.item.ItemContract
import br.com.montecardo.simplistic.item.ItemPagePresenter
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class ItemPresenterModule {
    @Provides
    @Singleton
    fun provideItemPagePresenter(repository: Repository)
        : PersistentPresenter<ItemContract.PageView, ItemContract.PageState> =
        ItemPagePresenter(repository)
}
