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
        val added = careAssistantDAO.allList.firstOrNull{careAssistant -> careAssistant.name == login }
        if(added != null){
            careAssistantDAO.userList.add(added)
            refreshCareAssistants()
            careAssistantsView.clearAddCareAssistant()
        } else careAssistantsView.showSnackBarError("nie znaleziono podaengo urzytkownika")

    }

    override fun checkRemovingCareAssistatn(id: Int) {
        val remove = careAssistantDAO.userList.first { careAssistant -> careAssistant.idCareAssistant==id }
        careAssistantsView.showRemoveDialog( remove )
    }

    override fun removeCareAssistant(id: Int) {
        val remove = careAssistantDAO.userList.first { careAssistant -> careAssistant.idCareAssistant==id }
        careAssistantDAO.userList.remove(remove)
        refreshCareAssistants()
    }

    private class CareAssistantsMockDAO{
        val allList = mutableListOf(
                CareAssistant(1,"Tomek","Radca"),
                CareAssistant(2,"Milek","Mosona"),
                CareAssistant(3,"Olek","Katorga"))

        val userList = mutableListOf(allList[1],allList[0])
    }
}