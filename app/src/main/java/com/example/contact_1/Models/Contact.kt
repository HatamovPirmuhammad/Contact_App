package com.example.contact_1.Models

class MyContact {
    var name:String? = null
    var number:String? = null

    constructor(name: String?, number: String?) {
        this.name = name
        this.number = number
    }

    override fun toString(): String {
        return "Contact(name=$name, number=$number)"
    }

}