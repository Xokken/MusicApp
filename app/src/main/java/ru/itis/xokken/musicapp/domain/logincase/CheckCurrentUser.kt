package ru.itis.xokken.musicapp.domain.logincase

import ru.itis.xokken.musicapp.data.model.User

interface CheckCurrentUser {

    suspend fun getCurrentUser(): User

}
