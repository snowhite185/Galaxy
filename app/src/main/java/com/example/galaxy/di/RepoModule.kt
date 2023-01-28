package com.example.galaxy.di

import com.example.galaxy.chitfund.FundRepository
import com.example.galaxy.chitfund.FundRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepoModule {

    @Binds
    abstract fun provideMembersRepo(repo: FundRepositoryImpl): FundRepository
}