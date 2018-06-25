package com.zpi.zpiapp.utlis

import android.view.View
import java.util.*



object Utils{
    fun hideView( view:View ){
        view.visibility=View.GONE
    }
    fun showView( view: View ){
        view.visibility=View.VISIBLE
    }

    /**
     * sets all the time related fields to ZERO!
     *
     * @param date
     *
     * @return Date with hours, minutes, seconds and ms set to ZERO!
     */
    fun zeroTime(date: Date): Date {
        return Utils.setTime(date, 0, 0, 0, 0)
    }

    fun todayZeroTime():Date{
        return zeroTime(Date())
    }

    /**
     * Set the time of the given Date
     *
     * @param date
     * @param hourOfDay
     * @param minute
     * @param second
     * @param ms
     *
     * @return new instance of java.util.Date with the time set
     */
    fun setTime(date: Date, hourOfDay: Int, minute: Int, second: Int, ms: Int): Date {
        val gc = GregorianCalendar()
        gc.time = date
        gc.set(Calendar.HOUR_OF_DAY, hourOfDay)
        gc.set(Calendar.MINUTE, minute)
        gc.set(Calendar.SECOND, second)
        gc.set(Calendar.MILLISECOND, ms)
        return gc.time
    }
}