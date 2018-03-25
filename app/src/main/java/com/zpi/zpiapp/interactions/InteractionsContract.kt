package com.zpi.zpiapp.interactions

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.model.Interaction

interface InteractionsContract {

    interface View : BaseView<Presenter> {
        fun showNoInteractions()
        fun showInteractions(interactions: List<Interaction>)
        fun setDrugs(drugs: Array<Drug>)
        fun showNoSuchDrug(drugNumber: Int)
        fun hideInteractionSection()
        fun showError()
    }

    interface Presenter : BasePresenter {
        fun interact(drug1: String, drug2: String)
    }
}