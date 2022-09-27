package com.example.galaxy.chitfund

import kotlinx.coroutines.flow.Flow

class MemberRepositoryImpl : MemberRepository {

    override suspend fun getAllMembers(): Flow<List<Member>> {
        TODO()
    }
}