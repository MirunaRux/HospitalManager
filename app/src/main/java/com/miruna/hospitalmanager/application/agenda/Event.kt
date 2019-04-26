package com.miruna.hospitalmanager.application.agenda

class Event {
    var id: String = ""
    var name: String? = null
    var location: String? = null
    var startDate: String? = null
    var startTime: String? = null
    var pacientName: String? = null
    var doctorUsername: String? = null

    constructor(){}

    constructor(id: String, name:String, location: String, startDate: String, startTime: String, pacientName: String, doctorUsername: String){
        this.id = id
        this.name = name
        this.location = location
        this.startDate = startDate
        this.startTime = startTime
        this.pacientName = pacientName
        this.doctorUsername = doctorUsername
    }
}