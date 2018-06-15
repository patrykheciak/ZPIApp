package com.zpi.zpiapp.editPatientDrug

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.PatientDrug


class EditPatientDrugActivity : AppCompatActivity() {
    lateinit var editPatientDrugFragment: EditPatientDrugFragment
    private lateinit var presenter: EditPatientDrugPresenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_assistants)

        val patientDrug = if (intent.extras != null && intent.extras.containsKey(EXTRA_PATIENT_DRUG))
            intent.extras.getSerializable(EXTRA_PATIENT_DRUG) as? PatientDrug else null
        loadFragment(patientDrug)
    }

    private fun loadFragment(patientDrug: PatientDrug?) {
        if (::editPatientDrugFragment.isInitialized.not()) {
            editPatientDrugFragment = if (patientDrug == null)
                EditPatientDrugFragment.newInstance()
            else
                EditPatientDrugFragment.newEditInstance(patientDrug)
            presenter = EditPatientDrugPresenter(editPatientDrugFragment)

        }
        supportFragmentManager.beginTransaction()
                .replace(R.id.care_assistants_activity_frame, editPatientDrugFragment)
                .commit()

    }


    override fun onDestroy() {
        super.onDestroy()
        presenter.onViewDestroyed()
    }

    companion object {
        val EXTRA_PATIENT_DRUG = "EXTRA_PATIENT_DRUG"
    }

}