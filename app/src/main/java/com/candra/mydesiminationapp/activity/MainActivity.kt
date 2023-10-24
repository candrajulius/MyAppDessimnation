package com.candra.mydesiminationapp.activity

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.util.Log
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.databinding.ActivityMainBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.TempDataTwo
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class
MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    @Inject
    lateinit var localStore: TempDataTwo
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()
        toTemptActivity()
    }

    private fun toTemptActivity(){
        Handler(mainLooper).postDelayed({
            lifecycleScope.launch {
                localStore.getToken().collect{ dataToken ->
                    jumpToActivityAnother(dataToken.token,dataToken.category,dataToken.statusId,dataToken.tokenFcm)
                }
            }
        },Constant.TIME_DELAYED)
    }

    private fun jumpToActivityAnother(token: String,category: String,statusId: Int,tokenFcm: String){
        val intent
        = if (token.isBlank() && category.isBlank() && statusId == 5 && tokenFcm.isBlank()){
            Log.d("TAG", "jumpToActivityAnother: token => $token category => $category")
            Intent(this@MainActivity,TempActivity::class.java)
        } else{
            if(category.contains(Constant.LECTURER,true)){
                Log.d("TAG", "jumpToActivityAnotherLecturer: token => $token category => $category")
                Intent(this@MainActivity,LecturerActivity::class.java)
            }else if(category.contains(Constant.ADMINISTRATOR,true)){
                Log.d("TAG", "jumpToActivityAnotherAdministrator: token => $token category => $category")
                Intent(this@MainActivity,IndexStudentActivity::class.java)
            }else{
                Log.d("TAG", "jumpToActivityAnotherStudent: token => $token category => $category")
                Intent(this@MainActivity,IndexStudentActivity::class.java)
//                if(statusId == 0 || statusId == 2 || statusId == 3){
//                    Intent(this@MainActivity,AcceptMemberActivity::class.java)
//                }else{
//                    Intent(this@MainActivity,IndexStudentActivity::class.java)
//                }
            }
        }
        startActivity(intent).also { finish() }
    }
}