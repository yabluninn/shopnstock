package com.yablunin.shopnstock.data

import android.util.Log
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.yablunin.shopnstock.domain.user.User

class DatabaseHandler {
    companion object{
        const val DB_USERS_NAME: String = "users"
        val DB_REFERENCE: DatabaseReference = FirebaseDatabase.getInstance().getReference(
            DB_USERS_NAME
        )

        fun save(db: DatabaseReference, user: User){
            val uniqueId = user.id
            db.child(uniqueId).setValue(user).addOnCompleteListener {
                Log.d("database", "Data: $user saved to database!")
            }.addOnFailureListener {
                Log.d("database", "Error with saving to database: ${it.message}!")
            }
        }

        fun load(db: DatabaseReference, callback: (User?) -> Unit) {
            db.addListenerForSingleValueEvent(object : ValueEventListener {
                override fun onDataChange(dataSnapshot: DataSnapshot) {
                    if (dataSnapshot.exists()) {
                        val userData = dataSnapshot.getValue(User::class.java)
                        callback(userData)
                    } else {
                        callback(null)
                    }
                }

                override fun onCancelled(databaseError: DatabaseError) {
                    // Обработка ошибки
                    callback(null)
                }
            })
        }
    }
}