package com.zpi.zpiapp.careAssistants



class CareAssistantsPresenter( private val careAssistantsView: CareAssistantsContract.View): CareAssistantsContract.Presenter {

    private val careAssistantDAO:CareAssistantDAO = CareAssistatntMockDAO()

    init {
        careAssistantsView.setPresenter(this)
    }

    override fun start() {
        careAssistantsView.showCareAssistants(careAssistantDAO.getUserCareAssistants(0))
    }

    override fun addNewCareAssistant(login: String) {

    }

    override fun removeCareAssistant(index: Int) {

    }
}