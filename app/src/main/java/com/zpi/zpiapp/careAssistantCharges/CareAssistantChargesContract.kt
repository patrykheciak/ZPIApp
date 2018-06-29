package com.zpi.zpiapp.careAssistantCharges

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.PatientDTO

interface CareAssistantChargesContract{
    interface CareAssistantChargesVies:BaseView<CareAssistantChargesPresenter>{
        fun loadCharges( charges:List<PatientDTO>)
        fun showSnackBarError(error: String)
        fun clearTextAndFocus()
        fun showCareAssistantCharges()
        fun showCareAssistantChargesNotFound()
        fun showConnectionError()
    }

    interface CareAssistantChargesPresenter:BasePresenter{
        fun onViewDestroyed()
        fun deleteCharge( charge: PatientDTO)
        fun addCharge( login:String )
    }
}
