package com.example.galaxy.chitfund

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
                    Column {
                        val chitFundInputData = viewModel.chitFundInputData
                        InputForm(
                            chitFundInputData,
                            onNextClicked = { viewModel.onChitFundDataInput() },
                        )
                        val memberData = viewModel.members
                        MembersList(memberData)
                    }
                }
            }
        }
        viewModel.getMembers()
    }
}

@Composable
fun MembersList(members: Data<List<MembersUiState>>) {
    println("Rendering: MembersList")

    when (members) {
        is Data.Error ->
            Column {
                Text(text = "Something went wring.")
                Button(onClick = { /*TODO*/ }) { Text(text = "Retry") }
            }
        is Data.Loading -> Column {
            Text(text = "Loading, please wait")
        }
        is Data.Success<List<MembersUiState>> -> {
            for (item in members.data!!) {
                val color = if (item.selected) Color.Blue else Color.Black
                Text(text = item.member.name, color = color)
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