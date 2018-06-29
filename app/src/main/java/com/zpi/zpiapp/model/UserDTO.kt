package com.zpi.zpiapp.model

import java.util.*

data class UserDTO(
        var id:Int?,
        var name:String?,
        var surname:String?,
        var login:String?,
        var passwordHash:String?,
        var isActive:Boolean?,
        var birthDate:Date?
)
