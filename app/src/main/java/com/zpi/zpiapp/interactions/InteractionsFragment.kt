package com.zpi.zpiapp.interactions

import android.content.Context.INPUT_METHOD_SERVICE
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import android.view.inputmethod.InputMethodManager
import android.widget.ArrayAdapter
import com.zpi.zpiapp.R
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.Interaction
import kotlinx.android.synthetic.main.fragment_interactions.*

class InteractionsFragment : Fragment(), InteractionsContract.View {

    lateinit var mPresenter: InteractionsContract.Presenter
    lateinit var mDrugs: Array<Drug>

    override fun hideInteractionSection() {
        interactionsFound.visibility = View.GONE
        interactionsNotFound.visibility = View.GONE
    }

    override fun showNoInteractions() {
        interactionsFound.visibility = View.GONE
        interactionsNotFound.visibility = View.VISIBLE
    }

    override fun showInteractions(interactions: List<Interaction>) {
        interactionsNotFound.visibility = View.GONE
        interactionsFound.visibility = View.VISIBLE

        (recyclerInteractions.adapter as InteractionsAdapter).updateInteractions(interactions)
        interactionsFoundText.text = getString(R.string.found_n_interactions, interactions.size)
    }

    override fun showNoSuchDrug(drugNumber: Int) {
        val shake = AnimationUtils.loadAnimation(context, R.anim.shake)

        when (drugNumber) {
            1 -> drug1.startAnimation(shake)
            2 -> drug2.startAnimation(shake)
        }
    }

    override fun showError() {
        Snackbar.make(interactionsRoot, R.string.connection_error, Snackbar.LENGTH_LONG).show()
    }

    override fun setPresenter(presenter: InteractionsContract.Presenter) {
        mPresenter = presenter
    }

    override fun setDrugs(drugs: Array<Drug>) {
        mDrugs = drugs
        val drugStrings = mDrugs.map { it.name }
        val adapter = ArrayAdapter<String>(context, android.R.layout.simple_dropdown_item_1line, drugStrings)
        drug1.setAdapter(adapter)
        drug2.setAdapter(adapter)
    }


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_interactions, container, false)
    }

    override fun onViewCreated(view: View?, savedInstanceState: Bundle?) {

        recyclerInteractions.adapter = InteractionsAdapter()
        recyclerInteractions.layoutManager = LinearLayoutManager(context)

        floatingActionButton.setOnClickListener {
            removeFocusFromControlPanel()
            hideKeyboard()
            hideInteractionSection() // or/and make fancy loading animation
            mPresenter.interact(drug1.text.toString(), drug2.text.toString())
        }
    }

    private fun removeFocusFromControlPanel() {
        drug1.clearFocus()
        drug2.clearFocus()
    }

    private fun hideKeyboard() {
        val view = InteractionsFragment@ this.activity.currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ this.activity.getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun onResume() {
        super.onResume()
        mPresenter.start()
    }

}// Required empty public constructor
