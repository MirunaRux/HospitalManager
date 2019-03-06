package com.miruna.hospitalmanager.application.dashboard

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
import com.miruna.hospitalmanager.application.agenda.AgendaListFragment
import com.miruna.hospitalmanager.application.drug.DrugListFragment
import com.miruna.hospitalmanager.application.pacient.AddPacientFragment
import com.miruna.hospitalmanager.application.pacient.PacientDetailsFragment
import com.miruna.hospitalmanager.application.pacient.PacientListFragment

interface OnActivityFragmentCommunication {
    fun onAddFragment(TAG: String, bundle: Bundle? = null)
}

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener, OnActivityFragmentCommunication {

    override fun onAddFragment(TAG: String, bundle: Bundle?) {
        if(TAG.equals("DETAILS_FRAGMENT")) {
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = PacientDetailsFragment()
            fragmentTransaction.add(R.id.fragmentsContainer, fragment)
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.commit()
        }else if (TAG.equals("ADD_NEW_PACIENT_FRAGMENT")){
            val fragmentTransaction = supportFragmentManager.beginTransaction()
            val fragment = AddPacientFragment()
            fragmentTransaction.add(R.id.fragmentsContainer, fragment)
            fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            fragmentTransaction.commit()
        }

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
                fragmentTransaction.replace(R.id.content_dashboard, fragment)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()
            }
            R.id.nav_agenda -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = AgendaListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()
            }
            R.id.nav_medicamente -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = DrugListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment)
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()
            }
            R.id.nav_cereri -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
