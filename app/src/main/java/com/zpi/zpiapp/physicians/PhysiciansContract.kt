package com.zpi.zpiapp.physicians

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.Physician

interface PhysiciansContract {
    interface View:BaseView<Presenter>{
        fun showPhysicians()
        fun showPhysiciansNotFound()
        fun showConnectionError()
        fun addPhysician( physician: Physician )
        fun removePhysician( physician: Physician )
        fun showRemoveDialog( physician: Physician )
        fun showSnackBarError(error:String )
        fun clearAddPhysician()
        fun clearTextAndFocus()
    }

    interface Presenter : BasePresenter{
        fun addNewPhysician(pwzNumber: String)
        fun checkRemovingPhysician(id : Int )
        fun removePhysician(id: Int)
        fun refreshPhysicians()
        fun onViewDestroyed()
    }

}