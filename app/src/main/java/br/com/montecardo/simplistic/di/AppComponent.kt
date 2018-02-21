package br.com.montecardo.simplistic.di

import android.app.Application
import br.com.montecardo.simplistic.SimplisticApp
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(modules =
[AppModule::class,
ItemPresenterModule::class,
RepositoryModule::class,
ActivityBindingModule::class,
AndroidSupportInjectionModule::class])
interface AppComponent : AndroidInjector<SimplisticApp> {

//    fun inject(mainActivity: MainActivity)
//
//    fun inject(itemPage: ItemContract.PageView)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}