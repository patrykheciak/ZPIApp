package com.zpi.zpiapp.model

import java.util.*

data class User(
        var Id:Int,
        var Name:String,
        var Surname:String,
        var Login:String,
        var PasswordHash:String,
        var isActive:Boolean,
        var BirthDate:Date
)
