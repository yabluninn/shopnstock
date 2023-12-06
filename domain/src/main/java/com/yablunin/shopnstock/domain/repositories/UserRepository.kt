package com.yablunin.shopnstock.domain.repositories

import com.yablunin.shopnstock.domain.models.User

interface UserRepository {
    fun save(user: User)
    fun load(userId: String, callback: (User?) -> Unit)
    fun changeUsername(newName: String, user: User)
}