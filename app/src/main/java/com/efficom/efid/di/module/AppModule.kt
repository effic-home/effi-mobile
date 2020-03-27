package com.efficom.efid.di.module

import android.app.Application
import android.content.Context
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module(includes = [ViewModelModule::class])
internal open class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: Application): Context = application
}