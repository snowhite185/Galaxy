package com.example.galaxy.chitfund

import kotlinx.coroutines.flow.Flow

interface MemberRepository {

    suspend fun getAllMembers(): Flow<List<Member>>
}