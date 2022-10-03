package com.example.galaxy.chitfund

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.Button
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import com.example.galaxy.ui.theme.GalaxyTheme
import com.example.galaxy.utils.Data
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AddNewChitActivity : ComponentActivity() {
    private val viewModel: ChitFundVm by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalaxyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    println("Rendering: AddNewChitActivity")
                    val state = rememberScrollState()
                    Column(modifier = Modifier.verticalScroll(state)) {
                        val chitFundInputData = viewModel.chitFundInputData
                        InputForm(
                            chitFundInputData,
                            onNextClicked = { viewModel.onChitFundDataInput() },
                        )
                        val memberData = viewModel.allMembers
                        val allFunds = viewModel.allFunds
                        val members = viewModel.membersInFund
                        val selectedFund = viewModel.selectedFund
                        val contribution = viewModel.contribution
                        //MembersList(memberData)
                        //ChitList(allFunds)
                        //ChitMembers(members, selectedFund)
                        Contribution(contribution,
                            { viewModel.nextMember() },
                            { viewModel.prevMember() })
                    }
                }
            }
        }
        viewModel.getMembers()
        viewModel.getAllFunds()
        viewModel.onFundSelected(1)
    }
}

@Composable
fun Contribution(data: ContributionInput?, next: () -> Unit, prev: () -> Unit) {
    if (data == null) return
    println("Rendering: Contribution")
    Text(text = "------Contributions-------")
    Text(text = "Member ${data.memberIndex} ----")
    Text(text = "Chit Number: ${data.chitNumber}")
    Text(text = "Date: ${data.date}")
    Text(text = "Premium: ${data.premium}")
    Text(text = "Loan capital: ${data.loanCapital}")
    Text(text = "Loan interest: ${data.loanInterest}")
    Text(text = "Fine: ${data.fine}")
    Text(text = "Member present: ${data.memberPresent}")
    Text(text = "Next enabled: ${data.nextEnabled}")
    Text(text = "Prev enabled: ${data.prevEnabled}")
    Button(onClick = { next() }, enabled = data.nextEnabled) {
        Text(text = "NEXT")
    }
    Button(onClick = { prev() }, enabled = data.prevEnabled) {
        Text(text = "PREV")
    }
}

@Composable
fun ChitMembers(members: List<Member>, selectedFund: ChitFund?) {
    println("Rendering: ChitMembers")
    Text(text = "------Members in the Chit-------")
    members.forEachIndexed { pos, member ->
        Text(text = "Item $pos ----")
        Text(text = "Name: ${member.memberName}")
        Text(text = "Chit number: ${member.chitNumber}")
        Text(text = "Fund name: ${selectedFund?.name}")
    }
}

@Composable
fun ChitList(allFunds: List<ChitFund>) {
    println("Rendering: ChitList")
    Text(text = "------Chits-------")
    for (i in allFunds.indices) {
        val fund = allFunds[i]
        Text(text = "Item $i ----")
        Text(text = "Name: ${fund.name}")
        Text(text = "Start date: ${fund.fundMetaData?.startDate}")
        Text(text = "Premium: ${fund.premium}")
        Text(text = "Members: ${fund.members.size}")
        Text(text = "Duration: ${fund.duration} - ${fund.meetingFrequency}")
        Text(text = "Fine: ${fund.fineForAbsence}")
        Text(text = "Total dividend: ${fund.fundMetaData?.totalDividendGiven}")
        Text(text = "Loan took: ${fund.fundMetaData?.totalLoanGiven}")
        Text(text = "Interest collected: ${fund.fundMetaData?.totalLoanInterestCollected}")
        Text(text = "Loan interest: ${fund.loanSettings?.interest} per ${fund.loanSettings?.loanFrequency}")
        Text(text = "End date: ${fund.fundMetaData?.endDate}")
        Text(text = "Meeting count: ${fund.fundMetaData?.meetingCount}")
        Text(text = "-------------")
    }
}

@Composable
fun MembersList(members: Data<List<MembersUiState>>) {
    println("Rendering: MembersList")

    when (members) {
        is Data.Error -> Column {
            Text(text = "Something went wring.")
            Button(onClick = { /*TODO*/ }) { Text(text = "Retry") }
        }
        is Data.Loading -> Column {
            Text(text = "Loading, please wait")
        }
        is Data.Success<List<MembersUiState>> -> {
            for (item in members.data!!) {
                val color = if (item.selected) Color.Blue else Color.Black
                Text(
                    text = "name: ${item.participant.name}, chits: ${item.participant.currentChits}",
                    color = color
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun InputForm(
    data: ChitFundInput,
    onNextClicked: () -> Unit,
) {
    println("Rendering: InputForm")
    Column {
        TextField(value = data.name, onValueChange = { data.name = it })
        TextField(value = data.premium, onValueChange = { data.premium = it })
        TextField(value = data.duration, onValueChange = { data.duration = it })
        TextField(value = data.loanInterest, onValueChange = { data.loanInterest = it })
        TextField(value = data.fine, onValueChange = { data.fine = it })
        TextField(value = data.meetingFrequency, onValueChange = { data.meetingFrequency = it })
        TextField(value = data.loanInterestFreq, onValueChange = { data.loanInterestFreq = it })

        Button(onClick = { onNextClicked() }) {
            Text(text = "NEXT")
        }
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GalaxyTheme {
        InputForm(
            ChitFundInput(),
            onNextClicked = { },
        )
    }
}