package com.example.galaxy.chitfund

import com.example.galaxy.chitfund.db.MemberMappingDao
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class MemberRepositoryImpl @Inject constructor(
    private val memberMappingDao: MemberMappingDao,
) : MemberRepository {

    override suspend fun getAllMembers(): Flow<List<Member>> {
        return flow {
            emit(memberMappingDao.getMemberChits().map {
                Member(
                    id = it.id,
                    name = it.name,
                    currentChits = it.chitCount,
                    chitsToAdd = 0
                )
            })
        }
    }
}