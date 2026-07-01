package dev.esteki.ibank.core.data.search

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import dev.esteki.ibank.core.domain.search.SearchRepository
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class SearchDataModule {

    @Binds
    @Singleton
    abstract fun bindSearchRepository(impl: FakeSearchRepository): SearchRepository
}
