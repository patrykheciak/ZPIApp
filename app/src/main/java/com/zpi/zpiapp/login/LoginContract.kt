package com.zpi.zpiapp.login

import com.zpi.zpiapp.BasePresenter
import com.zpi.zpiapp.BaseView


interface LoginContract {
    interface View : BaseView<Presenter> {
        fun setSignUpForm()
        fun setSignInForm()
        fun hideKeyboard()
        fun clearErrors()
        fun errorLoginEmpty()
        fun errorLoginToShort(loginMinLength: Int)
        fun errorEmailEmpty()
        fun errorEmailInvalid()
        fun errorPasswordEmpty()
        fun errorPasswordToShort(passwordMinLength: Int)
        fun clearFocus()
        fun showProgress(showProgress: Boolean)
    }

    interface Presenter : BasePresenter {
        fun signUpFormRequested()
        fun signInFormRequested()
        fun attemptSignUpSignIn(login: String, email: String, password: String)
    }
}