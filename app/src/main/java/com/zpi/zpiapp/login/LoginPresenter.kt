package com.zpi.zpiapp.login

class LoginPresenter(val mView: LoginContract.View): LoginContract.Presenter {

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
}