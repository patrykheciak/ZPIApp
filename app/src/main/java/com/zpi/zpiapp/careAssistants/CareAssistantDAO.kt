package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.model.CareAssistant

interface CareAssistantDAO{
    fun getUserCareAssistants( userId : Int ):List<CareAssistant>
    fun getCareAssistant( login:String ): CareAssistant?
    fun addCareAssistantToUser( idCareAssistant: Int, idUser:Int ):Boolean
    fun removeCareAssistantFromUser( idCareAssistant: Int, idUser:Int ): Boolean
}