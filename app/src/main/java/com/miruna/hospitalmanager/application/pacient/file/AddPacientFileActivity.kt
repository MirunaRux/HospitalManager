package com.miruna.hospitalmanager.application.pacient.file

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.miruna.hospitalmanager.R
import com.miruna.hospitalmanager.application.pacient.PacientDetailsActivity
import kotlinx.android.synthetic.main.activity_add_pacient_file.*

class AddPacientFileActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_pacient_file)

        btn_submit_file.setOnClickListener {
            if(isInputValid()){
                val pacientDetailsIntent = Intent(this, PacientDetailsActivity::class.java)
                pacientDetailsIntent.putExtra("EXTRA_FILE_ID", et_addFile_id.text.toString())
                pacientDetailsIntent.putExtra("EXTRA_FILE_CONTENT", et_addFile_content.text.toString())
                startActivity(pacientDetailsIntent)
            }
        }
    }

    fun isInputValid(): Boolean {

        if (et_addFile_id.text.isNullOrEmpty()) {
            til_addFile_id.setError("Field required")
            et_addFile_id.requestFocus()
            return false
        }
        if (et_addFile_content.text.isNullOrEmpty()) {
            til_addFile_content.setError("Field required")
            et_addFile_content.requestFocus()
            return false
        }

        return true
    }
}
