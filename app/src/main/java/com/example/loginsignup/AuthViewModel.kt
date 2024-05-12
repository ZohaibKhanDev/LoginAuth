package com.example.loginsignup

import androidx.lifecycle.ViewModel

class AuthViewModel(private val repo:AuthRepository):ViewModel() {
    fun createUser(authUser: AuthUser)=repo.CreateUser(authUser)

    fun loginUser(authUser: AuthUser)=repo.LoginUser(authUser)

}