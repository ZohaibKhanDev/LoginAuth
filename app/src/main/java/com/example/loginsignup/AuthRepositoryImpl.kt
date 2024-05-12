package com.example.loginsignup

import com.google.firebase.auth.FirebaseAuth
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow

class AuthRepositoryImpl(private val authdb: FirebaseAuth) : AuthRepository {
    override fun CreateUser(authUser: AuthUser): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)

            authdb.createUserWithEmailAndPassword(

             authUser.email,
                authUser.password

            ).addOnCompleteListener {
                if (it.isSuccessful) {
                    trySend(ResultState.Success("User Created"))
                } else {
                    trySend(ResultState.Error(it.toString()))
                }
            }.addOnFailureListener {
                trySend(ResultState.Error(it.toString()))
            }

            awaitClose {
                close()
            }
        }



    override fun LoginUser(authUser: AuthUser): Flow<ResultState<String>> = callbackFlow {
        trySend(ResultState.Loading)
        authdb.signInWithEmailAndPassword(
            authUser.email,
            authUser.password
        ).addOnCompleteListener {
            if (it.isSuccessful) {
                trySend(ResultState.Success("User Created"))
            } else {
                trySend(ResultState.Error(it.toString()))
            }
        }.addOnFailureListener {
            trySend(ResultState.Error(it.toString()))
        }
        awaitClose { close() }
    }
}