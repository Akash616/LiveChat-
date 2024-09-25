package com.akashgupta.livechat

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.akashgupta.livechat.screens.ChatListScreen
import com.akashgupta.livechat.screens.LoginScreen
import com.akashgupta.livechat.screens.SignUpScreen
import com.akashgupta.livechat.ui.theme.LiveChatTheme
import dagger.hilt.android.AndroidEntryPoint

sealed class DestinationScreen(var route: String) {
    object SignUp : DestinationScreen("signup")
    object Login : DestinationScreen("login")
    object Profile : DestinationScreen("profile")
    object ChatList : DestinationScreen("chatList")
    object SingleChat : DestinationScreen("singleChat/{chatId}") {
        fun createRoute(id: String) = "singleChat/$id"
    }

    object StatusList : DestinationScreen("statusList")
    object SingleStatus : DestinationScreen("singleStatus/{userId}") {
        fun createRoute(userId: String) = "singleChat/$userId"
    }
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        //enableEdgeToEdge()
        setContent {
            LiveChatTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    ChatAppNavigation()
                }

            }
        }
    }

    @Composable
    fun ChatAppNavigation() {

        val navController = rememberNavController()
        val viewModel = hiltViewModel<LCViewModel>()

        NavHost(navController = navController, startDestination = DestinationScreen.SignUp.route) {
            composable(DestinationScreen.SignUp.route) {
                SignUpScreen(navController, viewModel)
            }
            composable(DestinationScreen.Login.route) {
                LoginScreen()
            }
            composable(DestinationScreen.ChatList.route) {
                ChatListScreen()
            }
        }
    }

}