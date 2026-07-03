package dev.esteki.ibank.core.data.quickaction.di

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.quickaction.dao.QuickActionDao
import dev.esteki.ibank.core.data.quickaction.datasource.QuickActionLocalDataSource
import dev.esteki.ibank.core.data.quickaction.repository.QuickActionRepositoryImpl
import dev.esteki.ibank.core.domain.quickaction.repository.QuickActionRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object QuickActionDataModule {

    @Provides
    @Singleton
    fun provideQuickActionLocalDataSource(quickActionDao: QuickActionDao): QuickActionLocalDataSource =
        QuickActionLocalDataSource(quickActionDao)

    @Provides
    @Singleton
    fun provideQuickActionRepository(localDataSource: QuickActionLocalDataSource): QuickActionRepository =
        QuickActionRepositoryImpl(localDataSource)
}
