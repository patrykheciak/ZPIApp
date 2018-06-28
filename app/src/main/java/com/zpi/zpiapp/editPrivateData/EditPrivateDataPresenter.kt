package com.zpi.zpiapp.editPrivateData

import com.zpi.zpiapp.model.User
import java.util.*

class EditPrivateDataPresenter( var mEditPrivateDataView:EditPrivateDataContract.View? ):EditPrivateDataContract.Presenter {

    var user = User( 1,"Adam","Awacki","Login","dsad",true, Date() )


    init {
        mEditPrivateDataView?.setPresenter(this)
    }

    override fun start() {
        mEditPrivateDataView?.loadData(user)
    }

    override fun onViewDestroyed() {
        mEditPrivateDataView=null
    }

    override fun sendData(name: String, surname: String, isActive: Boolean) {
        user.Name=name
        user.Surname=surname
        user.isActive=isActive

        mEditPrivateDataView?.showSnackBarError("Zaktalizowano")
    }

}