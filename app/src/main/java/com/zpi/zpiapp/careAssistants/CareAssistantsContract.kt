package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.CareAssistant

interface CareAssistantsContract {
    interface View:BaseView<Presenter>{
        fun showCareAssistants(careAssistants:List<CareAssistant>)
        fun showNoCareAssistants()
        fun showLoadCareAssistants()
        fun showSearchCareAssistants()
        fun addNewCareAssistant()
        fun showConnectionError()

    }

    interface Presenter : BasePresenter{
        fun searchCareAssistant(login:String)
        fun addNewCareAssistant(careAssistant : CareAssistant)
    }

}