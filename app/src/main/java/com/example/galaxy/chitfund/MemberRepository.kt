package com.example.galaxy.chitfund

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class MemberRepository {

    suspend fun getAllMembers(): Flow<List<Member>> {
        val value = listOf(
            Member(name = "Emil", count = 0),
            Member(name = "Anusha", count = 0, fundCount = 4)
        )
        return flow {
            delay(2000)
            emit(value)
        }
    }
}