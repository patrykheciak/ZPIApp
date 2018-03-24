package com.zpi.zpiapp.interactions

import com.zpi.zpiapp.model.Drug
import java.util.*

class InteractionsPresenter(val mInteractionsView: InteractionsContract.View) : InteractionsContract.Presenter {
    private lateinit var drugs: Array<Drug>

    init {
        mInteractionsView.setPresenter(this)
    }

    override fun start() {
        drugs = Mock.drugs()
        mInteractionsView.setDrugs(drugs)
    }

    override fun interact(drug1: String, drug2: String) {
        val d1 = findDrugByName(drug1)
        val d2 = findDrugByName(drug2)

        if (d1 == null)
            mInteractionsView.showNoSuchDrug(1)
        if (d2 == null)
            mInteractionsView.showNoSuchDrug(2)

        if (d1 != null && d2 != null) {
            // ask API for interaction

            // sumulacja odpowiedzi API. Docelowo bedzie chyba asynchronicznie,
            // wiec nie bedzie jej w tym miejscu
            if (Random().nextInt(2) == 0)
                mInteractionsView.showInteractions(Mock.interactions())
            else
                mInteractionsView.showNoInteractions()
        } else {
            mInteractionsView.hideInteractionSection()
        }

    }

    private fun findDrugByName(drug1: String): Drug? {
        return drugs.firstOrNull { it.name == drug1 }
    }
}