package com.example.galaxy.chitfund

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galaxy.utils.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChitFundVm @Inject constructor(var memberRepository: MemberRepository) : ViewModel() {

    var chitFundInputData by mutableStateOf(ChitFundInput())
    private var chitFundData: ChitFund? = null

    var members: Data<List<MembersUiState>> by mutableStateOf(Data.Loading())
    private var _members = ArrayList<MembersUiState>()

    init {
        members = Data.Loading()
        loadMembers()
    }

    private fun loadMembers() {
        viewModelScope.launch {
            memberRepository.getAllMembers()
                .catch {
                    members = Data.Error()
                }.collect { list ->
                    val data = list.map { MembersUiState(it, false) }
                    _members.addAll(data)
                    members = Data.Success(_members)
                }
        }
    }

    fun searchMember(searchText: String) {
        val result = _members.filter { it.member.name.contains(searchText) }
        members = Data.Success(result)
    }

    fun setMemberSelected(id: Int, selected: Boolean) {
        val selectedMemberIndex = _members.indexOfFirst { it.member.id == id }
        if (selectedMemberIndex != -1) {
            _members[selectedMemberIndex].selected = selected
        }
        members = Data.Success(_members)
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

    fun onMembersSelected() {
        chitFundData?.members =
            this.members.data?.filter { it.selected }?.map { it.member } ?: emptyList()
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

data class Member(var id: Int, var name: String, var count: Int, var fundCount: Int = 0)
data class MembersUiState(val member: Member, var selected: Boolean)

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