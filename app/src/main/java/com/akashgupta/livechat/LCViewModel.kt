package com.akashgupta.livechat

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import com.akashgupta.livechat.data.Event
import com.akashgupta.livechat.data.USER_NODE
import com.akashgupta.livechat.data.UserData
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.toObject
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(

    val auth: FirebaseAuth,
    val db: FirebaseFirestore

): ViewModel() {

    var inProcess = mutableStateOf(false)
    var eventMutableState = mutableStateOf<Event<String>?>(null)
    var signIn = mutableStateOf(false)
    var userData = mutableStateOf<UserData?>(null)

    init {
        val currentUser = auth.currentUser
        signIn.value = currentUser != null
        currentUser?.uid?.let {
            getUserData(it)
        }
    }

    fun signUp(name: String, number: String, email:String, password: String) {
        inProcess.value = true
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                signIn.value = true
                createOrUpdateProfile(name, number)
                Log.d("LiveChat","SignUp: User Logged In")
            } else {
                handleException(it.exception, "Sign up failed")
            }
        }
    }

    private fun createOrUpdateProfile(name: String?=null, number: String?=null, imgUrl: String?=null) {
        var uid = auth.currentUser?.uid
        val userData = UserData(
            userId = uid,
            name = name?: userData.value?.name,
            number = number?: userData.value?.number,
            imgUrl = imgUrl?: userData.value?.imgUrl
        )

        //let block run when value not null
        uid?.let {
            inProcess.value = true
            db.collection(USER_NODE).document(uid).get().addOnSuccessListener {

                if (it.exists()){
                    //update user data
                } else {
                    db.collection(USER_NODE).document(uid).set(userData)
                    inProcess.value = false
                    getUserData(uid)
                }

            }.addOnFailureListener {

                handleException(it, "Cannot retrieve user")

            }
        }
    }

    private fun getUserData(uid: String) {
        inProcess.value = true
        db.collection(USER_NODE).document(uid).addSnapshotListener { value, error ->

            if (value != null){
                var user = value.toObject<UserData>()
                userData.value = user
                inProcess.value = false
            }

            if (error != null) {
                handleException(error, "Can not retrieve user")
            }
        }
    }


    fun handleException(exception: Exception?=null, customMessage: String="") {
        Log.e("LiveChat","Live chat exception: ", exception)
        exception?.printStackTrace()
        val errorMsg = exception?.localizedMessage?:""
        val message = if (customMessage.isNullOrEmpty()) errorMsg else customMessage

        eventMutableState.value = Event(message)
        inProcess.value = false
    }

}
