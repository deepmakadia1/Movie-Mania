package com.moviemania.di

import android.content.Context
import androidx.room.Room
import com.moviemania.data.DataRepository
import com.moviemania.data.local.DB_MOVIE_DATABASE
import com.moviemania.data.local.LocalRepository
import com.moviemania.data.remote.ApiService
import com.moviemania.data.remote.RemoteRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DataModule {


    @Singleton
    @Provides
    fun provideRemoteRepository(
        apiService: ApiService
    ): RemoteRepository {
        return RemoteRepository(apiService)
    }

    @Singleton
    @Provides
    fun provideLocalRepository(@ApplicationContext context: Context): LocalRepository {
        return Room.databaseBuilder(
            context, LocalRepository::class.java,
            DB_MOVIE_DATABASE
        ).fallbackToDestructiveMigration()
            .build()
    }


    @Singleton
    @Provides
    fun provideDataRepository(
        @ApplicationContext context: Context,
        remoteRepository: RemoteRepository,
        localRepository: LocalRepository
    ): DataRepository {
        return DataRepository(context, remoteRepository, localRepository)
    }

}
