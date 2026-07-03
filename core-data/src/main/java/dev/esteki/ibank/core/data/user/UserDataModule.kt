package dev.esteki.ibank.core.data.user

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.user.repository.UserProfileRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object UserDataModule {

    @Provides
    @Singleton
    fun provideUserLocalDataSource(userProfileDao: UserProfileDao): UserLocalDataSource =
        UserLocalDataSource(userProfileDao)

    @Provides
    @Singleton
    fun provideUserProfileRepository(localDataSource: UserLocalDataSource): UserProfileRepository =
        UserProfileRepositoryImpl(localDataSource)
}
