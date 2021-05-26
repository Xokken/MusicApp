package ru.itis.xokken.musicapp.domain.logincase

import ru.itis.xokken.musicapp.data.model.User

interface SignUpCase {

    suspend fun signUp(email: String, password: String): User
}