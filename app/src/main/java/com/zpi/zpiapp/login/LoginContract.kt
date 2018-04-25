package com.zpi.zpiapp.login

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView
import com.zpi.zpiapp.model.CareAssistant


interface LoginContract {
    interface View: BaseView<Presenter> {
        fun setSignUpForm()
        fun setSignInForm()
    }

    interface Presenter : BasePresenter {
        fun signUpFormRequested()
        fun signInFormRequested()
    }
}