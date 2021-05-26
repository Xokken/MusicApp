package ru.itis.xokken.musicapp.presentation.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.android.synthetic.main.activity_login.*
import kotlinx.android.synthetic.main.fragment_sign_in.*
import ru.itis.xokken.musicapp.R
import ru.itis.xokken.musicapp.data.model.User
import ru.itis.xokken.musicapp.presentation.activity.MainActivity
import ru.itis.xokken.musicapp.presentation.viewmodel.LoginViewModel

@AndroidEntryPoint
class SignInFragment : Fragment(R.layout.fragment_sign_in) {

    private val loginViewModel: LoginViewModel by viewModels()

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        initView()

    }

    private fun initView(){
        loginViewModel.checkUser()
        btn_signUp.setOnClickListener {
            navHostFragmentLogin.findNavController().navigate(
                R.id.actionToSignUpFragment
            )
        }

        btn_signIn.setOnClickListener {
            loginViewModel.signIn(et_emailIn.text.toString(), et_passIn.text.toString())
        }

    }

    private fun subscribeToObservers() {
        loginViewModel.currentUser.observe(viewLifecycleOwner,{
            when(it.id){
                "" -> {}
                null->{}
                else -> newActivity(loginViewModel.currentUser.value!!)
            }
        })

        loginViewModel.error.observe(viewLifecycleOwner, {
            when(it) {
                null -> {}
                else -> showSnackbar(it)
            }

        })
        loginViewModel.progressBar.observe(viewLifecycleOwner, {
            when(it){
                true -> isLoading()
                false -> isLoaded()

            }
        })
    }

    private fun isLoading(){
        pb_signIn.isVisible = true
        et_emailIn.isVisible = false
        et_passIn.isVisible = false
        btn_signIn.isVisible = false
        btn_signUp.isVisible = false
    }

    private fun isLoaded(){
        pb_signIn.isVisible = false
        et_emailIn.isVisible = true
        et_passIn.isVisible =  true
        btn_signIn.isVisible = true
        btn_signUp.isVisible = true
    }


    private fun newActivity(user: User){
        Log.d("USER", user.id)
        val intent = Intent(
            activity,
            MainActivity::class.java
        )
        user.run {
            intent.putExtra("id", id)
            intent.putExtra("email", email)
        }
        startActivity(intent)
        activity?.supportFragmentManager?.popBackStack()
        activity?.finish()

    }

    private fun showSnackbar(text: String){
        Snackbar.make(signInFragment, text, Snackbar.LENGTH_LONG
        ).show()
    }
}