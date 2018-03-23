package com.zpi.zpiapp.interactions

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.zpi.zpiapp.R

class InteractionsFragment : Fragment(), InteractionsContract.View {

    override fun setPresenter(presenter: InteractionsContract.Presenter) {
        TODO("not implemented") //To change body of created functions use File | Settings | // File Templates.
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_interactions, container, false)
    }

}// Required empty public constructor
