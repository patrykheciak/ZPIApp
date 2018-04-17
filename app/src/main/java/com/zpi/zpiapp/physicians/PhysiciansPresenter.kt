package com.zpi.zpiapp.physicians


import com.google.gson.Gson
import com.zpi.zpiapp.model.Physician
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.SimpleBag
import com.zpi.zpiapp.utlis.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class PhysiciansPresenter(private var physiciansView: PhysiciansContract.View?) : PhysiciansContract.Presenter {
    override fun onViewDestroyed() {
        physiciansView = null
    }

    private val physiciansBag = SimpleBag<Physician>()

    init {
        physiciansView?.setPresenter(this)
    }

    override fun start() {
        if (physiciansBag.list.isEmpty())
            refreshPhysicians()
    }

    override fun refreshPhysicians() {
        RetrofitInstance.physiciansService
                .patientPhysicians(User.userId)
                .enqueue(object : Callback<List<Physician>> {
                    override fun onFailure(call: Call<List<Physician>>?, t: Throwable?) {
                        physiciansView?.showConnectionError()
                        physiciansView?.showSnackBarError(t.toString())

                    }

                    override fun onResponse(call: Call<List<Physician>>?,
                                            response: Response<List<Physician>>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                it.body()?.let {
                                    physiciansBag
                                            .reload(it,
                                                    Comparator { t1, t2 ->
                                                        (t1.surname + t1.name)
                                                                .compareTo((t2.surname + t2.name))
                                                    })
                                }
                                if (physiciansBag.list.isEmpty().not()) {
                                    physiciansBag
                                            .list
                                            .forEach { physiciansView?.addPhysician(it) }
                                    physiciansView?.showPhysicians()
                                } else
                                    physiciansView?.showPhysiciansNotFound()
                            }
                        }
                    }
                })
    }

    override fun addNewPhysician(pwzNumber: String) {
        if (physiciansBag.list.firstOrNull { it.pwzNumber == pwzNumber } == null) {
            RetrofitInstance.physiciansService
                    .addPhysician(User.userId, pwzNumber)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                            physiciansView?.showSnackBarError(t.toString())
                        }

                        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                            response?.let {
                                if (it.isSuccessful) {
                                    it.body()?.string()?.let {
                                        val physician = Gson().fromJson(it, Physician::class.java)
                                        physiciansBag.add(physician)
                                        physiciansView?.addPhysician(physician)
                                        if (physiciansBag.list.size == 1)
                                            physiciansView?.showPhysicians()
                                    }
                                } else {
                                    physiciansView
                                            ?.showSnackBarError(it.errorBody()?.string()
                                                    ?: "Nieznany błąd")
                                }
                            }
                        }
                    })

        } else
            physiciansView?.showSnackBarError("Lekarz o numerze $pwzNumber jest już dodany")

        physiciansView?.clearTextAndFocus()
    }

    override fun checkRemovingPhysician(id: Int) {
        val remove = physiciansBag.list
                .first { physician -> physician.idPhysician == id }
        physiciansView?.showRemoveDialog(remove)
    }

    override fun removePhysician(id: Int) {
        val remove = physiciansBag.list.first { physician -> physician.idPhysician == id }

        RetrofitInstance.physiciansService
                .removePhysician(User.userId, id)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        physiciansView?.showSnackBarError(t.toString())
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                physiciansBag.remove(remove)
                                if (physiciansBag.list.isEmpty())
                                    physiciansView?.showPhysiciansNotFound()
                                physiciansView?.removePhysician(remove)
                            } else
                                physiciansView
                                        ?.showSnackBarError(it.errorBody()?.string()
                                                ?: "Nieznany błąd")
                        }
                    }

                })
    }


}