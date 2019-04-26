package com.miruna.hospitalmanager.application.pacient.file

class File {
    var id: String = ""
    var content: String? = null
    var pacient_id: String? = null

    constructor(){}

    constructor(id: String, content:String, pacient_id: String){
        this.id = id
        this.content = content
        this.pacient_id = pacient_id
    }
}