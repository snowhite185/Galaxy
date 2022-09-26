package com.example.galaxy.di

import com.example.galaxy.chitfund.MemberRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class RepoModule {

    @Provides
    fun provideMembersRepo(): MemberRepository {
        return MemberRepository()
    }
}