package com.game.doodlingdoods.viewmodels

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.game.doodlingdoods.GameApi.KtorServerApi
import com.game.doodlingdoods.GameApi.RoomDetailsDataClass
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.launch

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

//    fun getRoomsFromApi(){
//        viewModelScope.launch {
//            getRooms()
//
//            addRooms("004","SundarC maams","Kushboo")
//        }
//    }


//    private suspend  fun getRooms(){
//        val response = KtorServerApi.api.getAllRooms()
//
//        if (response.isSuccessful && response.body() != null) {
//
//            Log.i("response " , response.body().toString())
//        }
//    }
//
//    private suspend fun addRooms(room_Id:String,create_by:String,password:String){
//        val data = RoomDetailsDataClass(id = 8, room_id = "005",created_by = "raghu", password = "pass" )
//        val response = KtorServerApi.api.addRooms(data)
//
//        Log.i("response",response.toString())
//
//    }
}