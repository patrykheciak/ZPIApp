package com.zpi.zpiapp.editPatientDrug

import android.util.Log
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.model.Physician
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User
import com.zpi.zpiapp.utlis.Utils
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.*

class EditPatientDrugPresenter(private var mEditPatientDrugView:EditPatientDrugContract.View?):EditPatientDrugContract.Presenter {

    init {
        mEditPatientDrugView?.setPresenter(this)
    }

    private lateinit var drugs: List<Drug>
    private lateinit var physicians: List<Physician>
    private var mSavedPatientDrug:PatientDrug? = null


    //null błędne dane
    private var mUserId:Int? = User.userId
    // -1 znaczy bez lekarza
    private var mPhysicianId:Int? =-1
    private var mDrugId:Int? = null
    private var mStartDate: Date = Utils.todayZeroTime()
    private var mEndDate:Date = Utils.todayZeroTime()
    private var mAnnotation:String? = null

    private var mMidday: Int? = null
    private var mMorning: Int? = null
    private var mNight: Int? = null

    private var mHasEndDate = false

    override fun setHasEndDate(hasEndDate: Boolean) {
        mHasEndDate = hasEndDate;
    }

    override fun sendData() {
        var correct =true
        if (mDrugId==null){
            mEditPatientDrugView?.drugNotExistError()
            correct=false
        }
        if (mPhysicianId==null){
            mEditPatientDrugView?.pwzNotExistError()
            correct=false
        }
        if( mHasEndDate && !validateDate(mStartDate,mEndDate) ){
            mEditPatientDrugView?.showSnackBarError("Data zakończenia przed datą rozpoczęcia")
            correct=false
        }

        if(correct){
            val patientDrug =PatientDrug(
                    -1,
                    mUserId!!,
                    mDrugId!!,
                    if (mPhysicianId==-1) null else mPhysicianId,
                    "",
                    "",
                    mStartDate,
                    if(mHasEndDate) mEndDate else null,
                    mMidday?:0,
                    mMorning?:0,
                    mNight?:0,
                    mAnnotation?:"",
                    emptyList()
            )

            if(mSavedPatientDrug==null)
                addData(patientDrug)
            else
                editData(patientDrug)
        }


    }

    private fun editData(patientDrug: PatientDrug) {
        RetrofitInstance.patientDrugService
                .updatePatientDrug(mUserId!!,patientDrug)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Log.d("EPDPresenter", "" + t.toString())
                        mEditPatientDrugView?.showSnackBarError("Brak połączenia")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful)
                                mEditPatientDrugView?.showSnackBarError("Zaktualizowano")
                            else mEditPatientDrugView?.showSnackBarError("Błąd: "+(it.errorBody()?.string()?:"nieznany"))
                        }
                    }

                })
    }

    private fun addData(patientDrug: PatientDrug){


        RetrofitInstance.patientDrugService
                .addNewPatientDrug(patientDrug)
                .enqueue(object : Callback<ResponseBody> {
                    override fun onFailure(call: Call<ResponseBody>?, t: Throwable?) {
                        Log.d("EPDPresenter", "" + t.toString())
                        mEditPatientDrugView?.showSnackBarError("Brak połączenia")
                    }

                    override fun onResponse(call: Call<ResponseBody>?, response: Response<ResponseBody>?) {
                        response?.let {
                            if (it.isSuccessful)
                                mEditPatientDrugView?.showSnackBarError("Dodano")
                            else mEditPatientDrugView?.showSnackBarError(it.errorBody()?.string()?:"Nieznany błąd")
                        }
                    }

                })
    }

    override fun setDrugName(drugName: String) {
        val drugId = getDrugIdFromList(drugName)
        if (drugId!=null){
            mDrugId = drugId
        } else{
            mDrugId = null
            mEditPatientDrugView?.drugNotExistError()
        }

    }

    override fun setPhysiciansPwz(physiciansPwz: String) {
        if(physiciansPwz==""){
            mPhysicianId = -1
        } else {
            val physicianId = getPhysicianIdFromList(physiciansPwz)
            if (physicianId!=null){
                mPhysicianId = physicianId
            } else{
                mPhysicianId=null
                mEditPatientDrugView?.pwzNotExistError()
            }
        }
    }

    override fun setStartDate(startDate: Calendar) {
        mStartDate=Utils.zeroTime(startDate.time)
    }

    override fun setEndDate(endDate: Calendar) {
        mEndDate=Utils.zeroTime(endDate.time)
    }

    override fun setMorning(morning: String) {
        mMorning = morning.toIntOrNull()
        mMorning?.let {
            if (it==0)
            mMorning = null
        }
    }

    override fun setMidday(midday: String) {
        mMidday = midday.toIntOrNull()
        mMidday?.let {
            if (it==0)
                mMidday = null
        }
    }

    override fun setNight(night: String) {
        mNight = night.toIntOrNull()
        mNight?.let {
            if (it==0)
                mNight = null
        }
    }

    override fun setAnnotation(annotation: String) {
        mAnnotation = if (annotation.isEmpty())
            null
        else
            annotation
    }


    override fun start() {
        RetrofitInstance.interactionsService
                .drugList()
                .enqueue(object : Callback<List<Drug>> {
                    override fun onResponse(call: Call<List<Drug>>?, response: Response<List<Drug>>?) {
                        Log.d("InteractionPresenter", response?.body().toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                body?.let {
                                    drugs = it
                                    mEditPatientDrugView?.setDrugs(drugs)
                                }
                            }
                        }
                    }

                    override fun onFailure(call: Call<List<Drug>>?, t: Throwable?) {
                        Log.d("EPDPresenter", "" + t.toString())
                        call?.clone()?.enqueue(this)
                    }

                })

        RetrofitInstance.physiciansService
                .getAllPhysicians()
                .enqueue(object :Callback<List<Physician>>{
                    override fun onFailure(call: Call<List<Physician>>?, t: Throwable?) {
                        Log.d("EPDPresenter", "" + t.toString())
                        call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<List<Physician>>?, response: Response<List<Physician>>?) {
                        Log.d("EPDPresenter", response?.body().toString())
                        response?.let {
                            if (response.isSuccessful)
                                response.body()?.let {
                                    physicians = it
                                    mEditPatientDrugView?.setPhysicians(physicians)
                                    mSavedPatientDrug?.let {
                                        loadPhysician(it.idPhysician)
                                    }
                                }
                        }
                    }

                })

    }

    override fun setData(patientDrug: PatientDrug) {
        mSavedPatientDrug = patientDrug
        mUserId=User.userId
        mDrugId = patientDrug.idDrug;
        mPhysicianId = patientDrug.idPhysician
        mEndDate= patientDrug.dateStop?: Utils.todayZeroTime()
        mStartDate=patientDrug.dateStart
        mMidday=patientDrug.midday
        mMorning=patientDrug.morning
        mNight=patientDrug.night
        mAnnotation=patientDrug.drugAnnotation

        mHasEndDate = patientDrug.dateStop!=null

        loadPhysician(patientDrug.idPhysician)
    }

    private fun loadPhysician(idPhysician:Int?){
        if(::physicians.isInitialized){
            if (idPhysician == null){
                mPhysicianId=-1
                mEditPatientDrugView?.setPhysicianPwz("")
            }
            else
                mEditPatientDrugView?.setPhysicianPwz(getPhysicianPwzFromList(idPhysician))
        }

    }

    override fun onViewDestroyed() {
        mEditPatientDrugView= null
    }

    fun getDrugIdFromList(name:String):Int?{
        return drugs.find { it.name == name }?.idDrug
    }

    fun getDrugNameFromList(id:Int):String{
        return drugs.find { it.idDrug==id }?.name ?: ""
    }

    fun getPhysicianIdFromList(pwz:String):Int?{
        return physicians.find { it.pwzNumber == pwz }?.idPhysician
    }

    fun getPhysicianPwzFromList(id:Int):String{
        return physicians.find { it.idPhysician==id }?.pwzNumber ?: ""
    }



    fun validateDate(startDate:Date,endDate:Date ):Boolean{
        return startDate <= endDate
    }
}
