package com.efficom.efid.di.component

import android.app.Application
import com.efficom.efid.MainApplication
import com.efficom.efid.di.module.*
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjector
import dagger.android.DaggerApplication
import dagger.android.support.AndroidSupportInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        ActivityModule::class,
        FragmentModule::class,
        NetworkModule::class,
        ViewModelModule::class
    ]
)


interface AppComponent: AndroidInjector<MainApplication> {

    override fun inject(app: MainApplication)

    @Component.Builder
    interface Builder {

        @BindsInstance
        fun application(application: Application): AppComponent.Builder

        fun build(): AppComponent
    }
}