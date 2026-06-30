package dev.esteki.ibank.core.data.home

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.home.HomeRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class HomeDataModule {

    @Binds
    @Singleton
    abstract fun bindHomeRepository(impl: FakeHomeRepository): HomeRepository
}
