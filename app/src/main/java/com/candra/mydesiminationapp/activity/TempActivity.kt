package com.candra.mydesiminationapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.candra.mydesiminationapp.activity.admin.LoginActivityAdministrator
import com.candra.mydesiminationapp.databinding.ActivityTempBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TempActivity : AppCompatActivity() {
    private lateinit var tempBinding: ActivityTempBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        tempBinding = ActivityTempBinding.inflate(layoutInflater)
        setContentView(tempBinding.root)

        Help.setToolbar(1,supportActionBar,"Pilihan Login")
        tempBinding.btnLoginAsLecturer.setOnClickListener {
            toLecturerDissemination()
        }
        tempBinding.btnLoginAsStudent.setOnClickListener {
            toStudentDissemination()
        }
        tempBinding.btnLoginAsAdministrator.setOnClickListener {
            toAdministratorDissemination()
        }
    }


    private fun toLecturerDissemination(){
       startActivity(Intent(this@TempActivity,LoginActivity::class.java)).also { finish() }
    }

    private fun toAdministratorDissemination(){
        startActivity(Intent(this@TempActivity,LoginActivityAdministrator::class.java)).also { finish() }
    }

    private fun toStudentDissemination()
    {
       startActivity(Intent(this@TempActivity,LoginActivityStudent::class.java)).also { finish() }
    }
}