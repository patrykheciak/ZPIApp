package com.zpi.zpiapp.login

import android.text.TextUtils


class LoginPresenter(val mView: LoginContract.View) : LoginContract.Presenter {
    var loginType = LoginType.SIGN_UP

    init {
        mView.setPresenter(this)
    }

    override fun start() {

    }

    override fun signUpFormRequested() {
        loginType = LoginType.SIGN_UP
        mView.setSignUpForm()
    }

    override fun signInFormRequested() {
        loginType = LoginType.SIGN_IN
        mView.setSignInForm()
    }

    override fun attemptSignUpSignIn(login: String, email: String, password: String) {
        val LOGIN_MIN_LENGTH = 4
        val PASSWORD_MIN_LENGTH = 8

        mView.clearErrors()

        var errorOcured = false

        // Reversed order checking needed to focus errors in right order (login -> email -> pass)
        // check password
        if (password.isEmpty()) {
            errorOcured = true
            mView.errorPasswordEmpty()
        } else if (password.length < PASSWORD_MIN_LENGTH) {
            errorOcured = true
            mView.errorPasswordToShort(PASSWORD_MIN_LENGTH)
        }

        // check email (optionally)
        if (loginType == LoginType.SIGN_UP) {
            if (email.isEmpty()) {
                errorOcured = true
                mView.errorEmailEmpty()
            } else if (!isEmailValid(email)) {
                errorOcured = true
                mView.errorEmailInvalid()
            }
        }

        // check login
        if (login.isEmpty()) {
            errorOcured = true
            mView.errorLoginEmpty()
        } else if (login.length < LOGIN_MIN_LENGTH) {
            errorOcured = true
            mView.errorLoginToShort(LOGIN_MIN_LENGTH)
        }

        if (errorOcured.not()){
            mView.hideKeyboard()
            mView.clearFocus()

            // TODO wszystkie pola sa ok. Tutaj trzeba logowac/rejestrowac
            mView.showProgress(true)
            when (loginType){
                LoginType.SIGN_UP -> {

                }
                LoginType.SIGN_IN -> {

                }
            }
        }
    }

    private fun isEmailValid(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}