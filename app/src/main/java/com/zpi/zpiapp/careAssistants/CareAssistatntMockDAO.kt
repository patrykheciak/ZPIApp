package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.model.CareAssistant

class CareAssistatntMockDAO : CareAssistantDAO {
    private val mockCareAssistants: List<CareAssistant> = listOf(
            CareAssistant(1, "alek", "Pan", "Kleks"),
            CareAssistant(2, "franek_kimono", "Frankek", "Kimono"),
            CareAssistant(3, "papo", "Józef", "Piłsucki"),
            CareAssistant(4, "stary", "Roman", "Dmowski")
    )

    private val mockUserCareAssistantsIndex: MutableList<Int> = mutableListOf(
            mockCareAssistants[1].index,
            mockCareAssistants[2].index
    )

    override fun getUserCareAssistants(userId: Int): List<CareAssistant> {
        return mockCareAssistants
                .filter { mockUserCareAssistantsIndex.any { any@ it == filter@ it } }
    }

    override fun getCareAssistant(login: String): CareAssistant? {
        return mockCareAssistants.firstOrNull { it.login == login }
    }

    override fun addCareAssistantToUser(idCareAssistant: Int, idUser: Int): Boolean {
        return mockUserCareAssistantsIndex.add(idCareAssistant)
    }

    override fun removeCareAssistantFromUser(idCareAssistant: Int, idUser: Int):Boolean {
        mockUserCareAssistantsIndex.remove(idCareAssistant)
        return true
    }

}
