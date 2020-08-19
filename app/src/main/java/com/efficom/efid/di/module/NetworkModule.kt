package com.efficom.efid.di.module

import android.content.SharedPreferences
import androidx.navigation.Navigator
import com.efficom.efid.data.network.AuthApi
import com.google.gson.Gson
import dagger.Module
import dagger.Provides
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.converter.scalars.ScalarsConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
class NetworkModule {

    @Provides
    @Named(LOGINHTTP)
    fun provideHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)

        return httpClient.build()
    }



    @Provides
    @Named(LOGINRETROFIT)
    fun provideRetrofit(@Named(LOGINHTTP) client: OkHttpClient): Retrofit =

        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(SERVERURL)
                .build()

    @Provides
    @Singleton
    fun provideAuthApi(@Named(LOGINRETROFIT) retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    @Provides
    @Named(MAINHTTP)
    fun provideMainHttpClient(): OkHttpClient {
        val interceptor = HttpLoggingInterceptor()
        interceptor.level = HttpLoggingInterceptor.Level.BODY

        val httpClient = OkHttpClient.Builder()
            .readTimeout(TIMEOUT, TimeUnit.SECONDS)
            .writeTimeout(TIMEOUT, TimeUnit.SECONDS)
            .connectTimeout(TIMEOUT, TimeUnit.SECONDS)
            .addNetworkInterceptor(interceptor)

        return httpClient.build()
    }



    @Provides
    @Named(MAINRETROFIT)
    fun provideMainRetrofit(@Named(MAINHTTP) client: OkHttpClient): Retrofit =

        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .baseUrl(SERVERURL)
            .build()

    @Provides
    @Singleton
    fun provideRoomApi(@Named(MAINRETROFIT) retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    companion object {
        private const val SERVERURL = ""
        private const val TIMEOUT: Long = 15
        private const val LOGINHTTP = "LOGIN_HTTP"
        private const val LOGINRETROFIT = "LOGIN_RETROFIT"
        private const val MAINHTTP = "MAIN_HTTP"
        private const val MAINRETROFIT = "MAIN_RETROFIT"
    }
}