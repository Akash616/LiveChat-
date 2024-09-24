package com.akashgupta.livechat

import android.util.Log
import androidx.lifecycle.ViewModel
import com.google.firebase.auth.FirebaseAuth
import dagger.hilt.android.lifecycle.HiltViewModel
import java.lang.Exception
import javax.inject.Inject

@HiltViewModel
class LCViewModel @Inject constructor(

    val auth: FirebaseAuth

): ViewModel() {


    fun signUp(name: String, number: String, email:String, password: String) {
        auth.createUserWithEmailAndPassword(email, password).addOnCompleteListener {
            if (it.isSuccessful) {
                Log.d("LiveChat","SignUp: User Logged In")
            } else {

            }
        }
    }

}

fun handleException(exception: Exception?=null, customMessage: String="") {
    Log.e("LiveChat","Live chat exception: ", exception)
    exception?.printStackTrace()
    val errorMsg = exception?.localizedMessage?:""
    val message = if (customMessage.isNullOrEmpty()) errorMsg else customMessage
}
