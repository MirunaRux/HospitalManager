package com.miruna.hospitalmanager.application.drug

class Drug {
    var id: String = ""
    var name: String? = null
    var drugNumber: Int = 0

    constructor() {}

    constructor(id: String, name: String, drugNumber: Int) {
        this.id = id
        this.name = name
        this.drugNumber = drugNumber
    }
}