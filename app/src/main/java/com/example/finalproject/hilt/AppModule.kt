package com.example.finalproject.hilt

import com.example.finalproject.Constant.BASE_URL_KANJI_ALIVE
import com.example.finalproject.retrofit.KanjiAliveService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object AppModule {

    @Singleton
    @Provides
    @Named("KanjiAlive")
    fun providesKanjiAliveRetrofit(): Retrofit {
        return Retrofit.Builder().baseUrl(BASE_URL_KANJI_ALIVE)
            .addConverterFactory(GsonConverterFactory.create()).build()
    }

    @Singleton
    @Provides
    fun providesKanjiAliveService(@Named("KanjiAlive")retrofit: Retrofit): KanjiAliveService {
        return retrofit.create(KanjiAliveService::class.java)
    }

}