package com.example.galaxy.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
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
import com.example.galaxy.chitfund.AddNewChitActivity
import com.example.galaxy.ui.theme.DarkGreen
import com.example.galaxy.ui.theme.GalaxyTheme
import com.example.galaxy.ui.theme.LightGrey
import com.example.galaxy.ui.theme.MediumBlue
import com.example.galaxy.utils.SectionDivider

class HomeActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalaxyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background
                ) {
                    HomePage {
                        startActivity(
                            Intent(
                                this@HomeActivity,
                                AddNewChitActivity::class.java
                            )
                        )
                    }
                }
            }
        }
    }
}

@Composable
fun HomePage(onNewChitFundClicked: () -> Unit) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 10.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        HeaderView("Jane Doe", 15)
        ChitFundEmptyView(onNewChitFundClicked = onNewChitFundClicked)
    }
}

@Composable
fun ChitFundEmptyView(modifier: Modifier = Modifier, onNewChitFundClicked: () -> Unit) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(top = 20.dp, start = 8.dp, end = 8.dp, bottom = 12.dp),
        verticalArrangement = Arrangement.SpaceBetween,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(text = buildAnnotatedString {
            withStyle(
                SpanStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 25.sp
                )
            ) {
                append("Manage your chit funds and its members with the ")
            }
            withStyle(
                SpanStyle(
                    color = MediumBlue, fontWeight = FontWeight.Bold, fontSize = 25.sp
                )
            ) {
                append("Galaxy ")
            }
            withStyle(
                SpanStyle(
                    color = Color.Black, fontWeight = FontWeight.Bold, fontSize = 25.sp
                )
            ) {
                append("app")
            }
        }, textAlign = TextAlign.Center)

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
                text = "Your chit funds will show up here. Start by adding your first chit fund.",
                fontSize = 16.sp,
                color = Color.Black,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 20.dp, bottom = 20.dp)
            )
            Button(
                onClick = {
                    onNewChitFundClicked()
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(containerColor = MediumBlue)
            ) {
                Text(text = "ADD NEW CHIT FUND", fontSize = 16.sp, fontWeight = FontWeight.Bold)
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
                    Row(
                        modifier = Modifier.padding(top = 5.dp, bottom = 10.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        MyCircle()
                        Text(
                            "18 May 2021 - 18 May 2022",
                            fontSize = 12.sp,
                            modifier = Modifier.padding(start = 3.dp)
                        )
                    }

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

                    Divider(
                        color = LightGrey, modifier = Modifier
                            .fillMaxWidth()
                            .width(1.dp)
                    )
                }
            }
        }
    }

}

@Composable
fun MyCircle() {
    Canvas(modifier = Modifier.size(10.dp), onDraw = {
        drawCircle(color = Color.Red)
    })
}

@Composable
fun HeaderView(name: String, chitCount: Int, modifier: Modifier = Modifier) {
    Column(
        modifier = modifier.fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Hi $name!",
            fontSize = 28.sp,
            fontWeight = FontWeight.Bold,
            color = Color.Black,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 10.dp, bottom = 0.dp)
        )
        Text(
            text = "20 September, Tuesday",
            fontSize = 16.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 5.dp)
        )
        Text(
            text = "$chitCount ongoing chits",
            color = MediumBlue,
            fontWeight = FontWeight.Bold,
            fontSize = 14.sp,
            modifier = Modifier.padding(start = 10.dp, end = 10.dp, top = 0.dp, bottom = 10.dp)
        )
        SectionDivider()
    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GalaxyTheme {
        HomePage {}
    }
}