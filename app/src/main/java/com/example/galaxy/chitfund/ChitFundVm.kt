package com.example.galaxy.chitfund

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galaxy.utils.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ChitFundVm @Inject constructor(var fundRepository: FundRepository) : ViewModel() {

    var chitFundInputData = ChitFundInput()
    private var chitFundData: ChitFund? = null

    var members: Data<List<MembersUiState>> by mutableStateOf(Data.Loading())
    private var _members = ArrayList<MembersUiState>()

    var meetingFrequency: List<FrequencyUiState> by mutableStateOf(emptyList())
    var loanFrequency: List<FrequencyUiState> by mutableStateOf(emptyList())

    init {
        members = Data.Loading()
        meetingFrequency = Frequency.values().map { FrequencyUiState(it, false) }
        loanFrequency = meetingFrequency.map { it.copy() }
    }

    fun saveChitFund() {
        viewModelScope.launch(Dispatchers.IO) {
            chitFundData?.let { fundRepository.saveChitFund(it) }
        }
    }

    fun getMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            fundRepository.saveChitFund(
                ChitFund(
                    name = "Club chit 100",
                    premium = 100.0,
                    duration = 60,
                    meetingFrequency = Frequency.WEEK,
                    loanSettings = LoanSettings(interest = 1.0, loanFrequency = Frequency.WEEK),
                    fineForAbsence = 10.0
                )
            )
            fundRepository.getAllMembers()
                .catch {
                    members = Data.Error()
                }.collect { list ->
                    val data = list.map { MembersUiState(it, false) }
                    _members.addAll(data)
                    members = Data.Success(_members)
                }
        }
    }

    fun getSelectedMembers() {
        val result = _members.filter { it.selected }
        members = Data.Success(result)
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
        chitFundData?.members = _members.filter { it.selected }.map { it.member }
    }

    fun onSkipMemberSelection() {
        //save chitFundData to DB
    }
}

class ChitFundInput {
    var name by mutableStateOf("")
    var premium by mutableStateOf("")
    var duration by mutableStateOf("")
    var meetingFrequency by mutableStateOf("")
    var loanInterest by mutableStateOf("")
    var fine by mutableStateOf("")
    var loanInterestFreq by mutableStateOf("")
}

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
    var id: Long,
    var meetingCount: Int = 0,
    var totalFineCollected: Double = 0.0,
    var totalLoanInterestCollected: Double = 0.0,
    var totalLoanGiven: Double = 0.0,
    var totalDividendGiven: Double = 0.0,
    var totalDividendToGive: Double = 0.0,
    var dividendPerMember: Double = 0.0,
    var availableAmount: Double = 0.0,
    var startDate: String = "",
    var endDate: String = "",
    var premiumExpectedInUpcomingMeeting: Double = 0.0,
    var fineExpectedInUpcomingMeeting: Double = 0.0,
)

data class LoanSettings(
    val interest: Double,
    var loanFrequency: Frequency,
)

data class Member(var id: Int, var name: String, var chitsToAdd: Int, var currentChits: Int = 0)
data class MembersUiState(val member: Member, var selected: Boolean)

fun List<MembersUiState>.getTotalCount() = this.sumOf { it.member.chitsToAdd }

data class FrequencyUiState(val frequency: Frequency, var selected: Boolean)

fun Frequency.format(pre: String = "", post: String = ""): String {
    return "$pre${this.frequency}$post"
}

enum class Frequency(val frequency: String) {
    DAY("day"),
    WEEK("week"),
    MONTH("week");
}