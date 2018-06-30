package com.zpi.zpiapp

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.BottomNavigationView
import android.support.v4.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import com.zpi.zpiapp.careAssistantCharges.CareAssistantChargesActivity
import com.zpi.zpiapp.careAssistants.CareAssistantsActivity
import com.zpi.zpiapp.currentDrugsList.CurrentDrugsFragment
import com.zpi.zpiapp.currentDrugsList.CurrentDrugsPresenter
import com.zpi.zpiapp.drugHistory.DrugHistoryFragment
import com.zpi.zpiapp.drugHistory.DrugHistoryPresenter
import com.zpi.zpiapp.editPrivateData.EditPrivateDataActivity
import com.zpi.zpiapp.editTodayDrugs.EditTodayDrugsFragment
import com.zpi.zpiapp.editTodayDrugs.EditTodayDrugsPresenter
import com.zpi.zpiapp.interactions.InteractionsFragment
import com.zpi.zpiapp.interactions.InteractionsPresenter
import com.zpi.zpiapp.login.LoginActivity
import com.zpi.zpiapp.login.LoginActivity.Companion.KEY_PREFS_ID
import com.zpi.zpiapp.login.LoginActivity.Companion.KEY_PREFS_USER_TYPE
import com.zpi.zpiapp.physicians.PhysiciansActivity
import com.zpi.zpiapp.utlis.User
import com.zpi.zpiapp.utlis.User.userId
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var interactionsFragment: InteractionsFragment
    lateinit var interactionsPresenter: InteractionsPresenter
    lateinit var drugHistoryFragment: DrugHistoryFragment
    lateinit var drugHistoryPresenter: DrugHistoryPresenter
    lateinit var currentDrugsFragment: CurrentDrugsFragment
    lateinit var currentDrugsPresenter: CurrentDrugsPresenter
    lateinit var editTodayDrugsFragment: EditTodayDrugsFragment
    lateinit var editTodayDrugsPresenter: EditTodayDrugsPresenter

    private val mOnNavigationItemSelectedListener = BottomNavigationView.OnNavigationItemSelectedListener { item ->
        when (item.itemId) {
            R.id.navigation_home -> {
                if (::editTodayDrugsFragment.isInitialized.not()){
                    editTodayDrugsFragment = EditTodayDrugsFragment()
                    editTodayDrugsPresenter = EditTodayDrugsPresenter(editTodayDrugsFragment)
                }
                replaceFragment(editTodayDrugsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_current_drugs -> {
                if(::currentDrugsFragment.isInitialized.not()){
                    currentDrugsFragment = CurrentDrugsFragment()
                    currentDrugsPresenter = CurrentDrugsPresenter(currentDrugsFragment)
                }
                replaceFragment(currentDrugsFragment)
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_notifications -> {
                return@OnNavigationItemSelectedListener true
            }
            R.id.navigation_drug_history -> {
                if (::drugHistoryFragment.isInitialized.not()) {
                    drugHistoryFragment = DrugHistoryFragment()
                    drugHistoryPresenter = DrugHistoryPresenter(drugHistoryFragment)
                }
                replaceFragment(drugHistoryFragment)
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
        navigation.selectedItemId=R.id.navigation_home


        startActivity(Intent(this, LoginActivity::class.java))
    }


    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.test_menu,menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if(item?.itemId==R.id.careAssistantMenuId)
            startActivity(Intent(this,CareAssistantsActivity::class.java))
        if(item?.itemId==R.id.physicianMenuId)
            startActivity(Intent(this,PhysiciansActivity::class.java))
        if(item?.itemId==R.id.accountMenuId)
            startActivity(Intent(this,EditPrivateDataActivity::class.java))
        if(item?.itemId==R.id.logOutMenu){
            getPreferences(Context.MODE_PRIVATE).edit()
                    .putInt(KEY_PREFS_ID, -1)
                    .putInt(KEY_PREFS_USER_TYPE, -1)
                    .commit()
            User.userId = -1

            finish()
        }

        return true
    }
}
