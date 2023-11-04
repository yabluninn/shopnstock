package com.yablunin.shopnstock.domain.usecases.user

import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.UserRepository

class SaveUserUseCase(private val userRepository: UserRepository) {
    fun execute(user: User){
        userRepository.save(user)
    }
}