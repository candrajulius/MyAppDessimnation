@file:Suppress("DEPRECATION")

package com.candra.mydesiminationapp.activity

import android.app.Dialog
import android.content.Intent
import android.content.res.Configuration
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.adapter.AdapterMember
import com.candra.mydesiminationapp.adapter.ImageAdapter
import com.candra.mydesiminationapp.adapter.ParticipantsAdapter
import com.candra.mydesiminationapp.data.source.local.LocalIndexLecturerItem
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.DialogParticipantsBinding
import com.candra.mydesiminationapp.databinding.DialogShowTwoDisseminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.LoginViewModel
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.ResearchDisseminationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class DetailDisseminationTwo: AppCompatActivity(){
    private lateinit var detailDisseminationTwoBinding: DialogShowTwoDisseminationBinding
    @Inject
    lateinit var localStore: TempDataTwo
    private val adapterImage by lazy { ImageAdapter() }
    private val adapterParticipants by lazy { ParticipantsAdapter() }
    private val adapterMember by lazy { AdapterMember() }
    private val detailTwoDisseminationViewModel by viewModels<ResearchDisseminationViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        detailDisseminationTwoBinding = DialogShowTwoDisseminationBinding.inflate(layoutInflater)
        setContentView(detailDisseminationTwoBinding.root)
        Help.setToolbar(1,supportActionBar,"Disseminasi Penelitian")
        recyclerviewMember()
        lifecycleScope.launch {
            localStore.getToken().collect{ tokenLecturer ->
                if (tokenLecturer.category == Constant.LECTURER){
                    intent.getParcelableExtra<LocalIndexLecturerItem>(Constant.STUDENT_ID)?.let { data ->
                        detailDisseminationTwoBinding.btnGetImage.setOnClickListener {
                            showDialogImageList(data.id,tokenLecturer.token?:"")
                        }

                        detailDisseminationTwoBinding.btnNote.setOnClickListener {
                            Intent(this@DetailDisseminationTwo,LogActivity::class.java).apply {
                                putExtra(Constant.STUDENT_ID,data.id)
                            }.also { startActivity(it) }
                        }

                        detailDisseminationTwoBinding.btnChangeStatusNew.setOnClickListener {
                            detailTwoDisseminationViewModel.backStatusToNew(data.id,tokenLecturer.token?:"").observe(this@DetailDisseminationTwo) { datas ->
                                when (datas) {
                                    is States.Loading -> {}
                                    is States.Success -> {
                                        Help.showToast(this@DetailDisseminationTwo, datas.data)
                                    }

                                    is States.Failed -> {
                                        datas.message
                                    }

                                    else -> {}
                                }
                            }
                        }

                        detailDisseminationTwoBinding.btnBack.setOnClickListener {
                            startActivity(Intent(this@DetailDisseminationTwo,LecturerActivity::class.java)).also {
                                finish()
                            }
                        }

                        detailDisseminationTwoBinding.btnParticipants.setOnClickListener {
                            showDialogParticipantsList(data.id,tokenLecturer.token?: "")
                        }
                        detailTwoDisseminationViewModel.showLecturerDissemination(data.id,tokenLecturer.token?: "").observe(this@DetailDisseminationTwo){
                            with(detailDisseminationTwoBinding){
                                when(it){
                                    is States.Loading -> {}
                                    is States.Success -> {
                                        valueIdDesimination.text = "#${it.data.id}"
                                        valueStatusDesimination.text = it.data.statusCaption
                                        valueTittleDesimination.text = it.data.title
                                        valueAbstract.text = Help.removeHtmlTags(it.data.abstract)
                                        valueKeyword.text = it.data.keywords
                                        valueLecturer.text = it.data.lecturer.name
                                        valueNameJournal.text = it.data.journal.publisherName
                                        valueYearJournal.text = it.data.journal.journalYear.toString()
                                        valueCategoryJournal.text = it.data.journal.journalGradeCaption
                                        valueNumberIssn.text = it.data.journal.publisherNumber
                                        valuePagePublisher.text = it.data.journal.publisherUrl
                                        valuePageArticle.text = it.data.journal.articleUrl
                                        valueVolumeJournal.text = it.data.journal.journalVolume.toString()
                                        valueNumberJournal.text = it.data.journal.journalNumber.toString()
                                        valueResearchDesimination.text = it.data.document.filename
                                        valueMaterialPresentation.text = it.data.presentation.filename
                                        valueFreeAdmin.text = it.data.administration.filename
                                        valueArticle.text = it.data.article.filename
                                        valueRoomDissemination.text = "${it.data.schedule.classRoomId}"
                                        valueStartDissemination.text = it.data.schedule.startTime
                                        valueEndDissemination.text = it.data.schedule.endTime
                                        valueQuotaParticipants.text = it.data.schedule.quota
                                        Help.visibleDeterminationDecision(status = data.statusCaption,
                                        detailDisseminationTwoBinding.determinationDecision,detailDisseminationTwoBinding.valueDeterminationDecision)
                                        val dataId = valueIdDesimination.text.toString()
                                        val textId = dataId.substring(1)
                                        val numberId = textId.toInt()
                                        detailTwoDisseminationViewModel.getMember(numberId,tokenLecturer.token?: "").observe(this@DetailDisseminationTwo){ dataMember ->
                                            when(dataMember){
                                                is States.Loading -> {}
                                                is States.Success -> {
                                                    adapterMember.submitListData(dataMember.data)
                                                }
                                                is States.Failed -> {Help.showToast(this@DetailDisseminationTwo,dataMember.message.toString())}
                                                else -> {}
                                            }
                                        }
                                    }
                                    else -> {}
                                }
                            }
                        }
                    }
                }
            }
        }
    }

    private fun recyclerviewMember(){
        detailDisseminationTwoBinding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@DetailDisseminationTwo,2)
            else LinearLayoutManager(this@DetailDisseminationTwo)
            setHasFixedSize(true)
            adapter = adapterMember
        }
    }

    private fun showDialogImageList(id: Int,token: String){
        val dialog = Dialog(this@DetailDisseminationTwo)
        val dialogBinding = com.candra.mydesiminationapp.databinding.DialogImageBinding.inflate(layoutInflater)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
        }
        with(dialogBinding){
            setRecyclerViewImage(rvDialogImage)
            btnFloatingActionBack.setOnClickListener {
                dialog.dismiss()
            }
            detailTwoDisseminationViewModel.getImage(id,token).observe(this@DetailDisseminationTwo){
                when(it){
                    is States.Loading -> Help.showProgressBar(true,rvDialogImage,progressBar)
                    is States.Success -> {
                        Help.showProgressBar(false,rvDialogImage,progressBar)
                        adapterImage.submitListData(it.data)
                    }
                    is States.Failed -> {
                        Help.showToast(this@DetailDisseminationTwo,it.message)
                    }
                    else -> {}
                }
            }
        }
        dialog.apply {
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }

    private fun setRecyclerViewImage(list: RecyclerView){
        list.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@DetailDisseminationTwo,2)
            else LinearLayoutManager(this@DetailDisseminationTwo)
            setHasFixedSize(true)
            adapter = adapterImage
        }
    }

    private fun setRecyclerViewParticipants(list: RecyclerView){
        list.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@DetailDisseminationTwo,2)
            else LinearLayoutManager(this@DetailDisseminationTwo)
            setHasFixedSize(true)
            adapter = adapterParticipants
        }
    }

    private fun showDialogParticipantsList(id: Int,token: String)
    {
        val dialog = Dialog(this@DetailDisseminationTwo)
        val dialogBinding = DialogParticipantsBinding.inflate(layoutInflater)
        dialog.apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setContentView(dialogBinding.root)
        }
        with(dialogBinding){
            setRecyclerViewParticipants(rvDialogParticipants)
            btnFloatingActionBack.setOnClickListener {
                dialog.dismiss()
            }
            detailTwoDisseminationViewModel.getParticipants(id,token).observe(this@DetailDisseminationTwo){
                when(it){
                    is States.Loading -> Help.showProgressBar(true,rvDialogParticipants,progressBar)
                    is States.Success -> {
                        if (it.data.isEmpty()) showEmptyText(true,dialogBinding)
                        Help.showProgressBar(false,rvDialogParticipants,progressBar)
                        adapterParticipants.submitListData(it.data)
                    }
                    is States.Failed -> Help.showToast(this@DetailDisseminationTwo,it.message)
                    else -> {}
                }
            }
        }
        dialog.apply {
            show()
            window?.apply {
                setLayout(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.MATCH_PARENT)
                setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
                attributes.windowAnimations = R.style.DialogAnimation
                setGravity(Gravity.BOTTOM)
            }
        }
    }
    private fun showEmptyText(isShow:Boolean,binding: DialogParticipantsBinding){
        with(binding){
            mtvEmptyText.visibility = if (isShow) View.VISIBLE else View.GONE
            progressBar.visibility = if (isShow) View.GONE else View.GONE
            rvDialogParticipants.visibility = if (isShow) View.GONE else View.GONE
        }
    }
}