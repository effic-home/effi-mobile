package com.efficom.efid.di.module

import android.content.SharedPreferences
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
    @Named("URL")
    fun provideServerUrl(sharedPreferences: SharedPreferences): String{
        sharedPreferences?.let {
            return it.getString("base_url", "https://www.google.fr/").toString()
        }
    }

    @Provides
    @Named("LOGIN_RETROFIT")
    fun provideRetrofit(client: OkHttpClient, @Named("URL") url: String): Retrofit =

        Retrofit.Builder()
                .addConverterFactory(GsonConverterFactory.create())
                .client(client)
                .baseUrl(url)
                .build()

    @Provides
    @Singleton
    fun provideAuthApi(@Named("LOGIN_RETROFIT") retrofit: Retrofit): AuthApi {
        return retrofit.create(AuthApi::class.java)
    }

    companion object {
        private const val TIMEOUT: Long = 15
    }
}