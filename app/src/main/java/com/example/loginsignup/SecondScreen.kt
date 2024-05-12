package com.example.loginsignup

import android.app.Activity
import android.content.Intent
import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.Button
import androidx.compose.material3.CircularProgressIndicator
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
import androidx.compose.ui.text.input.KeyboardType.Companion.Text
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

@Composable
fun SecondScreen(navController: NavController) {
    var email1 by remember {
        mutableStateOf("")
    }

    var password1 by remember {
        mutableStateOf("")
    }

    val scope = rememberCoroutineScope()

    val context = LocalContext.current

    var isDialog by remember {
        mutableStateOf(false)
    }
    val viewModel: AuthViewModel = koinInject()
    var eyes by remember {
        mutableStateOf(false)
    }

    LazyColumn(modifier = Modifier.padding(20.dp)) {
        item {
            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = "Login")
                Spacer(modifier = Modifier.height(16.dp))

                TextField(value = email1, onValueChange = {
                    email1 = it
                }, placeholder = {
                    Text(text = "Enter Email")
                })

                Spacer(modifier = Modifier.height(15.dp))

                TextField(
                    value = password1,
                    onValueChange = {
                        password1 = it

                    },
                    placeholder = {
                        Text(text = "Enter Password")
                    },
                    trailingIcon = {
                        if (password1 >= 1.toString()) {
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
                if (password1.isNullOrEmpty() && email1.isNullOrEmpty()) {
                    Button(onClick = {
                        Toast.makeText(
                            context,
                            "Please Enter Email and Password",
                            Toast.LENGTH_SHORT
                        ).show()
                    }) {
                        Text(text = "Login")
                    }

                } else {
                    Button(onClick = {
                        scope.launch {
                            viewModel.loginUser(
                                AuthUser(
                                    email1,
                                    password1
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
                                        Toast.makeText(context, "Login SccessFully", Toast.LENGTH_SHORT).show()

                                    }
                                }

                                    val intent = Intent(context, MainActivity::class.java)
                                    context.startActivity(intent)
                                    (context as Activity).finishAffinity()

                            }
                        }
                    }) {
                        Text(text = "Login")

                    }
                }


                Spacer(modifier = Modifier.height(15.dp))
                Text(
                    text = "Register",
                    fontSize = 17.sp,
                    modifier = Modifier.clickable { navController.navigate(screen.Home.route) }, color = Color.Blue
                )
            }
        }
    }
}