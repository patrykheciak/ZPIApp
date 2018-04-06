package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.CareAssistant

interface CareAssistantsContract {
    interface View:BaseView<Presenter>{
        fun showCareAssistants(careAssistants:List<CareAssistant>)
        fun showCareAssistantsNotFound()
        fun showConnectionError()
        fun showSnackBarError(error:String )
        fun clearAddCareAssistant()
        fun showRemoveDialog(careAssistant: CareAssistant)
    }

    interface Presenter : BasePresenter{
        fun addNewCareAssistant(login: String)
        fun checkRemovingCareAssistatn(id : Int )
        fun removeCareAssistant(id: Int)
        fun refreshCareAssistants()
    }

}