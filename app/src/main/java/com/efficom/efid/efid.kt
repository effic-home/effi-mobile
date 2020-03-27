package com.efficom.efid

import android.app.Application
import com.efficom.efid.di.component.DaggerAppComponent
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication

class efid: DaggerApplication() {

    override fun onCreate() {
        super.onCreate()
    }

    override fun applicationInjector(): AndroidInjector<out DaggerApplication> {
            return DaggerAppComponent.builder().application(this).build()
    }
}