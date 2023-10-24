package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.activity.viewModels
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.AddDialogLeaderDesiminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.ResearchDisseminationViewModel
import com.google.android.material.textfield.TextInputEditText
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AddLeaderDisseminationActivity: AppCompatActivity(){
    private lateinit var addLeaderDisseminationBinding : AddDialogLeaderDesiminationBinding
    @Inject
    lateinit var localStore: TempDataTwo
    private val addLeaderViewModel by viewModels<ResearchDisseminationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        addLeaderDisseminationBinding = AddDialogLeaderDesiminationBinding.inflate(layoutInflater)
        setContentView(addLeaderDisseminationBinding.root)
        lifecycleScope.launch {
            localStore.getToken().collect{ tokenLecturer ->
                if (tokenLecturer.category == Constant.LECTURER){
                    with(addLeaderDisseminationBinding){
                        btnBack.setOnClickListener {
                            toLecturerActivity()
                        }

                        btnClearText.setOnClickListener {
                            tilLeaderDesimination.editText?.text?.clear()
                        }
                        val dataString = intent.getStringExtra(Constant.EXTRA_STUDENT_ID)
                        edtLeaderDesimination.setText(dataString)

                        Help.setToolbar(1,supportActionBar,"Tambah Desiminasi Penelitian")

                        btnFindStudent.setOnClickListener {
                            val leaderDissemination = tilLeaderDesimination.editText?.text.toString()
                            if (leaderDissemination.isEmpty()) Help.showToast(
                                this@AddLeaderDisseminationActivity,
                                "Maaf inputan anda masih kosong"
                            )
                            else showDialogStudent(leaderDissemination,edtLeaderDesimination,tokenLecturer.token?: "")
                        }

                        btnSaveLeaderDesimination.setOnClickListener {
                            val dataLeader = tilLeaderDesimination.editText?.text.toString().trim()
                            val codeNumberLeader = dataLeader.split("#")[1]
                            val resultNumber = codeNumberLeader.toInt()
                            observerCreateDissemination(resultNumber,tokenLecturer.token?: "")
                            Log.d("TAG", "onCreate: $resultNumber")
                        }
                    }
                }
            }
        }
    }

    private fun observerCreateDissemination(id: Int,token: String){
        addLeaderViewModel.createDissemination(id,token).observe(this@AddLeaderDisseminationActivity){
            when(it){
                is States.Loading -> {}
                is States.Success -> {
                   toLecturerActivity()
                }
                is States.Failed -> Help.showToast(this@AddLeaderDisseminationActivity,it.message.toString())
                else -> {}
            }
        }
    }

    private fun toLecturerActivity(){
        startActivity(Intent(this@AddLeaderDisseminationActivity,LecturerActivity::class.java))
            .also { finish() }
    }

    private fun showDialogStudent(leaderDissemination: String,edtLeaderDissemination: TextInputEditText,token: String){
        addLeaderViewModel.queryStudent(leaderDissemination, token).observe(this@AddLeaderDisseminationActivity){
            when(it){
                is States.Loading -> {}
                is States.Success -> {
                    if (it.data.isEmpty()){
                        Help.showToast(this@AddLeaderDisseminationActivity,"Maaf, mahasiswa dengan kata kunci $leaderDissemination tidak dapat ditemukan")
                    }else{
                        showDialog(message = it.data,edtLeaderDissemination)
                    }
                }
                is States.Failed -> {
                    Help.showToast(this@AddLeaderDisseminationActivity,it.message)
                }
                else -> {}
            }
        }
    }

   private fun showDialog(message: String,edtLeaderDissemination: TextInputEditText){
       val cleanedData = message.replace("[","").replace("]","")
       val listArray = cleanedData.split(",").map { it.trim() }.toTypedArray()
       val checkedItems = BooleanArray(listArray.size)
        val builder = AlertDialog.Builder(this)
        builder.setTitle(getString(R.string.name_student))
            .setMultiChoiceItems(listArray,checkedItems){dialog,which,isChecked ->
                checkedItems[which] = isChecked
            }
            .setIcon(R.drawable.unpri)
            .setPositiveButton("Benar"){dialog,which ->
                for (i in listArray.indices){
                    if (checkedItems[i]){
                        val selectedItems = listArray[i]
                        val cleanedString = selectedItems.replace("[","").replace("]","")
                        edtLeaderDissemination.setText(cleanedString)
                        dialog.dismiss()
                    }
                }
            }

        builder.setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
            dialog.dismiss()
        }
        val alertDialog = builder.create()
        alertDialog.show()
    }
}