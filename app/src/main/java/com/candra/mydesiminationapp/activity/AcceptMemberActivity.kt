package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import coil.load
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityAcceptDisseminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class AcceptMemberActivity : AppCompatActivity() {
    @Inject
    lateinit var localStore: TempDataTwo
    private val researchDisseminationViewModelStudent by viewModels<ResearchDisseminationViewModelStudent>()
    private lateinit var binding: ActivityAcceptDisseminationBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAcceptDisseminationBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.imageLogo.load(Constant.URL_IMAGE_SISKA_DISSEMINATION) {
            error(R.drawable.baseline_broken_image_24)
        }
        Help.setToolbar(1,supportActionBar,"Penugasan Sebagai Anggota Peneliti")
        lifecycleScope.launch {
            localStore.getToken().collect { data ->
                if (data.category == Constant.STUDENT && data.statusId == 0) {
                    showAll(true)
                    setAlLData(data.token,data.code)
                }else if (data.statusId == 1){
                    startActivity(Intent(this@AcceptMemberActivity,IndexStudentActivity::class.java))
                        .also { finish() }
                }else{
                    showAll(false)
                }
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_lecturer){
            lifecycleScope.launch {
                localStore.putToken(GetTokenLecturer("","","",5,""))
                    .also { startActivity(Intent(this@AcceptMemberActivity,TempActivity::class.java))
                        .also { finish() }}
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun setAlLData(token: String,code: String) {
        with(binding) {
            researchDisseminationViewModelStudent.indexStudent(token)
                .observe(this@AcceptMemberActivity) { dataObserver ->
                    when (dataObserver) {
                        is States.Loading -> {}
                        is States.Success -> {
                            val data = dataObserver.data.members
                            for (member in data) {
                                    if (code.contains(member.student.code,true)) {
                                        btnAccept.setOnClickListener {
                                            setAcceptMemberDissemination(
                                                member.id,
                                                token
                                            )
                                        }
                                        btnReject.setOnClickListener {
                                            setRejectMember(token, member.id)
                                        }
                                    }
                                if (member.asX == Constant.LEADER_DISSEMINATAION) {
                                    valueStatusDesimination.text = member.student.name
                                    }
                                }
                            valueIdDesimination.text =
                                dataObserver.data.statusCaption ?: ""
                            valueTitleDesimination.text = dataObserver.data.title ?: "?"
                            valueLeaderDesimination.text =
                                dataObserver.data.lecturer.name ?: ""
                        }

                        is States.Failed -> Help.showToast(
                            this@AcceptMemberActivity,
                            dataObserver.message
                        )

                        else -> {}
                    }
                }
        }
    }

    private fun setAcceptMemberDissemination(id: Int, token: String) {
        researchDisseminationViewModelStudent.acceptMemberToDissemination(token, id)
            .observe(this@AcceptMemberActivity) {
                when (it) {
                    is States.Loading -> showProgressBarForBtnRejectAndBtnAccept(true)
                    is States.Success -> {
                        showProgressBarForBtnRejectAndBtnAccept(false)
                        Help.showToast(this@AcceptMemberActivity, it.data)
                        startActivity(
                            Intent(
                                this@AcceptMemberActivity,
                                IndexStudentActivity::class.java
                            )
                        )
                            .also { finish() }
                    }

                    is States.Failed ->{
                        Help.showToast(this@AcceptMemberActivity, it.message)
                        showProgressBarForBtnRejectAndBtnAccept(false)
                    }
                    else -> {}
                }
            }
    }

    private fun setRejectMember(token: String, id: Int) {
        researchDisseminationViewModelStudent.rejectMemberDissemination(token, id)
            .observe(this@AcceptMemberActivity) {
                when (it) {
                    is States.Loading -> {showProgressBarForBtnRejectAndBtnAccept(true)}
                    is States.Success -> {
                        binding.progressBar.visibility = View.GONE
                        Help.showToast(this@AcceptMemberActivity, it.data)
                        showAll(false)
                    }
                    is States.Failed -> {
                        binding.progressBar.visibility = View.GONE
                        showAll(false)
                        Help.showToast(this@AcceptMemberActivity, it.message)
                    }

                    else -> {}
                }
            }
    }

    private fun showProgressBarForBtnRejectAndBtnAccept(isShow: Boolean){
        binding.apply {
            progressBar.visibility = if (isShow) View.VISIBLE else View.GONE
            btnAccept.visibility = if(isShow) View.GONE else View.VISIBLE
            btnReject.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun showAll(isShow: Boolean) {
        with(binding) {
            titleSubmissionAsResearchMember.visibility = if (isShow) View.VISIBLE else View.GONE
            titleAcceptMember.visibility = if (isShow) View.VISIBLE else View.GONE
            container.visibility = if (isShow) View.VISIBLE else View.GONE
        }
    }

}