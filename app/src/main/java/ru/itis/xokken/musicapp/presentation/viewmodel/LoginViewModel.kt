package ru.itis.xokken.musicapp.presentation.viewmodel

import android.util.Log
import androidx.hilt.lifecycle.ViewModelInject
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.itis.xokken.musicapp.data.model.User
import ru.itis.xokken.musicapp.domain.logincase.CheckCurrentUser
import ru.itis.xokken.musicapp.domain.logincase.SignInCase
import ru.itis.xokken.musicapp.domain.logincase.SignUpCase


class LoginViewModel @ViewModelInject constructor(
    private val signUpCase: SignUpCase,
    private val signInCase: SignInCase,
    private val checkCurrentUser: CheckCurrentUser
): ViewModel() {

    val currentUser: MutableLiveData<User> = MutableLiveData()
    val error: MutableLiveData<String> = MutableLiveData()
    val progressBar: MutableLiveData<Boolean> = MutableLiveData()

    fun checkUser() {
        try {
            progressBar.value = true
            viewModelScope.launch {
                val user = checkCurrentUser.getCurrentUser()
                if (user.email != "") {
                    currentUser.value = user

                }

            }
        } catch (throwable: Throwable) {
            error.value = throwable.message
        } finally {
            progressBar.value = false
        }
    }

    fun signIn(etEmailin: String, etPassin: String) {
        viewModelScope.launch {
            try {
                progressBar.value = true
                Log.d("SIGNINGGGG", "${etEmailin}  ${etPassin}")
                signInCase.signIn(etEmailin, etPassin).run {
                    var user = this
                    Log.d("TAG", user.toString())
                    currentUser.value = user
                    if (user.email == ""){
                        Log.d("SIGNIN", "error")
                        error.value = "Something went wrong!"
                    } else {
                        currentUser.value = user
                        Log.d("SIGNINNNNNNN", "${currentUser.value.toString()}")
                    }
                }
            } catch (throwable: Throwable){
                error.value = throwable.message
            } finally {
                progressBar.value = false
            }

        }
    }

    fun signUp(email: String, passOne: String, passTwo: String) {
        Log.d("SIGNUPPPP", email + passOne + passTwo)
        if (passOne == passTwo) {
            viewModelScope.launch {
                try {
                    progressBar.value = true
                    signUpCase.signUp(email, passOne).run {
                        val user = this
                        if (user.email == "") {
                            error.value = "Something went wrong!"
                        } else {
                            currentUser.value = user
                        }
                    }
                } catch (throwable: Throwable){
                    error.value = throwable.message
                } finally {
                    progressBar.value = false
                }

            }


        }
        else{
            error.value = "Uncorrected data"
        }
    }



}