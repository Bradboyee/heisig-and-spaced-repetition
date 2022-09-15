package com.example.finalproject.hilt

import android.content.Context
import androidx.room.Room
import com.example.finalproject.Constant.BASE_URL_KANJI_ALIVE
import com.example.finalproject.Constant.databaseName
import com.example.finalproject.retrofit.KanjiAliveService
import com.example.finalproject.roomdatabase.dao.SpacedDao
import com.example.finalproject.roomdatabase.SpacedDatabase
import com.example.finalproject.roomdatabase.SpacedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
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
    fun providesKanjiAliveService(@Named("KanjiAlive") retrofit: Retrofit): KanjiAliveService {
        return retrofit.create(KanjiAliveService::class.java)
    }

    @Singleton
    @Provides
    fun provideSpacedDatabase(@ApplicationContext context: Context): SpacedDatabase {
        return Room.databaseBuilder(context, SpacedDatabase::class.java, databaseName).build()
    }

    @Provides
    fun provideSpacedDao(spacedDatabase: SpacedDatabase): SpacedDao {
        return spacedDatabase.spacedDao()
    }

    @Provides
    fun provideSpacedRepository(spacedDao: SpacedDao): SpacedRepository {
        return SpacedRepository(spacedDao)
    }

}