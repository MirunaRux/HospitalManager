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
        val editFileContentIntent : Intent = Intent(this, EditFileContentActivity()::class.java)

        btn_edit_file.setOnClickListener {
            editFileContentIntent.putExtra("EXTRA_CONTENT_MODIFIED", file_details_content.text.toString())
            editFileContentIntent.putExtra("EXTRA_ID_MODIFIED", file_details_id.text.toString())
            startActivity(editFileContentIntent)
            finish()
        }

        val extraUpdatedFileContent = editFileContentIntent.getStringExtra("EXTRA_UPDATED_FILE_CONTENT")
        val extraUpdatedFileId = editFileContentIntent.getStringExtra("EXTRA_UPDATED_FILE_ID")

        if(extraUpdatedFileContent!= null && extraUpdatedFileId!= null && !(extraUpdatedFileContent.equals(file_details_content.text.toString()))){
            file_details_content.setText(extraUpdatedFileContent)
            file_details_id.setText("File : " + extraUpdatedFileId)

        }
        else{
            file_details_content.setText(extraContent)
            file_details_id.setText("File : " + extraId)

        }
    }

}
