package com.moviemania.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.moviemania.BuildConfig
import com.moviemania.data.remote.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.ArrayList
import java.util.concurrent.TimeUnit
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RetrofitModule {

    private const val CONNECTION_TIMEOUT_SEC = 120
    private const val WRITE_TIMEOUT_SEC = 120
    private const val READ_TIMEOUT_SEC = 120


    @Singleton
    @Provides
    fun provideGsonBuilder(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }


    @Singleton
    @Provides
    fun provideInterceptorList(): ArrayList<Interceptor> {
        val interceptorList = ArrayList<Interceptor>()

        val loggingInterceptor = HttpLoggingInterceptor()
        if (BuildConfig.DEBUG) {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        } else {
            loggingInterceptor.level = HttpLoggingInterceptor.Level.NONE
        }
        interceptorList.add(loggingInterceptor)
        return interceptorList
    }

    @Singleton
    @Provides
    fun provideUnsafeOkHttpClient(interceptorList: ArrayList<Interceptor>): OkHttpClient.Builder {
        try {
            var builder = OkHttpClient.Builder()
            for (interceptor in interceptorList) {
                builder.addNetworkInterceptor(interceptor)
            }
            builder = builder.connectTimeout(CONNECTION_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            builder = builder.writeTimeout(WRITE_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            builder = builder.readTimeout(READ_TIMEOUT_SEC.toLong(), TimeUnit.SECONDS)
            return builder
        } catch (e: Exception) {
            throw RuntimeException(e)
        }

    }


    @Singleton
    @Provides
    fun provideRetrofit(clientBuilder: OkHttpClient.Builder, gson: Gson): Retrofit.Builder {
        return Retrofit.Builder()
            .baseUrl(BuildConfig.BASE_URL)
            .client(clientBuilder.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
    }


    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit.Builder): ApiService {
        return retrofit
            .build()
            .create(ApiService::class.java)
    }



}