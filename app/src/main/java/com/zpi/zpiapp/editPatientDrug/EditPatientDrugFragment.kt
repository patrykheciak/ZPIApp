package com.zpi.zpiapp.editPatientDrug

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.Animation
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.model.Physician
import kotlinx.android.synthetic.main.fragment_drug_therapy_edit.*
import java.text.SimpleDateFormat
import java.util.*


class EditPatientDrugFragment : Fragment(), EditPatientDrugContract.View {
    var mShake: Animation? = null
    val mSimpleDateFormat = SimpleDateFormat("dd MMMM yyyy")

    override fun drugNotExistError() {
        drugTherapyDrugNameIL.startAnimation(mShake)
        showSnackBarError("Lek nie istnieje w bazie")
    }

    override fun pwzNotExistError() {
        drugTherapyPhysicianIL.startAnimation(mShake)
        showSnackBarError("Lekarz nie istnieje w bazie danych")
    }

    override fun dateError() {
        drugTherapyEndDateButton.startAnimation(mShake)
        drugTherapyStartDateButton.startAnimation(mShake)
        showSnackBarError("Data początku musi być po dacie końcowej")
    }

    override fun setPhysicianPwz(pwz: String) {
        drugTherapyPhysician.setText(pwz)
    }


    private var mPatientDrug: PatientDrug? = null
    private lateinit var mPresenter: EditPatientDrugContract.Presenter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mPatientDrug = arguments.getSerializable(ARG_PATIENT_DRUG) as? PatientDrug
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_drug_therapy_edit, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mShake = AnimationUtils.loadAnimation(context, R.anim.shake)

        drugTherapyStartDateButton.setOnClickListener { displayDatePicker(true) }
        drugTherapyEndDateButton.setOnClickListener { displayDatePicker(false) }

        drugName.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) mPresenter.setDrugName(drugName.text.toString()) }
        drugTherapyPhysician.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) mPresenter.setPhysiciansPwz(drugTherapyPhysician.text.toString()) }

        drugTherapyMorningDose.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) mPresenter.setMorning(drugTherapyMorningDose.text.toString()) }
        drugTherapyNoonDose.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) mPresenter.setMidday(drugTherapyNoonDose.text.toString()) }
        drugTherapyEveningDose.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) mPresenter.setNight(drugTherapyEveningDose.text.toString()) }

        drugTherapyAnnotation.setOnFocusChangeListener { _, hasFocus -> if (!hasFocus) mPresenter.setAnnotation(drugTherapyAnnotation.text.toString()) }

        drugTherapyOkButton.setOnClickListener {
            hideKeyboard()
            mPresenter.sendData()
        }

        hasEndDateCB.setOnClickListener { mPresenter.setHasEndDate(hasEndDateCB.isChecked) }

        mPatientDrug?.let {
            drugTherapyMorningDose.setText(it.morning.toString())
            drugTherapyNoonDose.setText(it.midday.toString())
            drugTherapyEveningDose.setText(it.night.toString())
            drugName.setText(it.drugName)

            hasEndDateCB.isChecked = it.dateStop != null
            drugTherapyStartDateButton.text = mSimpleDateFormat.format(it.dateStart)
            drugTherapyEndDateButton.text = mSimpleDateFormat.format(it.dateStop ?: Date())
            setEditPatientDrug()
        }
        if (mPatientDrug == null) {
            drugTherapyStartDateButton.text = mSimpleDateFormat.format(Date())
            drugTherapyEndDateButton.text = mSimpleDateFormat.format(Date())
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
        mPatientDrug?.let { mPresenter.setData(it) }
    }

    override fun setPresenter(presenter: EditPatientDrugContract.Presenter) {
        mPresenter = presenter
    }

    override fun setDrugs(drugs: List<Drug>) {
        val drugStrings = drugs.map { it.name }.sorted()
        if (context != null) {
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, drugStrings)
            drugName.setAdapter(adapter)
        }
    }

    override fun setPhysicians(physicians: List<Physician>) {
        val physiciansString = physicians.map { it.pwzNumber }.sorted()
        if (context != null) {
            val adapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, physiciansString)
            drugTherapyPhysician.setAdapter(adapter)
        }
    }


    private fun setEditPatientDrug() {
        drugName.isEnabled = false
        drugName.isClickable = false
        drugTherapyPhysician.isEnabled = false
        drugTherapyPhysician.isClickable = false
        drugTherapyStartDateButton.isEnabled = false
        drugTherapyStartDateButton.isClickable = false
    }

    private fun displayDatePicker(isStartDate: Boolean) {
        val today = GregorianCalendar()
        val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener(
                        { _, year, monthOfYear, dayOfMonth ->
                            val cal = Calendar.getInstance()
                            cal.set(year, monthOfYear, dayOfMonth)
                            if (isStartDate) {
                                mPresenter.setStartDate(cal)
                                drugTherapyStartDateButton.text = mSimpleDateFormat.format(cal.time)
                            } else {
                                mPresenter.setEndDate(cal)
                                drugTherapyEndDateButton.text = mSimpleDateFormat.format(cal.time)
                            }
                        })
                .setDateRange(MonthAdapter.CalendarDay(today), null)
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setPreselectedDate(
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH))
                .setDoneText("Wybierz")
                .setCancelText("Anuluj")
                .setThemeCustom(R.style.MyCustomBetterPickersDialogs)

        cdp.show(fragmentManager, "FRAG_TAG_DATE_PICKER")
    }

    override fun showSnackBarError(error: String) {
        Snackbar.make(drugTherapyRoot, error, Snackbar.LENGTH_LONG).show()
    }


    companion object {
        private val ARG_PATIENT_DRUG = "patientDrug"
        fun newEditInstance(patientDrug: PatientDrug): EditPatientDrugFragment {
            val fragment = EditPatientDrugFragment()
            val args = Bundle()
            patientDrug.let { args.putSerializable(ARG_PATIENT_DRUG, it) }
            fragment.arguments = args
            return fragment
        }

        fun newInstance(): EditPatientDrugFragment {
            return EditPatientDrugFragment()
        }
    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
            view.clearFocus()
        }
    }


}
