package com.efficom.efid.di.module

import com.efficom.efid.ui.activity.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity
}