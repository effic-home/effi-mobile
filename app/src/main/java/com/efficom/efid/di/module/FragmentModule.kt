package com.efficom.efid.di.module

import com.efficom.efid.ui.fragment.BaseFragment
import com.efficom.efid.ui.fragment.HomeFragment
import com.efficom.efid.ui.fragment.LoginFragment
import com.efficom.efid.ui.fragment.RegisterFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    internal abstract fun contributeBaseFragment(): BaseFragment

    @ContributesAndroidInjector
    internal abstract fun contributeLoginFragment(): LoginFragment

    @ContributesAndroidInjector
    internal abstract fun contributeRegisterFragment(): RegisterFragment

    @ContributesAndroidInjector
    internal abstract fun contributeHomeFragment(): HomeFragment
}