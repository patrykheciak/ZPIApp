package com.zpi.zpiapp.editPrivateData

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.User

interface EditPrivateDataContract{
    interface View:BaseView<Presenter>{
        fun loadData( user: User)
        fun showSnackBarError(error: String)
    }

    interface Presenter:BasePresenter{
        fun onViewDestroyed()
        fun sendData( name:String,surname:String, isActive:Boolean )
    }
}
