package com.miruna.hospitalmanager.application.pacient.file

import android.content.Intent
import android.os.AsyncTask
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_edit_file_content.*

class EditFileContentActivity : AppCompatActivity() {
    lateinit var currentFile: File

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_file_content)

        val editFileContentIntent: Intent = getIntent()

        val extraFileContent = editFileContentIntent.getStringExtra("EXTRA_CONTENT_MODIFIED")
        val extraFileId = editFileContentIntent.getStringExtra("EXTRA_ID_MODIFIED")
        val extraPacientId = editFileContentIntent.getStringExtra("EXTRA_PACIENT_ID_MODIFIED")

        currentFile = File(extraFileId, extraFileContent, extraPacientId)
        et_file_content.setText(extraFileContent)

        btn_save_updates.setOnClickListener {
            val fileDetailsIntent = Intent(this, FileDetailsActivity()::class.java)
            currentFile.content = et_file_content.text.toString()

            updateFileTask().execute()
            startActivity(fileDetailsIntent)
            finish()
        }
    }

    private inner class updateFileTask : AsyncTask<Void, Void, Boolean>() {
        override fun doInBackground(vararg params: Void?): Boolean {
            try {
                return FileService().updateFile(currentFile)
            } catch (e: Exception) {
                e.printStackTrace()
            }

            return false
        }

        override fun onPostExecute(result: Boolean?) {

        }

    }
}
