package com.zpi.zpiapp.editPrivateData

import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.User
import kotlinx.android.synthetic.main.fragment_drug_therapy_edit.*
import kotlinx.android.synthetic.main.fragment_private_data.*

class EditPrivateDataFragment:Fragment(),EditPrivateDataContract.View{
    private lateinit var mPresenter: EditPrivateDataContract.Presenter

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_private_data, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        privateDataOkBtn.setOnClickListener {
            hideKeyboard()
            mPresenter.sendData(
                    privateDataNameET.text.toString(),
                    privateDataSurnameET.text.toString(),
                    privateDataIsActiveCB.isChecked)
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    override fun setPresenter(presenter: EditPrivateDataContract.Presenter) {
        mPresenter=presenter
    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun loadData(user: User) {
        privateDataNameET.setText(user.Name)
        privateDataSurnameET.setText(user.Surname)
        privateDataIsActiveCB.isChecked=user.isActive
    }

    override fun showSnackBarError(error: String) {
        Snackbar.make(privateDataRoot, error, Snackbar.LENGTH_LONG).show()
    }


}