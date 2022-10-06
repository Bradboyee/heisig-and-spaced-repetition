package com.thepparat.heisigwithspacedrepetition.hilt

import android.content.Context
import androidx.room.Room
import com.thepparat.heisigwithspacedrepetition.Constant.BASE_URL_KANJI_ALIVE
import com.thepparat.heisigwithspacedrepetition.Constant.databaseName
import com.thepparat.heisigwithspacedrepetition.retrofit.KanjiAliveService
import com.thepparat.heisigwithspacedrepetition.roomdatabase.dao.SpacedDao
import com.thepparat.heisigwithspacedrepetition.roomdatabase.SpacedDatabase
import com.thepparat.heisigwithspacedrepetition.roomdatabase.SpacedRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.SupervisorJob
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Qualifier
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
    fun provideSpacedDatabase(@ApplicationContext context: Context,callback: SpacedDatabase.Callback): SpacedDatabase {
        return Room.databaseBuilder(context, SpacedDatabase::class.java, databaseName).addCallback(callback).build()
    }

    @Provides
    fun provideSpacedDao(spacedDatabase: SpacedDatabase): SpacedDao {
        return spacedDatabase.spacedDao()
    }

    @Provides
    fun provideSpacedRepository(spacedDao: SpacedDao): SpacedRepository {
        return SpacedRepository(spacedDao)
    }

    @ApplicationScope
    @Provides
    @Singleton
    fun provideApplicationScope() = CoroutineScope(SupervisorJob())
}
@Retention(AnnotationRetention.RUNTIME)
@Qualifier
annotation class ApplicationScope