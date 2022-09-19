package com.example.galaxy.ui.login

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.galaxy.utils.PrefKeys
import com.example.galaxy.utils.PrefUtil
import com.google.android.gms.auth.api.signin.GoogleSignInAccount
import com.google.android.gms.common.api.ApiException
import com.google.android.gms.tasks.Task
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch


class LoginViewModel(app: Application) : AndroidViewModel(app) {

    private val prefUtil = PrefUtil(app)

    private fun userExists(): Boolean {
        return prefUtil.isEmpty(PrefKeys.USER_NAME).not()
    }

    fun signIn(task: Task<GoogleSignInAccount>) {
        try {
            viewModelScope.launch { loginState.emit(States.LOADING) }
            val account = task.getResult(ApiException::class.java)
            viewModelScope.launch { loginState.emit(States.SUCCESS) }
        } catch (e: ApiException) {
            e.printStackTrace()
            viewModelScope.launch { loginState.emit(States.ERROR) }
        }
    }

    fun pageLoad() {
        viewModelScope.launch {
            if (userExists()) {
                loginState.emit(States.LOADING)
            } else {
                loginState.emit(States.IDLE)
            }
        }
    }

    fun onLoginClicked() {
        viewModelScope.launch { loginState.emit(States.LOADING) }
    }

    val loginState = MutableStateFlow(States.IDLE)

    object States {
        const val IDLE = 0
        const val LOADING = 1
        const val SUCCESS = 2
        const val ERROR = 3
    }
}