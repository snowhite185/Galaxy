package com.example.galaxy.chitfund

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberMockRepository @Inject constructor() : MemberRepository {

    override suspend fun getAllMembers(): Flow<List<Member>> {
        val value = listOf(
            Member(id = 0, name = "Emil", count = 0),
            Member(id = 1, name = "Anusha", count = 0, fundCount = 4)
        )
        return flow {
            delay(2000)
            emit(value)
        }
    }
}