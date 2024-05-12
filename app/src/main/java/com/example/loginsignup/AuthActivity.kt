package com.example.loginsignup

import android.os.Bundle
import android.os.PersistableBundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.loginsignup.ui.theme.LoginSignUpTheme
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class AuthActivity:ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        startKoin {
            androidContext(this@AuthActivity)
            androidLogger()
            modules(appModule)
        }
        setContent {
            LoginSignUpTheme {
                Navigation()
            }
        }
    }
}