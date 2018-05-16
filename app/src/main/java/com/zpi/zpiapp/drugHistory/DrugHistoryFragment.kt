package com.zpi.zpiapp.drugHistory

import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v4.app.FragmentManager
import android.support.v4.app.FragmentPagerAdapter
import android.support.v4.view.ViewPager
import android.util.Log
import android.util.SparseArray
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.codetroopers.betterpickers.calendardatepicker.CalendarDatePickerDialogFragment
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter
import com.zpi.zpiapp.R
import kotlinx.android.synthetic.main.fragment_drug_history.*
import java.util.*

class DrugHistoryFragment : Fragment(), DrugHistoryContract.View {
    override fun setAdapterPage(page: Int) {
        pager.setCurrentItem(page)
    }

    override fun setDates(first: List<Date>) {
        days.clear()
        days.addAll(first)
        mPagerAdapter?.notifyDataSetChanged()
    }

    override fun setDatesForSpinner(
            start: MonthAdapter.CalendarDay,
            end: MonthAdapter.CalendarDay,
            disabledDays: SparseArray<MonthAdapter.CalendarDay>) {
        pickerStartDate = start
        pickerEndDate = end
        pickerDisabledDays = disabledDays
    }

    override fun setPresenter(presenter: DrugHistoryContract.Presenter) {
        mPresenter = presenter
    }

    override fun setButtonText(string: String) {
        date_button.text = string
    }

    private val FRAG_TAG_DATE_PICKER: String = "FRAG_TAG_DATE_PICKER"
    private lateinit var mPresenter: DrugHistoryContract.Presenter

    private var pickerStartDate: MonthAdapter.CalendarDay? = null
    private var pickerEndDate: MonthAdapter.CalendarDay? = null
    private var pickerDisabledDays: SparseArray<MonthAdapter.CalendarDay>? = null

    private val days = ArrayList<Date>()
    private var mPagerAdapter: ScreenSlidePagerAdapter? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_drug_history, container, false)

    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        date_button.setOnClickListener {
            displayDatePicker()
        }

        mPagerAdapter = ScreenSlidePagerAdapter(fragmentManager)
        pager.adapter = mPagerAdapter
        pager.addOnPageChangeListener(object: ViewPager.SimpleOnPageChangeListener() {
            override fun onPageSelected(position: Int) {
                var dateOfNewPage = days[position]
                val asCalendar = Calendar.getInstance()
                asCalendar.time = dateOfNewPage
                mPresenter.dateSelected(asCalendar)
            }
        })
    }

    override fun onResume() {
        super.onResume()

        mPagerAdapter = ScreenSlidePagerAdapter(childFragmentManager)
        pager.adapter = mPagerAdapter

        mPresenter.start()
    }



    private fun displayDatePicker() {
        val today = GregorianCalendar()
        val cdp = CalendarDatePickerDialogFragment()
                .setOnDateSetListener(
                        CalendarDatePickerDialogFragment.OnDateSetListener { dialog, year, monthOfYear, dayOfMonth ->
                            val cal = Calendar.getInstance()
                            cal.set(year, monthOfYear, dayOfMonth)
                            mPresenter.dateSelected(cal)
                        })
                .setFirstDayOfWeek(Calendar.MONDAY)
                .setPreselectedDate(
                        today.get(Calendar.YEAR),
                        today.get(Calendar.MONTH),
                        today.get(Calendar.DAY_OF_MONTH))
                .setDateRange(pickerStartDate, pickerEndDate)
                .setDoneText("Wybierz")
                .setCancelText("Anuluj")
                .setThemeCustom(R.style.MyCustomBetterPickersDialogs)

        if (pickerDisabledDays != null) {
            cdp.setDisabledDays(pickerDisabledDays!!)
        }

        cdp.show(fragmentManager, FRAG_TAG_DATE_PICKER)
    }

    private inner class ScreenSlidePagerAdapter(fm: FragmentManager) : FragmentPagerAdapter(fm) {

        override fun getItem(position: Int): Fragment {
            val frag = ScreenSlidePageFragment()
            frag.setDrugHistoryDay(days[position])
            return frag
        }

        override fun getCount(): Int {
            return days.size
        }
    }
}
