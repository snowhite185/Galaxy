package com.example.galaxy.ui.login

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.Dimension
import androidx.core.view.WindowCompat
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.galaxy.R
import com.example.galaxy.ui.home.HomeActivity
import com.example.galaxy.ui.theme.DarkBlue
import com.example.galaxy.ui.theme.GalaxyTheme
import com.example.galaxy.utils.ConstraintContainer
import com.example.galaxy.utils.InsetContent
import com.google.android.gms.auth.api.signin.GoogleSignIn
import com.google.android.gms.auth.api.signin.GoogleSignInOptions


@ExperimentalMaterialApi
class LoginActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        WindowCompat.setDecorFitsSystemWindows(window, false)
        setContent {
            InsetContent {
                // A surface container using the 'background' color from the theme
                Surface(color = MaterialTheme.colors.background) {
                    LoginScreen()
                }
            }
        }
    }
}

@ExperimentalMaterialApi
@Composable
fun LoginScreen() {
    println("Login: Render")
    val context = LocalContext.current
    val viewModel = viewModel<LoginViewModel>()
    val state by viewModel.loginState.collectAsState()

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.StartActivityForResult()) {
            val task = GoogleSignIn.getSignedInAccountFromIntent(it.data)
            viewModel.signIn(task)
        }

    val gso = GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
        .requestEmail()
        .build()
    val googleSignInClient = GoogleSignIn.getClient(LocalContext.current, gso)

    when (state) {
        LoginViewModel.States.SUCCESS -> {
            (context as Activity).finish()
            context.startActivity(Intent(context, HomeActivity::class.java))
        }
        LoginViewModel.States.ERROR -> {
            Toast.makeText(context, "Login Failed", Toast.LENGTH_SHORT).show()
        }
        LoginViewModel.States.LOADING -> {
            launcher.launch(googleSignInClient.signInIntent)
        }
        LoginViewModel.States.IDLE -> {
            val account = GoogleSignIn.getLastSignedInAccount(context)
            viewModel.pageLoad(account)
        }
    }

    ConstraintContainer(
        modifier = Modifier
            .fillMaxSize()
            .background(color = DarkBlue)
    ) {
        val (image, label, singInButton, subText) = createRefs()
        val guideLine = createGuidelineFromTop(0.7f)
        Row(
            modifier = Modifier
                .fillMaxSize()
                .constrainAs(image) {
                    top.linkTo(parent.top)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(guideLine)
                    height = Dimension.fillToConstraints
                }
        ) {
            Image(
                painter = painterResource(id = R.drawable.rupee),
                contentDescription = "Background image",
                modifier = Modifier
                    .fillMaxSize()
            )
        }

        Text(text = "Welcome to Galaxy!",
            color = Color.White,
            fontSize = 30.sp,
            modifier = Modifier
                .constrainAs(label) {
                    top.linkTo(guideLine)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    bottom.linkTo(subText.top)
                    width = Dimension.fillToConstraints
                }
                .padding(start = 0.dp, end = 0.dp, top = 0.dp, bottom = 0.dp),
            textAlign = TextAlign.Center
        )
        Text(
            text = "The most intelligent app, you will ever need for all your chits. Sign in to get started.",
            color = Color.White,
            fontSize = 18.sp,
            modifier = Modifier
                .constrainAs(subText) {
                    top.linkTo(label.bottom)
                    start.linkTo(label.start)
                    end.linkTo(label.end)
                    bottom.linkTo(singInButton.top)
                }
                .padding(start = 20.dp, end = 20.dp, top = 0.dp, bottom = 0.dp),
            textAlign = TextAlign.Center
        )
        OutlinedButton(
            onClick = {
                if (state != LoginViewModel.States.LOADING) {
                    viewModel.onLoginClicked()
                    launcher.launch(googleSignInClient.signInIntent)
                }
            },
            modifier = Modifier
                .constrainAs(singInButton) {
                    bottom.linkTo(parent.bottom)
                    start.linkTo(parent.start)
                    end.linkTo(parent.end)
                    width = Dimension.fillToConstraints
                }
                .height(90.dp)
                .padding(start = 20.dp, end = 20.dp, top = 20.dp, bottom = 10.dp),
        )
        {
            ConstraintLayout(modifier = Modifier.fillMaxSize()) {
                val (googleImage, text) = createRefs()
                Image(
                    painter = painterResource(id = com.example.galaxy.R.drawable.ic_google),
                    contentDescription = "Sign in button",
                    modifier = Modifier.constrainAs(googleImage) {
                        top.linkTo(parent.top)
                        bottom.linkTo(parent.bottom)
                        start.linkTo(parent.start)
                    }
                )
                var loginText = "Sign in with Google"
                if (state == LoginViewModel.States.LOADING) {
                    loginText = "Please wait.."
                }
                Text(text = loginText, modifier = Modifier.constrainAs(text) {
                    top.linkTo(parent.top)
                    bottom.linkTo(parent.bottom)
                    start.linkTo(googleImage.start)
                    end.linkTo(parent.end)
                }, color = MaterialTheme.colors.onSurface)
            }
        }

    }
}

@ExperimentalMaterialApi
@Preview(showBackground = true)
@Composable
fun DefaultPreview2() {
    GalaxyTheme {
        LoginScreen()
    }
}