package com.zpi.zpiapp

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.zpi.zpiapp.careAssistants.CareAssistantsActivity
import com.zpi.zpiapp.interactions.InteractionsFragment
import com.zpi.zpiapp.interactions.InteractionsPresenter
import com.zpi.zpiapp.login.LoginActivity
import com.zpi.zpiapp.physicians.PhysiciansActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var interactionsFragment: InteractionsFragment
    lateinit var interactionsPresenter: InteractionsPresenter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_dashboard -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigaation_iteractions -> {
                if (::interactionsFragment.isInitialized.not()) {
                    interactionsFragment = InteractionsFragment()
                    interactionsPresenter = InteractionsPresenter(interactionsFragment)
                }
                replaceFragment(interactionsFragment)
                return@OnNavigationItemSelectedListener true
            }
        }
        false
    }

    private fun MainActivity.replaceFragment(fragment: Fragment) {

        supportFragmentManager
                .beginTransaction()
                .replace(R.id.content, fragment)
                .commit()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        navigation.setOnNavigationItemSelectedListener(mOnNavigationItemSelectedListener)

        startActivity(Intent(this, LoginActivity::class.java))
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.test_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.title=="Opiekunowie")
            startActivity(Intent(this,CareAssistantsActivity::class.java))
        if(item?.title=="Lekarze")
            startActivity(Intent(this,PhysiciansActivity::class.java))
        return true
    }
}
