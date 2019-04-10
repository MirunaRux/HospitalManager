package com.miruna.hospitalmanager.application.agenda

import java.util.*

data class Event (
    var id : String,
    var name : String,
    var location : String,
    var startTime : Date,
    var pacientName : String,
    var doctorUsername: String
)