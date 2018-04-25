package com.zpi.zpiapp.login

import android.os.Build
import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.zpi.zpiapp.R
import kotlinx.android.synthetic.main.activity_login.*


class LoginActivity : AppCompatActivity(), LoginContract.View {

    private lateinit var presenter: LoginContract.Presenter
    private var loginType: LoginType? = null

    companion object {
        const val KEY_LOGIN_TYPE = "KEY_LOGIN_TYPE"

        const val KEY_LOGIN_TEXT = "KEY_LOGIN_TEXT"
        const val KEY_EMAIL_TEXT = "KEY_EMAIL_TEXT"
        const val KEY_PASSWORD_TEXT = "KEY_PASSWORD_TEXT"
    }

    // =============================  from now on Android callbacks  =============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginPresenter(this)

        button_sign_up.setOnClickListener { presenter.signUpFormRequested() }
        button_sign_in.setOnClickListener { presenter.signInFormRequested() }

        if (savedInstanceState == null) {
            // If first launch.
            loginType = LoginType.SIGN_UP
        }
//        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
//            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
//                attemptLogin()
//                return@OnEditorActionListener true
//            }
//            false
//        })
//        email_sign_in_button.setOnClickListener { attemptLogin() }
    }

    override fun onSaveInstanceState(outState: Bundle?) {
        loginType?.let {
            when (it) {
                LoginType.SIGN_UP -> outState?.putString(KEY_LOGIN_TYPE, "SIGN_UP")
                LoginType.SIGN_IN -> outState?.putString(KEY_LOGIN_TYPE, "SIGN_IN")
            }
        }

        outState?.putString(KEY_LOGIN_TEXT, login.text.toString())
        outState?.putString(KEY_EMAIL_TEXT, email.text.toString())
        outState?.putString(KEY_PASSWORD_TEXT, password.text.toString())

        super.onSaveInstanceState(outState)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle?) {

        savedInstanceState?.let {
            if (it.containsKey(KEY_LOGIN_TYPE)) {
                val savedLoginType = it.getString(KEY_LOGIN_TYPE)
                if (savedLoginType == "SIGN_UP") {
                    loginType = LoginType.SIGN_UP
                    presenter.signUpFormRequested()
                } else if (savedLoginType == "SIGN_IN") {
                    loginType = LoginType.SIGN_IN
                    presenter.signInFormRequested()
                }
            }

            val loginText = it.getString(KEY_LOGIN_TEXT)
            login.setText(loginText)
            login.setSelection(loginText.length) // move cursor behind last character

            val emailText = it.getString(KEY_EMAIL_TEXT)
            email.setText(emailText)
            email.setSelection(emailText.length) // move cursor behind last character

            val passwordText = it.getString(KEY_PASSWORD_TEXT)
            password.setText(passwordText)
            password.setSelection(passwordText.length) // move cursor behind last character
        }
    }

    // =========================  from now on impl of LoginContract.View  =========================

    override fun setPresenter(presenter: LoginContract.Presenter) {
        this.presenter = presenter
    }

    override fun setSignUpForm() {
        loginType = LoginType.SIGN_UP

        button_sign_in.setTextColor(ContextCompat.getColor(this, R.color.primary_text_default_material_light))
        button_sign_up.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        
        til_email.animate().alpha(1.0f)
        til_email.visibility = View.VISIBLE

        btn_sign_in_up.setText(R.string.action_sign_up)
    }

    override fun setSignInForm() {
        loginType = LoginType.SIGN_IN

        button_sign_in.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        button_sign_up.setTextColor(ContextCompat.getColor(this, R.color.primary_text_default_material_light))

        til_email.animate().alpha(0.0f)
        til_email.visibility = View.GONE

        btn_sign_in_up.setText(R.string.action_sign_in)
    }

    /**
     * Attempts to sign in or register the account specified by the login form.
     * If there are form errors (invalid email, missing fields, etc.), the
     * errors are presented and no actual login attempt is made.
     */
//    private fun attemptLogin() {
////        if (mAuthTask != null) {
////            return
////        }
//
//        // Reset errors.
//        email.error = null
//        password.error = null
//
//        // Store values at the time of the login attempt.
//        val emailStr = email.text.toString()
//        val passwordStr = password.text.toString()
//
//        var cancel = false
//        var focusView: View? = null
//
//        // Check for a valid password, if the user entered one.
//        if (!TextUtils.isEmpty(passwordStr) && !isPasswordValid(passwordStr)) {
//            password.error = getString(R.string.error_invalid_password)
//            focusView = password
//            cancel = true
//        }
//
//        // Check for a valid email address.
//        if (TextUtils.isEmpty(emailStr)) {
//            email.error = getString(R.string.error_field_required)
//            focusView = email
//            cancel = true
//        } else if (!isEmailValid(emailStr)) {
//            email.error = getString(R.string.error_invalid_email)
//            focusView = email
//            cancel = true
//        }
//
//        if (cancel) {
//            // There was an error; don't attempt login and focus the first
//            // form field with an error.
//            focusView?.requestFocus()
//        } else {
//            // Show a progress spinner, and kick off a background task to
//            // perform the user login attempt.
//            showProgress(true)
////            mAuthTask = UserLoginTask(emailStr, passwordStr)
////            mAuthTask!!.execute(null as Void?)
//        }
//    }

//    private fun isEmailValid(email: String): Boolean {
//        //TODO: Replace this with your own logic
//        return email.contains("@")
//    }
//
//    private fun isPasswordValid(password: String): Boolean {
//        //TODO: Replace this with your own logic
//        return password.length > 4
//    }

    /**
     * Shows the progress UI and hides the login form.
     */
//    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
//    private fun showProgress(show: Boolean) {
//        // On Honeycomb MR2 we have the ViewPropertyAnimator APIs, which allow
//        // for very easy animations. If available, use these APIs to fade-in
//        // the progress spinner.
//
//        val shortAnimTime = resources.getInteger(android.R.integer.config_shortAnimTime).toLong()
//
//        login_form.visibility = if (show) View.GONE else View.VISIBLE
//        login_form.animate()
//                .setDuration(shortAnimTime)
//                .alpha((if (show) 0 else 1).toFloat())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        login_form.visibility = if (show) View.GONE else View.VISIBLE
//                    }
//                })
//
//        login_progress.visibility = if (show) View.VISIBLE else View.GONE
//        login_progress.animate()
//                .setDuration(shortAnimTime)
//                .alpha((if (show) 1 else 0).toFloat())
//                .setListener(object : AnimatorListenerAdapter() {
//                    override fun onAnimationEnd(animation: Animator) {
//                        login_progress.visibility = if (show) View.VISIBLE else View.GONE
//                    }
//                })
//
//    }
//

    /**
     * Represents an asynchronous login/registration task used to authenticate
     * the user.
     */
//    inner class UserLoginTask internal constructor(private val mEmail: String, private val mPassword: String) : AsyncTask<Void, Void, Boolean>() {
//
//        override fun onPostExecute(success: Boolean?) {
//            mAuthTask = null
//            showProgress(false)
//
//            if (success!!) {
//                finish()
//            } else {
//                password.error = getString(R.string.error_incorrect_password)
//                password.requestFocus()
//            }
//        }
//
//        override fun onCancelled() {
//            mAuthTask = null
//            showProgress(false)
//        }
//    }

}
