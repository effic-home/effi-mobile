package com.efficom.efid.di.module

import androidx.lifecycle.ViewModelProvider
import com.efficom.efid.di.ViewModelFactory
import com.efficom.efid.di.ViewModelKey
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory
}