package com.miruna.hospitalmanager.application.pacient.file

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_edit_file_content.*

class EditFileContentActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_file_content)

        val editFileContentIntent : Intent = getIntent()

        val extraFileContent = editFileContentIntent.getStringExtra("EXTRA_CONTENT_MODIFIED")
        val extraFileId = editFileContentIntent.getStringExtra("EXTRA_ID_MODIFIED")

        et_file_content.setText(extraFileContent)

        btn_save_updates.setOnClickListener {
            val fileDetailsIntent : Intent = Intent(this, FileDetailsActivity()::class.java)
            fileDetailsIntent.putExtra("EXTRA_UPDATED_FILE_CONTENT", et_file_content.text.toString())
            fileDetailsIntent.putExtra("EXTRA_UPDATED_FILE_ID", extraFileId)
            startActivity(fileDetailsIntent)
            finish()
        }
    }
}
