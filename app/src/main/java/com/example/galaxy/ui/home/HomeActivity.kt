package com.example.galaxy.ui.home

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.galaxy.R
import com.example.galaxy.ui.theme.DarkGreen
import com.example.galaxy.ui.theme.GalaxyTheme
import com.example.galaxy.ui.theme.LightGrey
import com.example.galaxy.ui.theme.MediumBlue

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalaxyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    HomePage()
                }
            }
        }
    }
}

@Composable
fun HomePage() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically, modifier = Modifier
                .padding(
                    bottom = 20.dp, top = 0.dp, start = 8.dp, end = 8.dp
                )
                .fillMaxWidth(), horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column {

                Text(
                    text = "Hi Jane Doe!",
                    fontSize = 24.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(bottom = 3.dp)
                )
                Text(
                    text = "20 September, Tuesday",
                    fontSize = 14.sp,
                    modifier = Modifier.padding(bottom = 2.dp)
                )
                Text(text = "10 ongoing chits", color = MediumBlue, fontSize = 12.sp)
            }
            Image(
                painter = painterResource(R.drawable.rupee),
                contentDescription = "Avatar",
                modifier = Modifier
                    .clip(shape = RoundedCornerShape(50))
                    .size(50.dp)
            )
        }
        Divider(
            color = LightGrey, modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
        )
        AddChitView()

    }
}

@Composable
fun AddChitView() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 25.dp, start = 8.dp, end = 8.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 30.sp
                )
            ) {
                append("Manage your team and chits with the ")
            }
            withStyle(
                SpanStyle(
                    color = MediumBlue, fontWeight = FontWeight.Bold, fontSize = 30.sp
                )
            ) {
                append("Galaxy ")
            }
            withStyle(
                SpanStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 30.sp
                )
            ) {
                append("app")
            }
        })

        Row(modifier = Modifier.padding(top = 12.dp, start = 8.dp, end = 8.dp)) {
            Image(
                painter = painterResource(id = R.drawable.ic_search),
                contentDescription = "Avatar",
            )
        }
        Column(
            modifier = Modifier.padding(bottom = 30.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(
                text = "Your chits will show up here. Start by adding your first chit.",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Button(
                onClick = { /*TODO*/ },
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier.fillMaxWidth().height(60.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue)
            ) {
                Text(text = "ADD NEW CHIT", fontSize = 16.sp, fontWeight = FontWeight.Bold)
            }
        }
    }
}

@Composable
fun ChitList() {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 20.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        items(2) {
            Card(
                border = BorderStroke(0.5.dp, LightGrey),
                colors = CardDefaults.cardColors(
                    containerColor = Color.White
                ),
                shape = RoundedCornerShape(10.dp),
                modifier = Modifier
                    .fillMaxSize()
                    .padding(all = 10.dp)
            ) {
                Column(
                    modifier = Modifier.padding(all = 20.dp)
                ) {

                    Text(
                        "Kudumbasree chit", fontWeight = FontWeight.Bold, fontSize = 18.sp
                    )
                    Text(
                        "18 May 2021 - 18 May 2022",
                        fontSize = 12.sp,
                        modifier = Modifier.padding(top = 5.dp, bottom = 5.dp)
                    )
                    Text(buildAnnotatedString {
                        withStyle(
                            SpanStyle(
                                color = DarkGreen, fontWeight = FontWeight.Bold, fontSize = 14.sp
                            )
                        ) {
                            append("45,000 ")
                        }
                        withStyle(
                            SpanStyle(
                                color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp
                            )
                        ) {
                            append("collected ")
                        }
                        withStyle(
                            SpanStyle(
                                color = MediumBlue, fontWeight = FontWeight.Bold, fontSize = 14.sp
                            )
                        ) {
                            append("15,000 ")
                        }
                        withStyle(
                            SpanStyle(
                                color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 14.sp
                            )
                        ) {
                            append("to go ")
                        }
                    })
                }
            }
        }
    }

}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GalaxyTheme {
        HomePage()
    }
}