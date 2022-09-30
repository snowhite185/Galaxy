package com.example.galaxy.chitfund

import kotlinx.coroutines.flow.Flow

interface FundRepository {

    suspend fun getAllMembers(): Flow<List<Member>>

    suspend fun saveChitFund(data: ChitFund): ChitFund

    suspend fun isMemberExists(name: String): Boolean

    suspend fun addMembers(fundId: Long, members: List<Member>)
}