package com.example.mycyptos.di

import android.content.Context
import com.example.mycyptos.data.apiservice.CryptoApiService
import com.example.mycyptos.data.repository.CryptoServiceRepository
import com.example.mycyptos.data.repository.CryptoServiceRepositoryImplementation
import com.example.mycyptos.utils.AppConstants
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.ConnectionPool
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppDependencyModule {

    // Provides a singleton instance of CryptoApiService
    @Provides
    @Singleton
    fun provideMessageApi(
        @ApplicationContext context: Context
    ): CryptoApiService {
        val client = OkHttpClient.Builder()
            .retryOnConnectionFailure(true)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .connectTimeout(5, TimeUnit.MINUTES)
            .connectionPool(ConnectionPool(5, 5, TimeUnit.MINUTES))
            .build()

        val retrofit = Retrofit.Builder()
            .baseUrl(AppConstants.BASE_URL)
            .addConverterFactory(GsonConverterFactory.create())
            .client(client)
            .build()

        return retrofit.create(CryptoApiService::class.java)
    }

    // Provides a singleton instance of CryptoServiceRepository
    @Provides
    @Singleton
    fun provideMessageServiceRepository(api: CryptoApiService): CryptoServiceRepository {
        return CryptoServiceRepositoryImplementation(api)
    }

    // Provides the application context
    @Singleton
    @Provides
    fun provideApplicationContext(@ApplicationContext context: Context): Context {
        return context
    }


}