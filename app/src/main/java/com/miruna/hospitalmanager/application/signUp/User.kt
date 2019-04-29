package com.miruna.hospitalmanager.application.signUp


class User {
    var username: String = ""
    var password: String = ""
    var role: String = ""

    constructor(){}

    constructor(username: String, password:String, role:String){
        this.username = username
        this.password = password
        this.role = role
    }
}