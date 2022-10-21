package com.example.galaxy.chitfund

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.expandHorizontally
import androidx.compose.foundation.*
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.galaxy.R
import com.example.galaxy.ui.theme.DividerGrey
import com.example.galaxy.ui.theme.GalaxyTheme
import com.example.galaxy.ui.theme.LightGrey
import com.example.galaxy.ui.theme.MediumBlue
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
    Column(
        modifier = Modifier
            .padding(start = 10.dp, end = 10.dp)
            .verticalScroll(rememberScrollState())
    ) {
        Row(modifier = Modifier.padding(top = 40.dp, bottom = 20.dp, start = 20.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_back_arrow),
                contentDescription = "Avatar",
                modifier = Modifier
                    .padding(end = 20.dp)
                    .align(Alignment.CenterVertically)
                    .size(20.dp)
            )
            Text(
                text = "Create a new Chit fund",
                textAlign = TextAlign.Center,
                fontSize = 26
                    .sp,
                color = Color.Black,
                fontWeight = FontWeight.Bold,
                modifier = Modifier.align(Alignment.CenterVertically)
            )

        }

        Text(
            text = "Please provide the following information to setup your chit fund\n",
            textAlign = TextAlign.Start,
            fontSize = 16
                .sp,
            modifier = Modifier.padding(start = 20.dp),
            color = Color.Black,
        )

        Divider(
            color = LightGrey, modifier = Modifier
                .padding(start = 5.dp, end = 5.dp)
                .fillMaxWidth()
                .width(1.dp)
        )



        OutlinedTextField(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 40.dp)
                .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(color = DividerGrey, text = "Enter a name for your chit fund") },
            value = data.name,
            onValueChange = { data.name = it })


        OutlinedTextField(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(color = DividerGrey, text = "Enter the contribution per member") },
            value = data.premium, onValueChange = { data.premium = it })

        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(color = DividerGrey, text = "Duration") },
            value = data.duration,
            onValueChange = { data.duration = it })

        OutlinedTextField(
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp, top = 20.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(color = DividerGrey, text = "Week") },
            value = data.meetingFrequency, onValueChange = { data.meetingFrequency = it })

        OutlinedTextField(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(color = DividerGrey, text = "Loan Interest") },
            value = data.loanInterest,
            onValueChange = { data.loanInterest = it })

        OutlinedTextField(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = {
                Text(
                    color = DividerGrey,
                    text = "Fine for absence/missing contribution"
                )
            },
            value = data.fine, onValueChange = { data.fine = it })

        OutlinedTextField(modifier = Modifier
            .padding(start = 20.dp, end = 20.dp, top = 20.dp)
            .fillMaxWidth(),
            colors = TextFieldDefaults.outlinedTextFieldColors(
                focusedBorderColor = DividerGrey,
                unfocusedBorderColor = DividerGrey
            ),
            shape = RoundedCornerShape(8.dp),
            placeholder = { Text(color = DividerGrey, text = "for a week") },
            value = data.loanInterestFreq,
            onValueChange = { data.loanInterestFreq = it })

        Text(
            text = "Proceed to add members to your chit fund",
            textAlign = TextAlign.Center,
            fontSize = 16
                .sp,
            modifier = Modifier.padding(start = 20.dp),
            color = Color.Black,
        )
        Button(
            onClick = { onNextClicked() },
            shape = RoundedCornerShape(10.dp),
            modifier = Modifier
                .fillMaxWidth()
                .height(50.dp),
            colors = ButtonDefaults.buttonColors(containerColor = MediumBlue)
        ) {
            Text(text = "NEXT", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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