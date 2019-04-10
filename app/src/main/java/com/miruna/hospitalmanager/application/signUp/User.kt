package com.miruna.hospitalmanager.application.signUp

import java.io.Serializable

data class User(
    var username: String,
    var password: String,
    var role: String
) : Serializable