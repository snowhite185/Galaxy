package com.example.galaxy

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import com.example.galaxy.ui.theme.GalaxyTheme
import com.example.galaxy.ui.theme.MediumBlue
import com.example.galaxy.utils.ConstraintContainer

class TestActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            GalaxyTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    Greeting("Android  ")
                }
            }
        }
    }
}

@Composable
fun Greeting(name: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(all = 20.dp), horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(
                    bottom = 20.dp
                )
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(modifier = Modifier.padding(end = 40.dp)) {

                Text(
                    text = "Hi $name!",
                    fontSize = 30.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color.Black
                )
                Text(text = "10 ongoing chits", color = MediumBlue)
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
            color = Color.Gray, modifier = Modifier
                .fillMaxWidth()
                .width(1.dp)
        )
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(top = 20.dp),
            verticalArrangement = Arrangement.SpaceBetween,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Text(buildAnnotatedString {
                withStyle(
                    SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                ) {
                    append("Manage your team and chits with the ")
                }
                withStyle(
                    SpanStyle(
                        color = MediumBlue,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                ) {
                    append("Galaxy ")
                }
                withStyle(
                    SpanStyle(
                        color = Color.Black,
                        fontWeight = FontWeight.Bold,
                        fontSize = 30.sp
                    )
                ) {
                    append("app")
                }
            })

            Row(modifier = Modifier.padding(top = 30.dp)) {
                Image(
                    painter = painterResource(id = R.drawable.ic_search),
                    contentDescription = "Avata",
                    contentScale = ContentScale.FillWidth,
                    modifier = Modifier.fillMaxWidth()
                )
            }
            Column(
                modifier = Modifier.padding(bottom = 30.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Text(
                    text = "Your chits will show up here. Start by adding your first chit.",
                    fontSize = 18.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 20.dp)
                )
                Button(
                    onClick = { /*TODO*/ }, shape = RectangleShape,
                    colors = ButtonDefaults.buttonColors(containerColor = MediumBlue)
                ) {
                    Text(text = "ADD NEW CHIT")
                }
            }

        }

    }
}

@Preview(showBackground = true)
@Composable
fun DefaultPreview() {
    GalaxyTheme {
        Greeting("Android")
    }
}