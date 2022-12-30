package com.kirito.app.remote.di

import com.kirito.app.remote.RemoteRepository
import com.kirito.app.remote.RemoteRepositoryImpl
import com.kirito.app.remote.api.ApiHelper
import com.kirito.app.remote.api.ApiHelperImpl
import com.kirito.app.remote.api.ApiService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataModule {

    @Provides
    fun baseUrl() = "https://api.pexels.com/"

    @Singleton
    @Provides
    fun provideOkHttpClient(): OkHttpClient {
        val header = Interceptor { chain ->
            val original: Request = chain.request()

            val request: Request = original.newBuilder().apply {
//                header("Authorization", "563492ad6f91700001000001041a7dccd84b4d1f89d964fa4848f06a")
                header("Authorization", "563492ad6f91700001000001afafd4b53f894fbc87ab98847a0789a3")
                method(original.method(), original.body())
            }.build()

            chain.proceed(request)
        }
        return OkHttpClient.Builder().apply {
            addInterceptor(header)
            connectTimeout(60, TimeUnit.SECONDS)
            readTimeout(30, TimeUnit.SECONDS)
        }.build()
    }

    @Singleton
    @Provides
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        BASE_URL: String
    ): Retrofit =
        Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .build()

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): ApiService = retrofit.create(ApiService::class.java)

    @Singleton
    @Provides
    fun provideApiHelper(apiHelper: ApiHelperImpl): ApiHelper = apiHelper

    @Singleton
    @Provides
    fun provideRemoteRepository(remoteRepositoryImpl: RemoteRepositoryImpl): RemoteRepository = remoteRepositoryImpl
}