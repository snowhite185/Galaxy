package com.example.galaxy

import android.content.Intent
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import com.example.galaxy.home.HomeActivity
import com.example.galaxy.login.LoginActivity
import com.example.galaxy.login.LoginViewModel
import com.google.android.gms.auth.api.signin.GoogleSignIn
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

class RoutingActivity : AppCompatActivity() {

    private val viewModel: LoginViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        val splashScreen = installSplashScreen()
        super.onCreate(savedInstanceState)
        splashScreen.setKeepOnScreenCondition { true }
        pageLoad()
        checkForLoginState()
    }

    private fun pageLoad() {
        val account = GoogleSignIn.getLastSignedInAccount(this)
        viewModel.pageLoad(account)
    }

    private fun checkForLoginState() {
        lifecycleScope.launch {
            repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.loginState.collectLatest { state ->
                    when (state) {
                        LoginViewModel.States.SUCCESS ->
                            startActivity(Intent(this@RoutingActivity, HomeActivity::class.java))
                        else -> startActivity(
                            Intent(this@RoutingActivity, LoginActivity::class.java)
                        )
                    }
                    finish()
                }
            }
        }
    }
}