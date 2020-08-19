package com.efficom.efid.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.efficom.efid.di.ViewModelFactory
import com.efficom.efid.di.ViewModelKey
import com.efficom.efid.viewmodel.LoginViewModel
import com.efficom.efid.viewmodel.RoomViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    abstract fun bindFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(loginViewModel: LoginViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(RoomViewModel::class)
    abstract fun bindRoomViewModel(roomViewModel: RoomViewModel): ViewModel

}