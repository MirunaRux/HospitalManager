package com.miruna.hospitalmanager.application.dashboard

import android.content.Intent
import android.os.Bundle
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.view.MenuItem
import android.view.View
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_dashboard.*
import kotlinx.android.synthetic.main.app_bar_dashboard.*
import android.widget.TextView
import com.miruna.hospitalmanager.application.agenda.AddEventActivity
import com.miruna.hospitalmanager.application.agenda.AgendaListFragment
import com.miruna.hospitalmanager.application.agenda.Event
import com.miruna.hospitalmanager.application.drug.AddDrugActivity
import com.miruna.hospitalmanager.application.drug.DrugListFragment
import com.miruna.hospitalmanager.application.pacient.AddPacientActivity
import com.miruna.hospitalmanager.application.pacient.PacientListFragment
import kotlinx.android.synthetic.main.content_dashboard.*

interface OnActivityFragmentCommunication {
    fun onAddObject(TAG: String, bundle: Bundle? = null)
}

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnActivityFragmentCommunication {

    lateinit var newEvent : Event

    override fun onAddObject(TAG: String, bundle: Bundle?) {
        /*if(TAG.equals("EVENT_LIST_FRAGMENT")) {
            val eventListFragment = AgendaListFragment()

            bundle?.putParcelable("eveniment nou", newEvent)
            eventListFragment.setArguments(bundle)
        }*/

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        var dahsboardIntent = getIntent()
        val username = dahsboardIntent.getStringExtra("EXTRA_USERNAME")
        val role = dahsboardIntent.getStringExtra("EXTRA_ROLE")
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById(R.id.dashboard_username) as TextView
        navUsername.text = username + "     " + role

        /*floating_button.setOnClickListener {
            //if()
            when (supportFragmentManager.fragments[supportFragmentManager.fragments.size]) {
                is PacientListFragment ->{

                }

            }

            val extraEventName = dahsboardIntent.getStringExtra("EXTRA_EVENT_NAME")
            val extraEventLocation = dahsboardIntent.getStringExtra("EXTRA_EVENT_LOCATION")
            val extraEventPacient = dahsboardIntent.getStringExtra("EXTRA_EVENT_PACIENT")
            val extraEventDoctor = dahsboardIntent.getStringExtra("EXTRA_EVENT_DOCTOR")

            if(extraEventName!= null && extraEventLocation != null && extraEventPacient != null && extraEventDoctor != null){
                val newEvent = Event(10, extraEventName, extraEventLocation, extraEventPacient, extraEventDoctor)
            }
        }*/

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_pacienti -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = PacientListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment, "PACIENT_LIST_FRAGMENT")
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()

                floating_button.setOnClickListener {
                    val addPacientActivity: Intent =  Intent(this, AddPacientActivity::class.java)
                    startActivity(addPacientActivity)
                }
            }
            R.id.nav_agenda -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = AgendaListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment, "AGENDA_LIST_FRAGMENT")
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()

                floating_button.setOnClickListener {
                    val addEventActivity: Intent =  Intent(this, AddEventActivity::class.java)
                    startActivity(addEventActivity)
                }
            }
            R.id.nav_medicamente -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = DrugListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment, "DRUGS_LIST_FRAGMENT")
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()

                floating_button.setOnClickListener {
                    val addDrugActivity: Intent =  Intent(this, AddDrugActivity::class.java)
                    startActivity(addDrugActivity)
                }
            }
            R.id.nav_cereri -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
