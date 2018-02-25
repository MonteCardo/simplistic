package br.com.montecardo.simplistic.item

import br.com.montecardo.simplistic.data.source.Repository
import br.com.montecardo.simplistic.di.ActivityScoped
import javax.inject.Inject

@ActivityScoped
class ItemPagePresenterInjector @Inject constructor(private val repository: Repository) :
    ItemContract.PagePresenter by ItemPagePresenter(repository)