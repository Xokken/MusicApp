package ru.itis.xokken.musicapp.data.repository

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.tasks.await
import kotlinx.coroutines.withContext
import ru.itis.xokken.musicapp.data.model.User
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class UserDatabase @Inject constructor(
    private val mAuth: FirebaseAuth,
) {

    suspend fun getUser(email: String, password: String): User {
        val user = User()
        mAuth.signInWithEmailAndPassword(email, password).await().let {
            user.email = it.user?.email.toString()
            user.id = it.user?.uid.toString()
        }
        return user
    }




    suspend fun saveNewUser(email: String, password: String): User{
        val user = User()
        mAuth.createUserWithEmailAndPassword(email, password).await().let{
            user.email = it.user?.email.toString()
            user.id = it.user?.uid.toString()
            return user
        }
    }

   fun checkUser(): User {
        val user = User()
        if (mAuth.currentUser != null){
            mAuth.currentUser?.apply {
                user.email = email.toString()
                user.id = uid
            }
        }
        return user
    }

    fun logOut(){
        mAuth.signOut()
    }
}