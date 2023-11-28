package com.yablunin.shopnstock.domain.usecases.user

import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.UserRepository

class ChangeUsernameUseCase(private val userRepository: UserRepository) {
    fun execute(newName: String, user: User){
        userRepository.changeUsername(newName, user)
    }
}