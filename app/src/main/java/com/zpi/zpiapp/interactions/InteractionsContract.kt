package com.zpi.zpiapp.interactions

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView

interface InteractionsContract {

    interface View : BaseView<Presenter> {

    }

    interface Presenter : BasePresenter {

    }
}