package com.miruna.hospitalmanager.application.dashboard

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.provider.SyncStateContract
import android.support.design.widget.NavigationView
import android.support.v4.app.FragmentTransaction
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.util.Log
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
import com.miruna.hospitalmanager.application.noutati.NewsActivity
import com.miruna.hospitalmanager.application.pacient.AddPacientActivity
import com.miruna.hospitalmanager.application.pacient.Pacient
import com.miruna.hospitalmanager.application.pacient.PacientListFragment
import com.miruna.hospitalmanager.application.request.AddRequestActivity
import com.miruna.hospitalmanager.application.request.RequestListFragment
import com.miruna.hospitalmanager.application.settings.SettingsActivity
import com.miruna.hospitalmanager.application.utils.Constants
import kotlinx.android.synthetic.main.content_dashboard.*

interface OnActivityFragmentCommunication {
    fun onAddObject(TAG: String, bundle: Bundle? = null)
}

class DashboardActivity : AppCompatActivity(), NavigationView.OnNavigationItemSelectedListener,
    OnActivityFragmentCommunication {

    lateinit var newEvent: Event
    var role: String = ""
    var username: String = ""

    override fun onAddObject(TAG: String, bundle: Bundle?) {
        /*if(TAG.equals("EVENT_LIST_FRAGMENT")) {
            val eventListFragment = AgendaListFragment()

            bundle?.putParcelable("eveniment nou", newEvent)
            eventListFragment.setArguments(bundle)
        }*/

    }

    @SuppressLint("RestrictedApi")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)
        setSupportActionBar(toolbar)

        var dahsboardIntent = getIntent()
        username = dahsboardIntent.getStringExtra("EXTRA_USERNAME")
        role = dahsboardIntent.getStringExtra("EXTRA_ROLE")
        Log.i("gigi dashbord", role)
        val navigationView = findViewById<View>(R.id.nav_view) as NavigationView
        val headerView = navigationView.getHeaderView(0)
        val navUsername = headerView.findViewById(R.id.dashboard_username) as TextView
        val navRole = headerView.findViewById(R.id.dashboard_role) as TextView
        navUsername.text = username
        navRole.text = role

        val toggle = ActionBarDrawerToggle(
            this, drawer_layout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close
        )
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        //set agenda as default fragment
        val fragmentTransaction = supportFragmentManager.beginTransaction()
        val fragment = AgendaListFragment()
        fragmentTransaction.replace(R.id.content_dashboard, fragment, "AGENDA_LIST_FRAGMENT")
        fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        fragmentTransaction.commit()

        if (role.equals("Asistent")) {
            floating_button.setOnClickListener {
                val addEventActivity: Intent = Intent(this, AddEventActivity::class.java)
                startActivityForResult(addEventActivity, Constants.RQUEST_CODE_ADD_EVENT)
            }
        } else {
            floating_button.visibility = View.INVISIBLE
        }

        nav_view.setNavigationItemSelectedListener(this)
        nav_view.menu.getItem(1).isChecked = true
    }

    fun sendUsername(): String{
        return username
    }
    fun sendRole(): String{
        return role
    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        val fragment = supportFragmentManager.fragments[supportFragmentManager.fragments.size - 1]
        when (fragment) {
            is PacientListFragment -> fragment.onActivityResult(requestCode, resultCode, data)
            is AgendaListFragment -> fragment.onActivityResult(requestCode, resultCode, data)
            is DrugListFragment -> fragment.onActivityResult(requestCode, resultCode, data)
            is RequestListFragment -> fragment.onActivityResult(requestCode, resultCode, data)
        }
    }

    @SuppressLint("RestrictedApi")
    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.nav_pacienti -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = PacientListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment, "PACIENT_LIST_FRAGMENT")
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()

                val role = getIntent().getStringExtra("EXTRA_ROLE")
                if (role.equals("Asistent")) {
                    floating_button.setOnClickListener {
                        val addPacientActivity: Intent = Intent(this, AddPacientActivity::class.java)
                        startActivityForResult(addPacientActivity, Constants.RQUEST_CODE_ADD_PACIENT)
                    }
                } else {
                    floating_button.visibility = View.INVISIBLE
                }
            }
            R.id.nav_agenda -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = AgendaListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment, "AGENDA_LIST_FRAGMENT")
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()

                val role = getIntent().getStringExtra("EXTRA_ROLE")
                if (role.equals("Asistent")) {
                    floating_button.setOnClickListener {
                        val addEventActivity: Intent = Intent(this, AddEventActivity::class.java)
                        startActivityForResult(addEventActivity, Constants.RQUEST_CODE_ADD_EVENT)
                    }
                } else {
                    floating_button.visibility = View.INVISIBLE
                }
            }
            R.id.nav_medicamente -> {
                val fragmentTransaction = supportFragmentManager.beginTransaction()
                val fragment = DrugListFragment()
                fragmentTransaction.replace(R.id.content_dashboard, fragment, "DRUGS_LIST_FRAGMENT")
                fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                fragmentTransaction.commit()
                val role = getIntent().getStringExtra("EXTRA_ROLE")
                if (role.equals("Farmacist")) {
                    floating_button.visibility = View.VISIBLE
                    floating_button.setOnClickListener {
                        val addDrugActivity: Intent = Intent(this, AddDrugActivity::class.java)
                        startActivityForResult(addDrugActivity, Constants.RQUEST_CODE_ADD_DRUG)
                    }
                } else {
                    floating_button.visibility = View.INVISIBLE
                }
            }
            R.id.nav_cereri -> {
                val role = getIntent().getStringExtra("EXTRA_ROLE")
                if (role.equals("Farmacist")) {
                    val fragmentTransaction = supportFragmentManager.beginTransaction()
                    val fragment = RequestListFragment()
                    fragmentTransaction.replace(R.id.content_dashboard, fragment, "REQUESTS_LIST_FRAGMENT")
                    fragmentTransaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
                    fragmentTransaction.commit()
                } else {
                    var addRequestIntent = Intent(this, AddRequestActivity::class.java)
                    startActivity(addRequestIntent)
                }

                floating_button.visibility = View.INVISIBLE
            }

            R.id.nav_stiri -> {
                var newsIntent = Intent(this, NewsActivity::class.java)
                startActivity(newsIntent)
            }
            R.id.nav_setari -> {
                var settingsIntent = Intent(this, SettingsActivity::class.java)
                settingsIntent.putExtra("EXTRA_DOCTOR_USERNAME", username)
                startActivity(settingsIntent)
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }
}
