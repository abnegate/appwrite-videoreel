package io.appwrite.videoreel.login

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import io.appwrite.Client
import io.appwrite.exceptions.AppwriteException
import io.appwrite.models.Session
import io.appwrite.services.Account
import io.appwrite.services.Database
import io.appwrite.videoreel.core.*
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

class LoginViewModel(private val client: Client) : BaseViewModel() {

    val username = MutableStateFlow("")
    val password = MutableStateFlow("")

    private val _session = MutableLiveData<Session>()

    val session: LiveData<Session> = _session

    private val database by lazy { Database(client) }
    private val account by lazy { Account(client) }

    fun login() {
        viewModelScope.launch {
            setBusy(true)

            if (!validateInputs()) {
                setBusy(false)
                return@launch
            }

            try {
                val session1 = account.getSession("current")

                val session = account.createSession(
                    username.value,
                    password.value
                )
                _session.postValue(session)
            } catch (ex: AppwriteException) {
                ex.printStackTrace()
                postMessage(
                    Messages.CREATE_SESSION_FAILED,
                    listOf(ex.message ?: ex.response ?: "")
                )
                return@launch
            } finally {
                setBusy(false)
            }
        }
    }

    fun register() {
        viewModelScope.launch {
            setBusy(true)

            if (!validateInputs()) {
                setBusy(false)
                return@launch
            }

            try {
                account.create(
                    "unique()",
                    username.value,
                    password.value
                )
                login()
            } catch (ex: AppwriteException) {
                ex.printStackTrace()
                postMessage(
                    Messages.CREATE_SESSION_FAILED,
                    listOf(ex.message ?: ex.response ?: "")
                )
                return@launch
            } finally {
                setBusy(false)
            }
        }
    }

    private fun validateInputs(): Boolean {
        if (!isValidUserName()) {
            postMessage(Messages.USER_NAME_INVALID)
            return false
        }
        if (!isValidPassword()) {
            postMessage(Messages.USER_PASSWORD_INVALID)
            return false
        }
        return true
    }

    private fun isValidUserName(): Boolean {
        return username.value.length >= 6
    }

    private fun isValidPassword(): Boolean {
        return password.value.length >= 6
    }
}