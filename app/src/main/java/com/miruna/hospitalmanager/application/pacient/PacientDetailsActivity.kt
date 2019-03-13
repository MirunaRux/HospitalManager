package com.miruna.hospitalmanager.application.pacient

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.pacient.file.AddPacientFileActivity
import com.miruna.hospitalmanager.application.pacient.file.File
import com.miruna.hospitalmanager.application.pacient.file.FileDetailsActivity
import com.miruna.hospitalmanager.application.pacient.file.FilesAdapter
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_pacient_details.*

class PacientDetailsActivity : AppCompatActivity() {
    var files = mutableListOf<File>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacient_details)

        val pacientDetailsIntent : Intent = getIntent()
        val extraName = pacientDetailsIntent.getStringExtra("EXTRA_NAME")
        val extraSurname = pacientDetailsIntent.getStringExtra("EXTRA_SURNAME")
        val extraAge = pacientDetailsIntent.getStringExtra("EXTRA_AGE")
        val extraCNP = pacientDetailsIntent.getStringExtra("EXTRA_CNP")
        val extraDateIn = pacientDetailsIntent.getStringExtra("EXTRA_DATE_IN")
        val extraDateEx = pacientDetailsIntent.getStringExtra("EXTRA_DATE_EX")

        val extraFileId = pacientDetailsIntent.getStringExtra("EXTRA_FILE_ID")
        val extraFileContent= pacientDetailsIntent.getStringExtra("EXTRA_FILE_CONTENT")

        pacient_name.setText("Name : " + extraName)
        pacient_surname.setText("Surname : " + extraSurname)
        pacient_age.setText("Age : " + extraAge)
        pacient_cnp.setText("CNP : " + extraCNP)
        pacient_date_in.setText("Check-in date : " + extraDateIn)
        pacient_date_ex.setText("Check-out date : " + extraDateEx)

        for (i in 1..9){
            files.add(File(i.toString(), "Fisa pacient "+ i.toString()))
        }

        if(extraFileId != null && extraFileContent != null){
            files.add(File(extraFileId, extraFileContent))
        }

        recyclerViewPacientFileList.apply {
            layoutManager = LinearLayoutManager(context)
            val adapter = FilesAdapter(files)
            adapter.onItemClick = {
                val pacientFileDetailsIntent = Intent(context, FileDetailsActivity::class.java)
                SharedPreferenceManager.saveFileId(context, it.id)
                pacientFileDetailsIntent.putExtra("EXTRA_ID", it.id)
                pacientFileDetailsIntent.putExtra("EXTRA_CONTENT", it.content)
                startActivity(pacientFileDetailsIntent)
            }
            this.adapter = adapter
        }

        floating_button_addFile.setOnClickListener {
            val addPacientFileIntent = Intent(this, AddPacientFileActivity::class.java)
            startActivity(addPacientFileIntent)
        }

    }
}
