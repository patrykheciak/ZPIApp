package com.zpi.zpiapp.physicians

import android.app.AlertDialog
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager

import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.Physician
import kotlinx.android.synthetic.main.fragment_physicians.*


class PhysiciansFragment : Fragment(),PhysiciansContract.View {
    private lateinit var mPresenter: PhysiciansContract.Presenter
    private lateinit var mPhysiciansAdapter: PhysiciansAdapter

    override fun setPresenter(presenter: PhysiciansContract.Presenter) {
        this.mPresenter = presenter
    }

    override fun showPhysicians() {
        physicians_not_found_frame.visibility=View.GONE
        physicians_connection_problem_frame.visibility=View.GONE
        physicians_found_frame.visibility=View.VISIBLE

    }

    override fun showPhysiciansNotFound() {
        physicians_connection_problem_frame.visibility=View.GONE
        physicians_found_frame.visibility=View.GONE
        physicians_not_found_frame.visibility=View.VISIBLE
    }

    override fun showConnectionError() {
        physicians_found_frame.visibility=View.GONE
        physicians_not_found_frame.visibility=View.GONE
        physicians_connection_problem_frame.visibility=View.VISIBLE
    }

    override fun showSnackBarError(error: String) {
        Snackbar.make(physicians_root,error,Snackbar.LENGTH_LONG).show();
    }

    override fun clearAddPhysician() {
        physicians_add_panel_pwz.text.clear()
    }

    override fun addPhysician(physician: Physician) {
        mPhysiciansAdapter.addItemToBeginning(physician)
    }

    override fun removePhysician(physician: Physician) {
        mPhysiciansAdapter.deleteItem(physician)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_physicians, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        mPhysiciansAdapter=PhysiciansAdapter(object :PhysiciansAdapter.ClickListener{
            override fun onRemoveButtonClicked(physicianId: Int) {
                mPresenter.checkRemovingPhysician(physicianId)
            }
        })
        physicians_found_recycler_view.adapter = mPhysiciansAdapter
        physicians_found_recycler_view.layoutManager=LinearLayoutManager(context)

        physicians_add_panel_fab.setOnClickListener {
            hideKeyboard()
            mPresenter.addNewPhysician(physicians_add_panel_pwz.text.toString() )
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun showRemoveDialog(physician: Physician) {
        val builder = AlertDialog.Builder(context)

        builder.setTitle("Usuwanie")
                .setMessage("Czy na pewno chcesz usunąć ${physician.name} ${physician.surname}?")
                .setPositiveButton(android.R.string.yes, { _, _ ->
                    mPresenter.removePhysician(physician.idPhysician)
                })
                .setNegativeButton(android.R.string.no, { _, _ ->
                })
                .setIcon(R.drawable.ic_error_outline_black_24px)
                .show()
    }

}
