package com.miruna.hospitalmanager.application.pacient.file

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import kotlinx.android.synthetic.main.activity_file_details.*


class FileDetailsActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_file_details)
        val pacientFileDetailsIntent : Intent = getIntent()
        val extraId = pacientFileDetailsIntent.getStringExtra("EXTRA_FILE_ID")
        val extraContent = pacientFileDetailsIntent.getStringExtra("EXTRA_CONTENT")
        val extraPacientId = pacientFileDetailsIntent.getStringExtra("EXTRA_PACIENT_ID")
        val editFileContentIntent : Intent = Intent(this, EditFileContentActivity()::class.java)

        file_details_content.setText(extraContent)
        file_details_id.setText("File : " + extraId)

        btn_edit_file.setOnClickListener {
            editFileContentIntent.putExtra("EXTRA_CONTENT_MODIFIED", file_details_content.text.toString())
            editFileContentIntent.putExtra("EXTRA_ID_MODIFIED", extraId)
            editFileContentIntent.putExtra("EXTRA_PACIENT_ID_MODIFIED", extraPacientId)
            startActivity(editFileContentIntent)
            finish()
        }


    }

}
