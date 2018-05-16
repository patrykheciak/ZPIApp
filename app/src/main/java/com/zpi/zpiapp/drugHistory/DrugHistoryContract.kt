package com.zpi.zpiapp.drugHistory

import android.util.SparseArray
import com.codetroopers.betterpickers.calendardatepicker.MonthAdapter
import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import java.util.*

interface DrugHistoryContract {

    interface View : BaseView<Presenter> {
        fun setDates(first: List<Date>)
        fun setDatesForSpinner(
                start: MonthAdapter.CalendarDay,
                end: MonthAdapter.CalendarDay,
                disabledDays: SparseArray<MonthAdapter.CalendarDay>)
        fun setButtonText(string: String)
        fun setAdapterPage(page: Int)
    }

    interface Presenter : BasePresenter {
        fun dateSelected(date: Calendar)

    }
}