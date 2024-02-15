package com.game.doodlingdoods.viewmodels

import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore

class LoginScreenViewModel:ViewModel() {
    val firebaseAuth: FirebaseAuth by lazy {
        FirebaseAuth.getInstance()
    }
    val firestoreDB: FirebaseFirestore by lazy {
        FirebaseFirestore.getInstance()
    }
}