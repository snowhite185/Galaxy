package com.example.galaxy.chitfund

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galaxy.utils.DataStatus
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChitFundVm @Inject constructor(var memberRepository: MemberRepository) : ViewModel() {

    var chitFundInputData by mutableStateOf(ChitFundInput())
    var members: DataStatus<List<MembersUiState>> by mutableStateOf(DataStatus.Loading())

    private var chitFundData: ChitFund? = null

    init {
        members = DataStatus.Loading()
        loadMembers()
    }

    private fun loadMembers() {
        viewModelScope.launch {
            memberRepository.getAllMembers().collect { list ->
                val data = list.map { MembersUiState(it, false) }
                members = DataStatus.Success(data)
            }
        }
    }

    fun onChitFundDataInput() {
        chitFundData = ChitFund(
            name = chitFundInputData.name,
            premium = chitFundInputData.premium.toDouble(),
            duration = chitFundInputData.duration.toInt(),
            meetingFrequency = Frequency.valueOf(chitFundInputData.meetingFrequency),
            loanSettings = LoanSettings(
                interest = chitFundInputData.loanInterest.toDouble(),
                loanFrequency = Frequency.valueOf(chitFundInputData.loanInterestFreq)
            ),
            fineForAbsence = chitFundInputData.fine.toDouble()
        )
    }

    fun onMembersSelected(members: List<MembersUiState>) {
        chitFundData?.members = members.filter { it.selected }.map { it.member }
    }

    fun onSkipMemberSelection() {
        //save chitFundData to DB
    }
}

data class ChitFundInput(
    var name: String = "",
    var premium: String = "",
    var duration: String = "",
    var meetingFrequency: String = "",
    var loanInterest: String = "",
    var fine: String = "",
    var loanInterestFreq: String = "",
)

data class ChitFund(
    var name: String,
    var premium: Double,
    var duration: Int,
    var meetingFrequency: Frequency,
    var loanSettings: LoanSettings? = null,
    var fineForAbsence: Double = 0.0,
    var members: List<Member> = emptyList(),
    var fundMetaData: ChitFundMetaData? = null
)

data class ChitFundMetaData(
    var id: Int,
    var meetingCount: Int,
    var totalFineCollected: Double,
    var totalLoanInterestCollected: Double,
    var totalLoanGiven: Double,
    var totalDividendGiven: Double,
    var totalDividendToGive: Double = 0.0,
    var dividendPerMember: Double = 0.0,
    var availableAmount: Double,
    var startDate: String,
    var endDate: String,
    var premiumExpectedInUpcomingMeeting: Double,
    var fineExpectedInUpcomingMeeting: Double,
)

data class LoanSettings(
    val interest: Double,
    var loanFrequency: Frequency,
)

data class Member(var name: String, var count: Int, var fundCount: Int = 0)
data class MembersUiState(val member: Member, val selected: Boolean)

enum class Frequency(val frequency: String) {
    DAY("day"),
    WEEK("week"),
    MONTH("week");

    companion object {
        fun asDisplayList(pre: String = "", post: String = ""): List<String> {
            return values().map { "$pre${it.frequency}$post" }
        }
    }
}