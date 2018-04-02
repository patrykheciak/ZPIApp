package com.zpi.zpiapp.careAssistants

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.CareAssistant

interface CareAssistantsContract {
    interface View:BaseView<Presenter>{
        fun showCareAssistants(careAssistants:List<CareAssistant>)
        fun showCareAssistantsNotFound()
        fun showConnectionError()
        fun showRESTMessageError( error:String )
        fun clearAddCareAssistant()
    }

    interface Presenter : BasePresenter{
        fun addNewCareAssistant(login: String)
        fun removeCareAssistant(index: Int)
    }

}