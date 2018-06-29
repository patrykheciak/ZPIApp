package com.zpi.zpiapp.login

import android.text.TextUtils
import android.util.Log
import com.zpi.zpiapp.model.Drug
import com.zpi.zpiapp.utlis.RetrofitInstance
import com.zpi.zpiapp.utlis.User
import okhttp3.ResponseBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class LoginPresenter(val mView: LoginContract.View) : LoginContract.Presenter {


    var loginType = LoginType.SIGN_UP

    init {
        mView.setPresenter(this)
    }

    override fun start() {
        mView.loadLoginFromPrefs()
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

        if (errorOcured.not()) {
            mView.hideKeyboard()
            mView.clearFocus()

            // TODO wszystkie pola sa ok. Tutaj trzeba logowac/rejestrowac
            mView.showProgress(true)
            when (loginType) {
                LoginType.SIGN_UP -> {

                }
                LoginType.SIGN_IN -> {

                    loguj(login, password)
                }
            }
        }
    }

    override fun setLoginLoadedFromPrefs(id: Int, userType: Int) {
        if (id > 0) {
            User.userId = id
            User.userType = userType
            mView.finishActivity()
        }
    }

    private fun loguj(login: String, password: String) {
        RetrofitInstance.userService
                .loginPerson(login, password).enqueue(object : Callback<IntArray> {
                    override fun onFailure(call: Call<IntArray>?, t: Throwable?) {
                        Log.d("LoginPresenter", "" + t.toString())
                        call?.clone()?.enqueue(this)
                    }

                    override fun onResponse(call: Call<IntArray>?, response: Response<IntArray>?) {
                        Log.d("LoginPresenter", response?.body().toString())
                        if (response != null) {
                            if (response.isSuccessful) {
                                val body = response.body()
                                body?.let {
                                    mView.showApiError("${body[0]} ${body[1]}")

                                    val id = body[0]
                                    val userType = body[1]

                                    if (id > 0){
                                        // successfully logged in
                                        User.userId = id
                                        User.userType = userType
                                        mView.storeLoginInPrefs(id, userType)
                                        mView.finishActivity()
                                    } else {
                                        // login failed
                                        mView.showProgress(false)
                                        mView.showApiError("Nie ma takiego użytkownika")
                                    }
                                }
                            }
                        }
                    }

                })
    }

    private fun isEmailValid(target: CharSequence): Boolean {
        return !TextUtils.isEmpty(target) && android.util.Patterns.EMAIL_ADDRESS.matcher(target).matches()
    }
}