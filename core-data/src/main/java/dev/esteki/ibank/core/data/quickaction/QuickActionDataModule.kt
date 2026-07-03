package dev.esteki.ibank.core.data.quickaction

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
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
