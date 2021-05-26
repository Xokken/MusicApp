package ru.itis.xokken.musicapp.domain.logincase

import android.util.Log
import kotlinx.coroutines.withContext
import ru.itis.xokken.musicapp.data.model.User
import ru.itis.xokken.musicapp.data.repository.UserDatabase
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class LoginCaseImpl @Inject constructor(
    private val userDatabase: UserDatabase,
    private val context: CoroutineContext
): SignInCase, SignUpCase, CheckCurrentUser, LogOut {
    override suspend fun signIn(email: String, password: String): User {
        Log.d("CASE", "${userDatabase.getUser(email, password)}")
        return withContext(context) {userDatabase.getUser(email, password)}
    }

    override suspend fun signUp(email: String, password: String): User {
        return withContext(context) {userDatabase.saveNewUser(email, password)}
    }

    override suspend fun getCurrentUser(): User {
        return withContext(context) {userDatabase.checkUser()}
    }

    override suspend fun logOut() {
        userDatabase.logOut()
    }
}