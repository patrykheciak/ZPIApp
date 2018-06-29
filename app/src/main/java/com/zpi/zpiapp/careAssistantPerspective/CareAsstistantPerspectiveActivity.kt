package com.zpi.zpiapp.careAssistantPerspective

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.Snackbar
import android.support.v7.app.AppCompatActivity
import android.view.Menu
import android.view.MenuItem
import com.zpi.zpiapp.R
import com.zpi.zpiapp.careAssistantCharges.CareAssistantChargesActivity
import com.zpi.zpiapp.careAssistants.CareAssistantsActivity
import com.zpi.zpiapp.editPrivateData.EditPrivateDataActivity
import com.zpi.zpiapp.physicians.PhysiciansActivity

import kotlinx.android.synthetic.main.activity_care_asstistant_perspective.*

class CareAsstistantPerspectiveActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_care_asstistant_perspective)


    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater = menuInflater
        inflater.inflate(R.menu.care_assistant_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem?): Boolean {
        if (item?.itemId == R.id.assistant_item_my_patients) {

        }
        return true
    }
}
