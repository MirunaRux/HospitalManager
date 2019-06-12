package com.miruna.hospitalmanager.application.pacient

import android.app.DatePickerDialog
import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.LinearLayoutManager
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.pacient.file.*
import com.miruna.hospitalmanager.application.utils.Constants
import com.miruna.hospitalmanager.application.utils.SharedPreferenceManager
import kotlinx.android.synthetic.main.activity_pacient_details.*
import java.text.SimpleDateFormat
import java.util.*

class PacientDetailsActivity : AppCompatActivity() {
    var fileList: MutableList<File>? = null
    var filesAdapter: FilesAdapter? = null

    var extraId: String = ""

    lateinit var currentPacient: Pacient

    fun Date.formatToStringByPattern(pattern: String): String {
        val df = SimpleDateFormat(pattern)
        return df.format(this)
    }

    private fun getDatePickerListener_et_pacient_date_in() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.time?.let {
                et_pacient_date_in.setText(it.formatToStringByPattern(Constants.DATE_FORMAT_MDY))
            }
        }
    private fun getDatePickerListener_et_pacient_date_ex() =
        DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->

            val calendar = Calendar.getInstance()
            calendar.set(year, month, dayOfMonth)
            calendar.time?.let {
                et_pacient_date_ex.setText(it.formatToStringByPattern(Constants.DATE_FORMAT_MDY))
            }
        }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_pacient_details)

        val pacientDetailsIntent: Intent = getIntent()
        extraId = pacientDetailsIntent.getStringExtra("EXTRA_ID")
        val extraName = pacientDetailsIntent.getStringExtra("EXTRA_NAME")
        val extraSurname = pacientDetailsIntent.getStringExtra("EXTRA_SURNAME")
        val extraBirthday = pacientDetailsIntent.getStringExtra("EXTRA_BIRTHDAY")
        val extraCNP = pacientDetailsIntent.getStringExtra("EXTRA_CNP")
        val extraDateIn = pacientDetailsIntent.getStringExtra("EXTRA_DATE_IN")
        val extraDateEx = pacientDetailsIntent.getStringExtra("EXTRA_DATE_EX")

        currentPacient = Pacient(extraId, extraName, extraSurname, extraBirthday, extraCNP, extraDateIn, extraDateEx)

        val extraFileId = pacientDetailsIntent.getStringExtra("EXTRA_FILE_ID")
        val extraFileContent = pacientDetailsIntent.getStringExtra("EXTRA_FILE_CONTENT")
        var calendar: Calendar

        pacient_name.setText("Name : " + extraName)
        pacient_surname.setText("Surname : " + extraSurname)
        pacient_age.setText("Birthday : " + extraBirthday)
        pacient_cnp.setText("CNP : " + extraCNP)
        et_pacient_date_in.setText(extraDateIn)
        et_pacient_date_ex.setText(extraDateEx)

        et_pacient_date_in.setOnClickListener {
            calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener_et_pacient_date_in(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        et_pacient_date_ex.setOnClickListener {
            calendar = Calendar.getInstance()
            val datePickerDialog = DatePickerDialog(
                this,
                getDatePickerListener_et_pacient_date_ex(),
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH)
            )
            datePickerDialog.show()
        }

        btn_submit_pacient.setOnClickListener {
            currentPacient.dateIn = et_pacient_date_in.text.toString()
            currentPacient.dateEx = et_pacient_date_ex.text.toString()
            updatePacientTask().execute()
        }

        getAllPacientFilesTask().execute()

        floating_button_addFile.setOnClickListener {
            val addPacientFileIntent = Intent(this, AddPacientFileActivity::class.java)
            startActivity(addPacientFileIntent)
        }

    }

    private inner class updatePacientTask : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void): Boolean {
            try {
                Log.i("gigel", currentPacient.dateEx)
                return PacientService().updatePacient(currentPacient)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("check", "check")
            }
            return false
        }

        override fun onPostExecute(pacient: Boolean?) {

        }
    }

    private inner class getAllPacientFilesTask : AsyncTask<Void, Void, List<File>>() {
        override fun doInBackground(vararg params: Void): List<File>? {
            try {
                var filteredFiles = FileService().findAllFiles()
                var auxFilteredFiles: ArrayList<File> = arrayListOf()
                for (file: File in filteredFiles) {
                    if (file.pacient_id.equals(extraId)) {
                        auxFilteredFiles.add(file)
                    }
                }
                return auxFilteredFiles
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(files: List<File>?) {
            var fileList = files as MutableList<File>?

            recyclerViewPacientFileList.apply {
                layoutManager = LinearLayoutManager(context)
                val filesAdapter = FilesAdapter(fileList!!)
                filesAdapter?.onItemClick = {
                    val pacientFileDetailsIntent = Intent(context, FileDetailsActivity::class.java)
                    SharedPreferenceManager.saveFileId(context, it.id)
                    pacientFileDetailsIntent.putExtra("EXTRA_FILE_ID", it.id)
                    pacientFileDetailsIntent.putExtra("EXTRA_CONTENT", it.content)
                    pacientFileDetailsIntent.putExtra("EXTRA_PACIENT_ID", it.pacient_id)
                    startActivity(pacientFileDetailsIntent)
                }
                this.adapter = filesAdapter
            }
        }

    }
}
