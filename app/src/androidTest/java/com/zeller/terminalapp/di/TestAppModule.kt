package com.zeller.terminalapp.di

import com.zeller.terminalapp.domain.MockUserRepository
import com.zeller.terminalapp.domain.UserRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.components.SingletonComponent
import dagger.hilt.testing.TestInstallIn
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import javax.inject.Singleton

@Module
@TestInstallIn(
    components = [SingletonComponent::class],
    replaces = [AppModule::class]
)
object TestAppModule {

    @Provides
    @Singleton
    fun provideMockUserRepository(): UserRepository {
        return MockUserRepository()
    }
}