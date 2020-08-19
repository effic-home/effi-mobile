package com.efficom.efid

import android.app.Application
import com.efficom.efid.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import timber.log.Timber

class MainApplication: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
        confTimber()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
            return DaggerAppComponent.builder().application(this).build()
    }

    private fun confTimber(){
        Timber.uprootAll()
        Timber.plant(Timber.DebugTree())
    }
}