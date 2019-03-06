package com.miruna.hospitalmanager.application.agenda

data class Event (
    var id : Int,
    var name : String,
    var location : String,
    var pacientName : String,
    var doctorId: Int)