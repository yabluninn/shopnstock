package com.yablunin.shopnstock.domain.usecases.user

import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.UserRepository

class LoadUserUseCase(private val userRepository: UserRepository) {
    fun execute(userId: String, callback: (User?) -> Unit){
        userRepository.load(userId, callback)
    }
}