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
        fun showApiError(errorMessage: String)
        fun finishActivity()

        fun storeLoginInPrefs(id: Int, userType: Int)
        fun loadLoginFromPrefs()

        fun launchAssistantActivity()
    }

    interface Presenter : BasePresenter {
        fun signUpFormRequested()
        fun signInFormRequested()
        fun attemptSignUpSignIn(login: String, email: String, password: String)

        fun setLoginLoadedFromPrefs(id: Int, userType: Int)
    }
}