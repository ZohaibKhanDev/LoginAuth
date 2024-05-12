package com.example.loginsignup

import kotlinx.coroutines.flow.Flow

interface AuthRepository {
    fun CreateUser(authUser: AuthUser):Flow<ResultState<String>>

    fun LoginUser(authUser: AuthUser):Flow<ResultState<String>>
}