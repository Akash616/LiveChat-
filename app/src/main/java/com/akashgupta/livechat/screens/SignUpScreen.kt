package com.akashgupta.livechat.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.akashgupta.livechat.CheckedSignedIn
import com.akashgupta.livechat.CommonProgressBar
import com.akashgupta.livechat.DestinationScreen
import com.akashgupta.livechat.LCViewModel
import com.akashgupta.livechat.R
import com.akashgupta.livechat.navigateTo
import com.akashgupta.livechat.ui.theme.Pink40
import com.akashgupta.livechat.ui.theme.purple

@Composable
fun SignUpScreen(navController: NavController, viewModel: LCViewModel) {

    CheckedSignedIn(viewModel, navController)

    Box(modifier = Modifier.fillMaxSize().background(color = Color.White)) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .wrapContentHeight()
                .verticalScroll(state = rememberScrollState()),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            val nameState = remember {
                mutableStateOf(TextFieldValue())
            }
            val numberState = remember {
                mutableStateOf(TextFieldValue())
            }
            val emailState = remember {
                mutableStateOf(TextFieldValue())
            }
            val passwordState = remember {
                mutableStateOf(TextFieldValue())
            }
            val focus = LocalFocusManager.current

            Image(
                painter = painterResource(R.drawable.ic_bubble_chart),
                contentDescription = null,
            )
            Text(
                text = "Sign Up",
                fontSize = 30.sp,
                color = purple,
                fontFamily = FontFamily.SansSerif,
                fontWeight = FontWeight.W400,
                modifier = Modifier.padding(bottom = 8.dp)
            )
            OutlinedTextField(
                value = nameState.value,
                onValueChange = {
                    nameState.value = it
                },
                label = { Text(text = "Name") },
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = numberState.value,
                onValueChange = {
                    numberState.value = it
                },
                label = { Text(text = "Number") },
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = emailState.value,
                onValueChange = {
                    emailState.value = it
                },
                label = { Text(text = "Email") },
                modifier = Modifier.padding(8.dp)
            )
            OutlinedTextField(
                value = passwordState.value,
                onValueChange = {
                    passwordState.value = it
                },
                label = { Text(text = "Password") },
                modifier = Modifier.padding(8.dp)
            )
            Button(
                onClick = {
                    viewModel.signUp(
                        nameState.value.text,
                        numberState.value.text,
                        emailState.value.text,
                        passwordState.value.text
                    )
                },
                modifier = Modifier
                    .padding(8.dp)
            ) {
                Text(text = "SIGN UP", color = Color.White)
            }
            Text(
                text = "Already a user? Go to login ->",
                color = purple,
                modifier = Modifier
                    .padding(8.dp)
                    .clickable {
                        navigateTo(navController, DestinationScreen.Login.route)
                    }
            )
        }
    }

    if (viewModel.inProcess.value) {
        CommonProgressBar()
    }

}
