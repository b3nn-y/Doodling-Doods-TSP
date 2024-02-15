package com.game.doodlingdoods.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class SignUpScreenViewModel:ViewModel() {

    val firebaseAuth:FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    val firestoreDB: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }

    fun addUserDetailsToCloud(userEmail:String, name:String){

        val data = hashMapOf("Email" to userEmail,"name" to name)
        val userDocument = firestoreDB.collection("Users").document(userEmail)

        userDocument.set(data).addOnCompleteListener {it->
            if (it.isSuccessful) println("name added document") else Log.i("firebase",it.exception.toString())
        }



    }
}