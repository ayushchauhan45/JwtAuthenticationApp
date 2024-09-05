package com.example.jwtauth.DI

import android.app.Application
import android.content.Context.MODE_PRIVATE
import android.content.SharedPreferences
import com.example.jwtauth.auth.AuthRepository
import com.example.jwtauth.auth.AuthRepositoryImpl
import com.example.jwtauth.auth.authApi
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AuthModule {
    @Provides
    @Singleton
    fun provideApi():authApi{
        val moshi = Moshi.Builder()
            .add(KotlinJsonAdapterFactory())
            .build()

        return Retrofit.Builder().baseUrl("http://100.64.1.90:8080/")
            .addConverterFactory(
            MoshiConverterFactory.create(moshi)
        ).build().create()
    }
    @Provides
    @Singleton
    fun provideSharedPrefs(app:Application):SharedPreferences{
        return app.getSharedPreferences("prefs", MODE_PRIVATE)
    }
    @Provides
    @Singleton
    fun provideAuthRepository(api:authApi,prefs:SharedPreferences): AuthRepository {
        return AuthRepositoryImpl(api,prefs)
    }
}