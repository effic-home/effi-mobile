package com.efficom.efid.di.module

import com.efficom.efid.ui.activity.BaseActivity
import com.efficom.efid.ui.activity.LoginActivity
import com.efficom.efid.ui.activity.MainActivity
import com.efficom.efid.ui.activity.SplashScreenActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityModule {

    @ContributesAndroidInjector
    internal abstract fun contributeMainActivity(): MainActivity

    @ContributesAndroidInjector
    internal abstract fun contributeBaseActivity(): BaseActivity

    @ContributesAndroidInjector
    internal abstract fun contributeSplashScreenActivity(): SplashScreenActivity

    @ContributesAndroidInjector
    internal abstract fun contributeLoginActivity(): LoginActivity
}