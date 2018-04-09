package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.CareAssistant

interface CareAssistantsContract {
    interface View:BaseView<Presenter>{
        fun showCareAssistants()
        fun showCareAssistantsNotFound()
        fun showConnectionError()
        fun addCareAssistant( careAssistant: CareAssistant )
        fun removeCareAssistant( careAssistant: CareAssistant )
        fun showRemoveDialog(careAssistant: CareAssistant)
        fun showSnackBarError(error:String )
        fun clearAddCareAssistant()
    }

    interface Presenter : BasePresenter{
        fun addNewCareAssistant(login: String)
        fun checkRemovingCareAssistants(id : Int )
        fun removeCareAssistant(id: Int)
        fun refreshCareAssistants()
    }

}