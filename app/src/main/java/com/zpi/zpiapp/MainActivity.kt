package com.zpi.zpiapp

import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import com.zpi.zpiapp.interactions.InteractionsFragment
import com.zpi.zpiapp.interactions.InteractionsPresenter
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

//        interactionsFragment = supportFragmentManager.findFragmentById(R.idDrug.content) as InteractionsFragment?
//        if (interactionsFragment == null)
//            interactionsFragment = InteractionsFragment()
//        if (interactionsFragment != null)
//            interactionsPresenter = InteractionsPresenter(interactionsFragment!!)
    }
}
