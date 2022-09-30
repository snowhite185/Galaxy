package com.example.galaxy.chitfund

import com.example.galaxy.chitfund.db.ChitFundDao
import com.example.galaxy.chitfund.db.ChitFundInfo
import com.example.galaxy.chitfund.db.MemberMapping
import com.example.galaxy.chitfund.db.MemberMappingDao
import com.example.galaxy.data.dao.MembersDao
import com.example.galaxy.data.entity.MemberInfo
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class FundRepositoryImpl @Inject constructor(
    private val memberDao: MembersDao,
    private val memberMappingDao: MemberMappingDao,
    private val chitFundDao: ChitFundDao,
) : FundRepository {

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

    override suspend fun saveChitFund(data: ChitFund): ChitFund {
        val info = ChitFundInfo(
            name = data.name,
            fineForAbsence = data.fineForAbsence,
            loanInterest = data.loanSettings?.interest ?: 0.0,
            premium = data.premium,
            duration = data.duration,
            loanAllowed = true,
            loanPaymentFrequency = data.loanSettings?.loanFrequency?.frequency
                ?: Frequency.WEEK.frequency,
            meetingFreq = data.meetingFrequency.frequency
        )
        val fundId = chitFundDao.insert(info)
        return data.copy(fundMetaData = ChitFundMetaData(id = fundId))
    }

    override suspend fun addMembers(fundId: Long, members: List<Member>) {
        var chitNumber: Long = memberMappingDao.getMaxChitNumber() + 1L
        memberDao.insert(members.map { MemberInfo(name = it.name) })
        val memberIds = memberDao.getAllByName(members.map { it.name })
        memberIds.forEach {
            memberMappingDao.insert(
                MemberMapping(
                    fundId = fundId,
                    memberId = it.id,
                    chitNumber = chitNumber++
                )
            )
        }
    }

    override suspend fun isMemberExists(name: String): Boolean {
        return memberDao.getByName(name) != null
    }
}