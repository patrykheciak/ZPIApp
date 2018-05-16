package com.zpi.zpiapp.drugHistory

import android.util.Log
import android.util.SparseArray
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter
import com.zpi.zpiapp.model.PatientDrug
import com.zpi.zpiapp.utlis.RetrofitInstance
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.Date
import java.util.Calendar
import java.text.SimpleDateFormat


class DrugHistoryPresenter(val mDrugHistoryView: DrugHistoryContract.View) : DrugHistoryContract.Presenter {

    init {
        mDrugHistoryView.setPresenter(this)
    }

    private lateinit var patientDrugs: List<PatientDrug>

    override fun start() {
        RetrofitInstance
                .patientDrugService
                .allPatientsDrugs(2)
                .enqueue(object : Callback<List<PatientDrug>> {

                    override fun onFailure(call: Call<List<PatientDrug>>?, t: Throwable?) {
                        Log.d("DrugHistoryPresenter", "" + t.toString())
                        call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<List<PatientDrug>>?,
                                            response: Response<List<PatientDrug>>?) {
                        Log.d("DrugHistoryPresenter", response?.body().toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                body?.let {
                                    onAllDrugsReceived(it)
                                }
                            }
                        }
                    }
                })
    }

    override fun dateSelected(date: Calendar) {
        val df = SimpleDateFormat("dd MMMM yyyy")
        val dateString = df.format(date.time)
        mDrugHistoryView.setButtonText(dateString)

        for (i in 0 until days.size){
            if (isSameDay(days[i], date.time)){
                mDrugHistoryView.setAdapterPage(i)
            }
        }
    }

    private val days = ArrayList<Date>()

    private fun onAllDrugsReceived(drugs: List<PatientDrug>) {
        val drugTherapyRange = drugTherapyRange(drugs)
        val enabledDisabledDays = enabledDisabledDays(drugTherapyRange, drugs)

        val start = MonthAdapter.CalendarDay(drugTherapyRange.first.time)
        val end = MonthAdapter.CalendarDay(drugTherapyRange.second.time)
        val disabledDaysSparseArray = dayListToSparseArray(enabledDisabledDays.second)

        days.clear()
        days.addAll(enabledDisabledDays.first)

        mDrugHistoryView.setDates(days)
        mDrugHistoryView.setDatesForSpinner(start, end, disabledDaysSparseArray)

        val df = SimpleDateFormat("dd MMMM yyyy")
        val date = df.format(days.last())
        mDrugHistoryView.setButtonText(date)
        mDrugHistoryView.setAdapterPage(days.size-1)
    }

    private fun dayListToSparseArray(days: List<Date>): SparseArray<MonthAdapter.CalendarDay> {
        val array = SparseArray<MonthAdapter.CalendarDay>()
        days.map {
            val cal = Calendar.getInstance()
            cal.time = it
            val y = cal.get(Calendar.YEAR)
            val m = cal.get(Calendar.MONTH)
            val d = cal.get(Calendar.DAY_OF_MONTH)
            array.put(y * 10000 + m * 100 + d, MonthAdapter.CalendarDay(y, m, d))
        }
        return array
    }

    private fun drugTherapyRange(drugs: List<PatientDrug>): Pair<Date, Date> {
        val start = Calendar.getInstance().time
        val end = Date(0)

        for (drug in drugs) {
            if (drug.dateStop == null) {
                end.time = Calendar.getInstance().time.time
            } else {
                if (drug.dateStart.before(start))
                    start.time = drug.dateStart.time

                if (drug.dateStop!!.after(end)) {
                    end.time = drug.dateStop!!.time
                }
            }
        }
        Log.d("DrugHistoryPresenter", "RANGE start $start end $end")
        return Pair(start, end)
    }

    private fun enabledDisabledDays(drugTherapyRange: Pair<Date, Date>, drugs: List<PatientDrug>)
            : Pair<List<Date>, List<Date>> {

        val enabledDays = ArrayList<Date>()
        val disabledDays = ArrayList<Date>()

        val start = Calendar.getInstance()
        start.time = drugTherapyRange.first
        val end = Calendar.getInstance()
        end.time = drugTherapyRange.second

        var date = start.time
        while (start.before(end) || start.equals(end)) {
            // for each day in range
            // check if at least one drug was meant to be taken that day
            var atLeastOne = false

            for (drug in drugs)
                if (drugTakenThatDay(drug, date))
                    atLeastOne = true

            if (atLeastOne)
                enabledDays.add(date)
            else
                disabledDays.add(date)

            start.add(Calendar.DATE, 1)
            date = start.time
        }

        return Pair(enabledDays, disabledDays)
    }

    private fun drugTakenThatDay(drug: PatientDrug, date: Date): Boolean {

        if (drug.dateStop != null) {
            val stop = drug.dateStop
            if (drug.dateStart.before(date) && date.before(stop)
                    || drug.dateStart == date
                    || drug.dateStop == date
                    || (drug.dateStart == drug.dateStop && isSameDay(drug.dateStart, date)))
                return true
        } else {
            if (drug.dateStart.before(date) || drug.dateStart == date)
                return true
        }
        return false
    }

    private fun isSameDay(d1: Date, d2: Date): Boolean {
        val c1 = Calendar.getInstance()
        c1.time = d1
        val c2 = Calendar.getInstance()
        c2.time = d2
        return c1.get(Calendar.YEAR) == c2.get(Calendar.YEAR)
                && c1.get(Calendar.DAY_OF_YEAR) == c2.get(Calendar.DAY_OF_YEAR)
    }
}