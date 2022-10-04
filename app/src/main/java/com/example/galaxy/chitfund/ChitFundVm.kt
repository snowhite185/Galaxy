package com.example.galaxy.chitfund

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.galaxy.utils.Data
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.format.TextStyle
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ChitFundVm @Inject constructor(var fundRepository: FundRepository) : ViewModel() {

    var chitFundInputData = ChitFundInput()
    private var chitFundData: ChitFund? = null
    var selectedFund by mutableStateOf<ChitFund?>(null)
    var allMembers: Data<List<MembersUiState>> by mutableStateOf(Data.Loading())
    var membersInFund: List<Member> by mutableStateOf(emptyList())
    var contribution by mutableStateOf<ContributionInput?>(null)
    private var contributionIndex = 0
    private var contributions = ArrayList<ContributionInput>()
    private var _allMembers = ArrayList<MembersUiState>()
    var meetingFrequencies: List<FrequencyUiState> by mutableStateOf(emptyList())
    var loanFrequencies: List<FrequencyUiState> by mutableStateOf(emptyList())
    var allFunds: List<ChitFund> by mutableStateOf(emptyList())

    init {
        allMembers = Data.Loading()
        meetingFrequencies = Frequency.values().map { FrequencyUiState(it, false) }
        loanFrequencies = meetingFrequencies.map { it.copy() }
    }

    fun saveChitFund() {
        viewModelScope.launch(Dispatchers.IO) {
            chitFundData?.let { fundRepository.saveChitFund(it) }
        }
    }

    fun getAllFunds() {
        viewModelScope.launch(Dispatchers.IO) {
            allFunds = fundRepository.getAllChits()
        }
    }

    fun onFundSelected(fundId: Long) {
        viewModelScope.launch {
            delay(2000)
            selectedFund = allFunds.find { it.fundMetaData?.id == fundId }
            membersInFund = selectedFund?.members ?: emptyList()
            prepareContributionList()
        }
    }

    fun onSkipMemberSelection() {
        saveChitFund()
    }

    fun getMembers() {
        viewModelScope.launch(Dispatchers.IO) {
            fundRepository.getAllMembers()
                .catch {
                    allMembers = Data.Error()
                }.collect { list ->
                    val data = list.map { MembersUiState(it, false) }
                    _allMembers.addAll(data)
                    allMembers = Data.Success(_allMembers)
                }
        }
    }

    fun getSelectedMembers() {
        val result = _allMembers.filter { it.selected }
        allMembers = Data.Success(result)
    }

    fun searchMember(searchText: String) {
        val result = _allMembers.filter { it.participant.name.contains(searchText) }
        allMembers = Data.Success(result)
    }

    fun setMemberSelected(id: Long, selected: Boolean) {
        val selectedMemberIndex = _allMembers.indexOfFirst { it.participant.id == id }
        if (selectedMemberIndex != -1) {
            _allMembers[selectedMemberIndex].selected = selected
        }
        allMembers = Data.Success(_allMembers)
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

    fun onMembersSelectionComplete() {
        chitFundData?.participants = _allMembers.filter { it.selected }.map { it.participant }
        saveChitFund()
    }

    private fun prepareContributionList() {
        val today = today()
        contributions.clear()
        selectedFund?.let { fund ->
            fund.members.forEachIndexed { index, member ->
                val input = ContributionInput()
                input.chitNumber = member.chitNumber.toString()
                input.memberName = member.memberName
                input.fundName = fund.name
                input.date = today
                input.memberIndex = "$index"
                input.premium = fund.premium.toString()
                input.loanCapital = ""
                input.loanInterest = ""
                input.fine = ""
                input.memberPresent = true
                input.meetingIndex = selectedFund?.fundMetaData?.meetingCount?.toString() ?: ""
                input.totalMembers = fund.members.size.toString()
                contributions.add(input)
            }
        }
        contribution = contributions[0]
    }

    private val today = {
        val now = LocalDate.now()
        val default = Locale.getDefault()
        val textStyle = TextStyle.FULL
        val week = now.dayOfWeek.getDisplayName(textStyle, default)
        val day = now.dayOfMonth
        val month = now.month.getDisplayName(textStyle, default)
        "$week $day $month"
    }

    fun prevMember() {
        if (contributionIndex <= 0) return
        contributionIndex--
        contribution = contributions[contributionIndex]
        contribution?.nextEnabled = contributionIndex < contributions.lastIndex
        contribution?.prevEnabled = contributionIndex > 0
    }

    fun nextMember() {
        if (contributionIndex >= contributions.lastIndex) return
        contributionIndex++
        contribution = contributions[contributionIndex]
        contribution?.prevEnabled = contributionIndex > 0
        contribution?.nextEnabled = contributionIndex < contributions.lastIndex
    }

    fun finishContribution() {
        val fundId = selectedFund?.fundMetaData?.id ?: return
        val now = Calendar.getInstance().timeInMillis
        contributions.map {
            Contribution(
                chitNumber = it.chitNumber.toInt(),
                fundId = fundId,
                amountPaid = it.premium.toDouble(),
                date = now,
                finePaid = it.fine.toDouble(),
                loanCapitalPaid = it.loanCapital.toDouble(),
                loanInterestPaid = it.loanInterest.toDouble(),
                memberPresent = it.memberPresent,
                meetingCount = it.meetingIndex.toInt() + 1
            )
        }
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
    var participants: List<Participant> = emptyList(),
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

data class Participant(
    var id: Long,
    var name: String,
    var chitsToAdd: Int,
    var currentChits: Int = 0
)

data class Member(
    val memberId: Long,
    val fundId: Long,
    val memberName: String,
    val fundName: String,
    val chitNumber: Long,
    val finePending: Double? = 0.0,
    val loanPending: Double? = 0.0,
    val loanInterestPending: Double? = 0.0,
    val premiumPending: Double? = 0.0,
)

data class MembersUiState(val participant: Participant, var selected: Boolean)

fun List<MembersUiState>.getTotalCount() = this.sumOf { it.participant.chitsToAdd }

data class FrequencyUiState(val frequency: Frequency, var selected: Boolean)

fun Frequency.format(pre: String = "", post: String = ""): String {
    return "$pre${this.frequency}$post"
}

enum class Frequency(val frequency: String) {
    DAY("day"),
    WEEK("week"),
    MONTH("month");

    companion object {
        fun asFrequency(frequency: String): Frequency? {
            return values().find { it.frequency == frequency }
        }
    }
}

class ContributionInput {
    var meetingIndex by mutableStateOf("")
    var memberName by mutableStateOf("")
    var fundName by mutableStateOf("")
    var chitNumber by mutableStateOf("")
    var date by mutableStateOf("")
    var memberIndex by mutableStateOf("")
    var totalMembers by mutableStateOf("")
    var premium by mutableStateOf("")
    var loanCapital by mutableStateOf("")
    var loanInterest by mutableStateOf("")
    var fine by mutableStateOf("")
    var memberPresent by mutableStateOf(false)
    var prevEnabled by mutableStateOf(false)
    var nextEnabled by mutableStateOf(true)
}

data class Contribution(
    val chitNumber: Int,
    val fundId: Long,
    val date: Long,
    val meetingCount: Int,
    val amountPaid: Double,
    val finePaid: Double,
    val loanInterestPaid: Double,
    val memberPresent: Boolean,
    val loanCapitalPaid: Double,
)