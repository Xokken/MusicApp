package ru.itis.xokken.musicapp.domain.logincase

import ru.itis.xokken.musicapp.data.model.User

interface SignInCase {
    suspend fun signIn(email: String, password: String): User
}