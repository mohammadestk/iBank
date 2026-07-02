package dev.esteki.ibank.core.data.settings

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.data.user.UserProfileDao
import dev.esteki.ibank.core.domain.settings.SettingsRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object SettingsDataModule {

    @Provides
    @Singleton
    fun provideSettingsLocalDataSource(
        settingsDao: SettingsDao,
        userProfileDao: UserProfileDao,
    ): SettingsLocalDataSource = SettingsLocalDataSource(settingsDao, userProfileDao)

    @Provides
    @Singleton
    fun provideSettingsRepository(localDataSource: SettingsLocalDataSource): SettingsRepository =
        SettingsRepositoryImpl(localDataSource)
}
