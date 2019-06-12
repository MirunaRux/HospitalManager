package com.miruna.hospitalmanager.application.pacient

import com.fasterxml.jackson.annotation.JsonIgnore

class Pacient {
    var id: String = ""
    var name: String? = null
    var surname: String? = null
    var birthday: String? = null
    var cnp: String? = null
    var dateIn: String? = null
    var dateEx: String? = null

    val allName: String
        @JsonIgnore
        get() = "$name $surname"

    constructor() {}

    constructor(id: String, name: String, surname: String, birthday: String, cnp: String, dateIn: String, dateEx: String) {
        this.id = id
        this.name = name
        this.surname = surname
        this.birthday = birthday
        this.cnp = cnp
        this.dateIn = dateIn
        this.dateEx = dateEx
    }
}
