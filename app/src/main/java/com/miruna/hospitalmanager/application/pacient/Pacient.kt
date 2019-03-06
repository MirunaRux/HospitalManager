package com.miruna.hospitalmanager.application.pacient


data class Pacient (
    val id: Int,
    val name: String,
    val surname: String,
    val age: Int,
    val CNP: String?,
    val dateIn: String,
    val dateEx: String,
    val department: String)