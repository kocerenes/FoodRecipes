package com.enesk.foodrecipes.di

import com.enesk.foodrecipes.data.source.local.LocalDataSource
import com.enesk.foodrecipes.data.source.local.LocalDataSourceImpl
import com.enesk.foodrecipes.data.source.remote.RemoteDataSource
import com.enesk.foodrecipes.data.source.remote.RemoteDataSourceImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class DataSourceModule {

    @Binds
    @Singleton
    abstract fun provideRemoteDataSource(
        remoteDataSourceImpl: RemoteDataSourceImpl
    ): RemoteDataSource

    @Binds
    @Singleton
    abstract fun provideLocalDataSource(
        localDataSourceImpl: LocalDataSourceImpl
    ): LocalDataSource
}