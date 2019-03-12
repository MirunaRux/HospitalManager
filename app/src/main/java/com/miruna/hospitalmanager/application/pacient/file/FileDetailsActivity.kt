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
        val extraId = pacientFileDetailsIntent.getStringExtra("EXTRA_ID")
        val extraContent = pacientFileDetailsIntent.getStringExtra("EXTRA_CONTENT")
        file_details_id.setText("File : " + extraId)
        file_details_content.setText(extraContent)
    }
}
