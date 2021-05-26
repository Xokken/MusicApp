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
import kotlinx.android.synthetic.main.fragment_sign_up.*
import ru.itis.xokken.musicapp.R
import ru.itis.xokken.musicapp.data.model.User
import ru.itis.xokken.musicapp.presentation.activity.MainActivity
import ru.itis.xokken.musicapp.presentation.viewmodel.LoginViewModel

@AndroidEntryPoint
class SignUpFragment : Fragment(R.layout.fragment_sign_up) {

    private val loginViewModel: LoginViewModel by viewModels()


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        subscribeToObservers()
        initView()


    }

    private fun initView(){
        btn_signUp.setOnClickListener {
            loginViewModel.signUp(et_emailUp.text.toString(), et_passUp.text.toString(), et_passRepeat.text.toString())

        }


        btn_signIn.setOnClickListener {

            navHostFragmentLogin.findNavController().navigate(
                R.id.actionToSignInFragment
            )

        }
    }
    private fun subscribeToObservers() {
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

        loginViewModel.currentUser.observe(viewLifecycleOwner, {
            when(it.id){
                "" -> {}
                null->{}
                else -> newActivity(loginViewModel.currentUser.value!!)
            }
        })
    }

    private fun isLoading(){
        pb_signUp.isVisible = true
        et_emailUp.isVisible = false
        et_passUp.isVisible = false
        et_passRepeat.isVisible = false
        btn_signIn.isVisible = false
        btn_signUp.isVisible = false
    }

    private fun isLoaded(){
        pb_signUp.isVisible = false
        et_emailUp.isVisible = true
        et_passUp.isVisible =  true
        et_passRepeat.isVisible = true
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
        Snackbar.make(signUpFragment, text, Snackbar.LENGTH_LONG
        ).show()
    }
}