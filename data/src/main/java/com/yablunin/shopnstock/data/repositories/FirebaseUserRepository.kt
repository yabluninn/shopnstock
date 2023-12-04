package com.yablunin.shopnstock.data.repositories

import android.util.Log
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yablunin.shopnstock.domain.models.User
import com.yablunin.shopnstock.domain.repositories.UserRepository

class FirebaseUserRepository: UserRepository {
    companion object{
        const val DB_USERS_NAME: String = "users"
    }
    private val dbReference: DatabaseReference = FirebaseDatabase.getInstance().getReference(
        DB_USERS_NAME
    )
    override fun save(user: User) {
        val uniqueId = user.id
        dbReference.child(uniqueId).setValue(user).addOnCompleteListener {
            Log.d("MyLog", "[FIREBASE] Data: $user saved to database!")
        }.addOnFailureListener {
            Log.d("MyLog", "[FIREBASE] Error with saving to database: ${it.message}!")
        }
    }

    override fun load(userId: String, callback: (User?) -> Unit) {
        dbReference.child(userId).addListenerForSingleValueEvent(object : ValueEventListener {
            override fun onDataChange(dataSnapshot: DataSnapshot) {
                if (dataSnapshot.exists()) {
                    val userData = dataSnapshot.getValue(User::class.java)
                    callback(userData)
                } else {
                    callback(null)
                }
            }
            override fun onCancelled(databaseError: DatabaseError) {
                callback(null)
            }
        })
    }

    override fun changeUsername(newName: String, user: User) {
        if (!newName.equals(user.username)){
            user.username = newName
        }
    }

}