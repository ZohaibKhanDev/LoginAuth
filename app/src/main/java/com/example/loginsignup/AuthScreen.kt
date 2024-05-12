package com.example.loginsignup

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject
import kotlin.math.sin

@Composable
fun AuthScreen(navController: NavController) {
    val viewModel: AuthViewModel = koinInject()

    var email by remember {
        mutableStateOf("")
    }

    var password by remember {
        mutableStateOf("")
    }


    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var isDialog by remember {
        mutableStateOf(false)
    }
    var eyes by remember {
        mutableStateOf(false)
    }

    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Register")
                Spacer(modifier = Modifier.height(16.dp))

                TextField(
                    value = email,
                    onValueChange = {
                        email = it
                    },
                    placeholder = {
                        Text(text = "Enter Email")
                    },
                )

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    value = password,
                    onValueChange = {

                        password = it
                    },
                    placeholder = {
                        Text(text = "Enter Password")
                    },
                    trailingIcon = {
                        if (password >= 1.toString()) {
                            Icon(
                                imageVector = if (eyes) Icons.Default.Visibility else Icons.Default.VisibilityOff,
                                contentDescription = "",
                                modifier = Modifier.clickable { eyes = !eyes }
                            )
                        } else {

                        }

                    },
                    visualTransformation = if (eyes) VisualTransformation.None else PasswordVisualTransformation(),
                    singleLine = true
                )

                Spacer(modifier = Modifier.height(16.dp))
                if (password.isNullOrEmpty() && email.isNullOrEmpty()) {
                    Button(onClick = {
                        Toast.makeText(
                            context,
                            "Please Enter Email and Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Text(text = "Register")
                    }

                }
                else{
                    Button(onClick = {

                        scope.launch {
                            viewModel.createUser(
                                AuthUser(
                                    email,
                                    password
                                )
                            ).collect {
                                when (it) {
                                    is ResultState.Error -> {
                                        isDialog = false
                                        Toast.makeText(context, "Login Error", Toast.LENGTH_SHORT).show()
                                    }

                                    ResultState.Loading -> {
                                        isDialog = true
                                    }

                                    is ResultState.Success -> {
                                        isDialog = false
                                        Toast.makeText(context, "Login SuccessFully", Toast.LENGTH_SHORT).show()

                                    }
                                }

                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                    (context as Activity).finishAffinity()

                            }
                        }
                    }) {
                        Text(text = "Register")
                    }
                }


                Spacer(modifier = Modifier.height(10.dp))
                Text(
                    text = "Login",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.SemiBold,
                    modifier = Modifier.clickable { navController.navigate(screen.SecondScreen.route) }, color = Color.Blue)
            }
        }


    }

}