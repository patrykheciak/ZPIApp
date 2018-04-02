package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.model.CareAssistant


class CareAssistantsPresenter(private val careAssistantsView: CareAssistantsContract.View) : CareAssistantsContract.Presenter {
    private val careAssistantDAO: CareAssistantsMockDAO = CareAssistantsMockDAO()

    init {
        careAssistantsView.setPresenter(this)
    }

    override fun start() {
        refreshCareAssistants()
    }

    override fun refreshCareAssistants() {
        val list = careAssistantDAO.userList
        if (list == null) {
            careAssistantsView.showConnectionError()
        } else if (!list.isEmpty())
            careAssistantsView.showCareAssistants(list)
        else
            careAssistantsView.showCareAssistantsNotFound()
    }

    override fun addNewCareAssistant(login: String) {
        careAssistantDAO.userList.add(careAssistantDAO.allList[2])
        refreshCareAssistants()
    }

    override fun removeCareAssistant(login: String) {
        careAssistantDAO.userList.removeAt(1)
        refreshCareAssistants()
    }

    private class CareAssistantsMockDAO{
        val allList = mutableListOf(
                CareAssistant("aaa","Tomek","Radca"),
                CareAssistant("ddd","Milek","Mosona"),
                CareAssistant("bbb","Olek","Katorga"))

        val userList = mutableListOf(allList[1],allList[0])


    }
}