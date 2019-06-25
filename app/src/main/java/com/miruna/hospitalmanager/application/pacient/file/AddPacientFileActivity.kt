package com.miruna.hospitalmanager.application.pacient.file

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.pacient.PacientDetailsActivity
import kotlinx.android.synthetic.main.activity_add_pacient_file.*
import kotlinx.android.synthetic.main.activity_pacient_details.*
import java.util.ArrayList

class AddPacientFileActivity : AppCompatActivity() {
    lateinit var newFile: File
    lateinit var mPrefs: SharedPreferences
    var fileList: MutableList<File>? = null
    lateinit var idPacient: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacient_file)

        getAllPacientFilesTask().execute()

        mPrefs = getPreferences(Context.MODE_PRIVATE)

        btn_submit_file.setOnClickListener {
            if (isInputValid()) {
                var lastId = fileList?.get(fileList!!.size-1)?.id?.substring(1)
                val fileId = "F" + ((lastId?.toInt() ?: 0) + 1).toString()

                val addPacientFileIntent = getIntent()
                idPacient = addPacientFileIntent.getStringExtra("EXTRA_PACIENT_ID_FROM_DETAILS") ?: "urat"
                Log.i("extra-ul de la add file", idPacient)
                //idPacient = mPrefs.getString("idPacient", "")

                newFile = File(fileId, et_addFile_content.text.toString(), idPacient)
                addPacientFileTask().execute()
                finish()
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_addFile_content.text.isNullOrEmpty()) {
            til_addFile_content.setError("Field required")
            et_addFile_content.requestFocus()
            return false
        }

        return true
    }

    private inner class addPacientFileTask : AsyncTask<Void, Void, File>() {
        override fun doInBackground(vararg params: Void): File? {
            try {
                return FileService().createFile(newFile)
            } catch (e: Exception) {
                e.printStackTrace()
                Log.i("check", "check")
            }
            return null
        }

        /*override fun onPostExecute(pacient: File?) {
            // getAllPacientFilesTask().execute()
        }*/
    }

    private inner class getAllPacientFilesTask : AsyncTask<Void, Void, List<File>>() {
        override fun doInBackground(vararg params: Void): List<File>? {
            try {
                return FileService().findAllFiles()
            } catch (e: Exception) {
                e.printStackTrace()
            }
            return null
        }

        override fun onPostExecute(files: List<File>?) {
            fileList = files as MutableList<File>?
        }
    }

}
