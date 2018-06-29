package com.zpi.zpiapp.login

import android.app.Application
import android.content.Context
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.animation.AnimationUtils
import android.view.inputmethod.EditorInfo
import android.view.inputmethod.InputMethodManager
import android.widget.TextView
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

        const val KEY_PREFS_ID = "KEY_PREFS_ID"
        const val KEY_PREFS_USER_TYPE = "KEY_PREFS_USER_TYPE"
    }

    // =============================  from now on Android callbacks  =============================

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        LoginPresenter(this)

        button_sign_up.setOnClickListener { presenter.signUpFormRequested() }
        button_sign_in.setOnClickListener { presenter.signInFormRequested() }
        forgot_password.setOnClickListener {
            /* TODO activity z edittextem na email i przyciskiem resetuj haslo ewentualnie dać to do webowki */
        }
        btn_sign_in_up.setOnClickListener { presenter.attemptSignUpSignIn(
                login.text.toString(),
                email.text.toString(),
                password.text.toString()) }

        if (savedInstanceState == null) {
            // If first launch.
            loginType = LoginType.SIGN_UP
        }
        password.setOnEditorActionListener(TextView.OnEditorActionListener { _, id, _ ->
            if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                presenter.attemptSignUpSignIn(
                        login.text.toString(),
                        email.text.toString(),
                        password.text.toString())
                return@OnEditorActionListener true
            }
            false
        })
        presenter.start()
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

    override fun onBackPressed() {
        finish()
        moveTaskToBack(true)
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

        forgot_password.visibility = View.GONE

        btn_sign_in_up.setText(R.string.action_sign_up)
        password.setImeActionLabel(resources.getString(R.string.action_sign_up), 6)
    }

    override fun setSignInForm() {
        loginType = LoginType.SIGN_IN

        button_sign_in.setTextColor(ContextCompat.getColor(this, R.color.colorAccent))
        button_sign_up.setTextColor(ContextCompat.getColor(this, R.color.primary_text_default_material_light))

        til_email.animate().alpha(0.0f)
        til_email.visibility = View.GONE

        forgot_password.visibility = View.VISIBLE

        btn_sign_in_up.setText(R.string.action_sign_in)
        password.setImeActionLabel(resources.getString(R.string.action_sign_in), 6)
    }

    override fun hideKeyboard() {
        val view = InteractionsFragment@ currentFocus
        if (view != null) {
            val imm = InteractionsFragment@ getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            imm.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun clearErrors() {
        til_login.error = null
        til_email.error = null
        til_password.error = null
    }

    override fun errorLoginEmpty() {
        til_login.error = getString(R.string.error_login_empty)
        shakeView(til_login)
        login.requestFocus()
    }

    override fun errorLoginToShort(loginMinLength: Int) {
        til_login.error = getString(R.string.error_login_too_short, loginMinLength)
        shakeView(til_login)
        login.requestFocus()
    }

    override fun errorEmailEmpty() {
        til_email.error = getString(R.string.error_email_empty)
        shakeView(til_email)
        email.requestFocus()
    }

    override fun errorEmailInvalid() {
        til_email.error = getString(R.string.error_email_invalid)
        shakeView(til_email)
        email.requestFocus()
    }

    override fun errorPasswordEmpty() {
        til_password.error = getString(R.string.error_password_empty)
        shakeView(til_password)
        password.requestFocus()}

    override fun errorPasswordToShort(passwordMinLength: Int) {
        til_password.error = getString(R.string.error_password_too_short, passwordMinLength)
        shakeView(til_password)
        password.requestFocus()
    }

    override fun clearFocus() {
        focus_thief.requestFocus()
    }

    override fun showProgress(showProgress: Boolean) {
        when(showProgress){
            true -> login_progress.visibility = View.VISIBLE
            false -> login_progress.visibility = View.GONE
        }
    }

    private fun shakeView(view: View) {
        val shake = AnimationUtils.loadAnimation(this, R.anim.shake)
        view.startAnimation(shake)
    }

    override fun showApiError(errorMessage: String) {
        Snackbar.make(cardView, errorMessage, Snackbar.LENGTH_LONG).show()
    }

    override fun finishActivity() {
        finish()
    }

    override fun storeLoginInPrefs(id: Int, userType: Int){
        getPreferences(Context.MODE_PRIVATE).edit()
                .putInt(KEY_PREFS_ID,id)
                .putInt(KEY_PREFS_USER_TYPE, userType)
                .apply()
    }

    override fun loadLoginFromPrefs() {
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val id = prefs.getInt(KEY_PREFS_ID, -1)
        val userType = prefs.getInt(KEY_PREFS_USER_TYPE, -1)
        presenter.setLoginLoadedFromPrefs(id, userType)
    }
}