package com.zpi.zpiapp.careAssistantPerspective

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import com.zpi.zpiapp.R
import com.zpi.zpiapp.careAssistantCharges.CareAssistantChargesActivity
import com.zpi.zpiapp.careAssistants.CareAssistantsActivity
import com.zpi.zpiapp.drugHistory.DrugHistoryFragment
import com.zpi.zpiapp.drugHistory.DrugHistoryPresenter
import com.zpi.zpiapp.editPrivateData.EditPrivateDataActivity
import com.zpi.zpiapp.model.PatientDTO
import com.zpi.zpiapp.physicians.PhysiciansActivity
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User

import kotlinx.android.synthetic.main.activity_care_asstistant_perspective.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CareAsstistantPerspectiveActivity : AppCompatActivity() {
    private var patientList: List<PatientDTO>? = null

    private lateinit var presenter: DrugHistoryPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_asstistant_perspective)

        RetrofitInstance
                .careAssistantsService
                .getCharges(User.userId)
                .enqueue(object : Callback<List<PatientDTO>>{
                    override fun onFailure(call: Call<List<PatientDTO>>?, t: Throwable?) {

                    }

                    override fun onResponse(call: Call<List<PatientDTO>>?, response: Response<List<PatientDTO>>?) {
                        Log.d("CareAsstistantPer", response?.body().toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                body?.let {
//                                    it
                                    patientsLoaded(it)
                                }
                            }
                        }
                    }

                })

        val fragment = DrugHistoryFragment()
        presenter = DrugHistoryPresenter(fragment)
        supportFragmentManager
                .beginTransaction()
                .replace(R.id.fragment_history_container, fragment)
                .commit()

    }

    private var idCopy: Int = 0

    override fun onResume() {
        super.onResume()
        idCopy = User.userId
    }

    override fun onPause() {
        super.onPause()
        User.userId = idCopy
    }

    private fun patientsLoaded(patientList: List<PatientDTO>) {
        if (patientList.isNotEmpty()) {

            this.patientList = patientList

            splash_no_patients.visibility = View.GONE

            val array = patientListToArray(patientList)

            val spinnerArrayAdapter = ArrayAdapter<String>(
                    baseContext, android.R.layout.simple_spinner_item, array)
            spinner.adapter = spinnerArrayAdapter
            spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
                override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                    Log.d("CareAsstistantPer","id $position  ${patientList[position].lastName}")
                    User.userId = patientList[position].idPatient
                    presenter.start()
                }

                override fun onNothingSelected(parent: AdapterView<*>) {

                }
            }
        }
    }

    private fun patientListToArray(patientList: List<PatientDTO>): Array<String?> {
        val res = arrayOfNulls<String>(patientList.size)
        for (i in 0 until patientList.size) {
            res[i] = patientList.get(i).name + " " + patientList.get(i).lastName
        }
        return res
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.care_assistant_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {

        if( item?.itemId == R.id.assistant_item_account ){
            startActivity(Intent(this,EditPrivateDataActivity::class.java))
        }

        if (item?.itemId == R.id.assistant_item_charges){
            startActivity(Intent( this,CareAssistantChargesActivity::class.java ))
        }
        return true
    }
}
