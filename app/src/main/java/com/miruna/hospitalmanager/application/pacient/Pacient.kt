package com.miruna.hospitalmanager.application.pacient



data class Pacient(
    val id: String,
    val name: String,
    val surname: String,
    val age: String,
    val CNP: String?,
    val dateIn: String,
    val dateEx: String)