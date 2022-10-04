package com.example.galaxy.chitfund

import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FundMockRepository @Inject constructor() : FundRepository {

    override suspend fun getAllMembers(): Flow<List<Participant>> {
        val value = listOf(
            Participant(id = 0, name = "Emil", chitsToAdd = 0),
            Participant(id = 1, name = "Anusha", chitsToAdd = 0, currentChits = 4)
        )
        return flow {
            delay(2000)
            emit(value)
        }
    }

    override suspend fun saveChitFund(data: ChitFund): ChitFund {
        TODO("Not yet implemented")
    }

    override suspend fun isMemberExists(name: String): Boolean {
        TODO("Not yet implemented")
    }

    override suspend fun addMembers(fundId: Long, members: List<Participant>) {
        TODO("Not yet implemented")
    }

    override suspend fun getAllChits(): List<ChitFund> {
        TODO("Not yet implemented")
    }

    override suspend fun saveContribution() {
        TODO("Not yet implemented")
    }
}