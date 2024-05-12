package com.example.loginsignup

import androidx.compose.ui.platform.LocalContext
import com.google.firebase.auth.FirebaseAuth
import org.koin.dsl.module


val appModule = module {
    single {
        FirebaseAuth.getInstance()
    }
    single<AuthRepository> {
        AuthRepositoryImpl(get())
    }

    single {
        AuthViewModel(get())
    }
}