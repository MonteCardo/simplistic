package br.com.montecardo.simplistic.di

import br.com.montecardo.simplistic.MainActivity
import br.com.montecardo.simplistic.item.ItemModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBindingModule {
    @ActivityScoped
    @ContributesAndroidInjector(modules = [ItemModule::class])
    abstract fun mainActivity(): MainActivity
}