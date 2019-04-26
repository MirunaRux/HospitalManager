package com.miruna.hospitalmanager.application.request


class Request{
    var id : String = ""
    var drugName : String? = null
    var cantity : Int = 0

    constructor(){}

    constructor(id: String, drugName: String, cantity: Int){
        this.id = id
        this.drugName = drugName
        this.cantity = cantity
    }
}