package br.com.montecardo.simplistic.di

import android.content.Context
import br.com.montecardo.simplistic.data.source.Repository
import br.com.montecardo.simplistic.data.source.room.RoomRepository
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {
    @Provides
    @Singleton
    fun provideRepository(context: Context): Repository = RoomRepository(context)
}