package com.zpi.zpiapp.careAssistants


import com.google.gson.Gson
import com.zpi.zpiapp.model.CareAssistant
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.SimpleBag
import com.zpi.zpiapp.utlis.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class CareAssistantsPresenter(private val careAssistantsView: CareAssistantsContract.View) : CareAssistantsContract.Presenter {
    private val careAssistantBag = SimpleBag<CareAssistant>()

    init {
        careAssistantsView.setPresenter(this)
    }

    override fun start() {
        if (careAssistantBag.list.isEmpty())
            refreshCareAssistants()
    }

    override fun refreshCareAssistants() {
        RetrofitInstance.careAssistantsService
                .patientCareAssistants(User.userId)
                .enqueue(object : Callback<List<CareAssistant>> {
                    override fun onFailure(call: Call<List<CareAssistant>>?, t: Throwable?) {
                        careAssistantsView.showConnectionError()
                        careAssistantsView.showSnackBarError(t.toString())
                    }

                    override fun onResponse(call: Call<List<CareAssistant>>?,
                                            response: Response<List<CareAssistant>>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                it.body()?.let {
                                    careAssistantBag
                                            .reload(it,
                                                    Comparator { t1, t2 ->
                                                        (t1.surname + t1.name)
                                                                .compareTo((t2.surname + t2.name))
                                                    })
                                }
                                if (careAssistantBag.list.isEmpty().not()) {
                                    careAssistantBag
                                            .list
                                            .forEach { careAssistantsView.addCareAssistant(it) }
                                    careAssistantsView.showCareAssistants()
                                } else
                                    careAssistantsView.showCareAssistantsNotFound()
                            }
                        }
                    }
                })
    }

    override fun addNewCareAssistant(login: String) {
        if (careAssistantBag.list.firstOrNull { it.login == login } == null) {
            RetrofitInstance.careAssistantsService
                    .addCareAssistant(User.userId, login)
                    .enqueue(object : Callback<ResponseBody> {
                        override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                            careAssistantsView.showSnackBarError(t.toString())
                        }

                        override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                            response?.let {
                                if (it.isSuccessful) {
                                    it.body()?.string()?.let {
                                        val careAssistant = Gson().fromJson(it, CareAssistant::class.java)
                                        careAssistantBag.add(careAssistant)
                                        careAssistantsView.addCareAssistant(careAssistant)
                                        if(careAssistantBag.list.size==1)
                                            careAssistantsView.showCareAssistants()
                                    }
                                } else {
                                    careAssistantsView
                                            .showSnackBarError(it.errorBody()?.string() ?: "Nieznany błąd")
                                }
                            }
                        }
                    })

        } else
            careAssistantsView.showSnackBarError("Opiekun $login jest już dodany")

    }

    override fun checkRemovingCareAssistants(id: Int) {
        val remove = careAssistantBag.list
                .first { careAssistant -> careAssistant.idCareAssistant == id }
        careAssistantsView.showRemoveDialog(remove)
    }

    override fun removeCareAssistant(id: Int) {
        val remove = careAssistantBag.list.first { careAssistant -> careAssistant.idCareAssistant==id }

        RetrofitInstance.careAssistantsService
                .removeCareAssistant(User.userId,id)
                .enqueue(object :Callback<ResponseBody>{
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        careAssistantsView.showSnackBarError(t.toString())
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful) {
                                careAssistantBag.remove(remove)
                                if (careAssistantBag.list.isEmpty())
                                    careAssistantsView.showCareAssistantsNotFound()
                                careAssistantsView.removeCareAssistant(remove)
                            } else
                                careAssistantsView
                                        .showSnackBarError(it.errorBody()?.string() ?: "Nieznany błąd")
                        }
                    }

                })
    }

}