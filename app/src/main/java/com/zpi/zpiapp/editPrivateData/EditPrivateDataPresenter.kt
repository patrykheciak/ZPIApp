package com.zpi.zpiapp.editPrivateData

import com.zpi.zpiapp.model.UserDTO
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditPrivateDataPresenter(var mEditPrivateDataView: EditPrivateDataContract.View?) : EditPrivateDataContract.Presenter {

    var user = UserDTO(-1, "", "", "", "", false, Date())

    init {
        mEditPrivateDataView?.setPresenter(this)
    }

    override fun start() {
        RetrofitInstance.userService
                .getUserData(User.userId)
                .enqueue(object : Callback<UserDTO> {
                    override fun onFailure(call: Call<UserDTO>?, t: Throwable?) {
                        if (mEditPrivateDataView != null)
                            call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<UserDTO>?, response: Response<UserDTO>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                it.body()?.let {
                                    user = it
                                    mEditPrivateDataView?.loadData(user)
                                }
                            } else {
                                if (mEditPrivateDataView != null)
                                    call?.clone()?.enqueue(this)
                                mEditPrivateDataView?.showSnackBarError(it.errorBody()?.string()
                                        ?: "Nieznany błąd")

                            }
                        }
                    }

                })


    }

    override fun onViewDestroyed() {
        mEditPrivateDataView = null
    }

    override fun sendData(name: String, surname: String, isActive: Boolean) {
        user.id=-1
        user.name = name
        user.surname = surname
        user.isActive = isActive

        RetrofitInstance.userService
                .editPersonalData(User.userId, user)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        mEditPrivateDataView?.showSnackBarError("Błąd połączenia")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                mEditPrivateDataView?.showSnackBarError("Pomyślnie zaktalizowano dane")
                            } else {
                                mEditPrivateDataView?.showSnackBarError(it.errorBody()?.string()?:"Nieznany błąd")
                            }
                        }
                    }

                })
    }

}