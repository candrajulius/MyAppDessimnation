package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import android.provider.OpenableColumns
import android.view.View
import androidx.activity.OnBackPressedCallback
import androidx.activity.result.contract.ActivityResultContracts
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.adapter.AdapterMember
import com.candra.mydesiminationapp.data.source.local.toGenerateMemberStudent
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActiivityEditDisseminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.student.ResearchDisseminationViewModelStudent
import com.karumi.dexter.Dexter
import com.karumi.dexter.PermissionToken
import com.karumi.dexter.listener.PermissionDeniedResponse
import com.karumi.dexter.listener.PermissionGrantedResponse
import com.karumi.dexter.listener.PermissionRequest
import com.karumi.dexter.listener.single.PermissionListener
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.MultipartBody
import okhttp3.RequestBody.Companion.asRequestBody
import okhttp3.RequestBody.Companion.toRequestBody
import java.io.File
import java.io.FileOutputStream
import javax.inject.Inject

@AndroidEntryPoint
class EditMemberActivity: AppCompatActivity()
{
    private lateinit var binding: ActiivityEditDisseminationBinding
    private val viewModelDisseminationStudent by viewModels<ResearchDisseminationViewModelStudent>()
    @Inject
    lateinit var localStore: TempDataTwo
    private val PICK_REQUEST_CODE_ARTICLE = 1
    private val PICK_REQUEST_CODE_PRESENTATION = 2
    private val PICK_REQUEST_CODE_ADMINISTRATION = 3
    private val adapterMember by lazy { AdapterMember() }
    private var statusType: Int = -1
    private var getFileAdministration: File? = null
    private var typeJournal: Int = -1
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActiivityEditDisseminationBinding.inflate(layoutInflater)
        Help.setToolbar(1,supportActionBar,"Koreksi Dessiminasi")
        setContentView(binding.root)
        binding.apply {
            btnBack.setOnClickListener {
                callBack.handleOnBackPressed()
            }
            btnChoiceFileArticle.setOnClickListener {
                checkPermissionStorage()
                nameFileArticle.text = getFileAdministration?.name
            }
            btnChoiceFilePresentation.setOnClickListener {
                checkPermissionStorage()
                namePresentation.text = getFileAdministration?.name
            }
            btnChoiceFreeAdministration.setOnClickListener {
                checkPermissionStorage()
                nameFreeAdministration.text = getFileAdministration?.name
            }
        }
        observerAllData()
        setRecyclerViewAdapterMember()
    }


    private fun observerAllData(){
        lifecycleScope.launch {
            localStore.getToken().collect{
                if (it.category == Constant.STUDENT) setAllData(token = it.token)
            }
        }
    }

    private fun setAllData(token: String){
        with(binding){
            viewModelDisseminationStudent.indexStudent(token).observe(this@EditMemberActivity){ student ->
                when(student){
                    is States.Loading -> {}
                    is States.Success -> {
                        val onSuccessData = student.data
                        edtTitleDissemination.setText(onSuccessData.title?: "?")
                        edtAbstract.setText(Help.removeHtmlTags(onSuccessData.abstract)?: "")
                        edtKeyword.setText(onSuccessData.keywords?: "?")
                        valueStatusDesimination.text = onSuccessData.statusCaption?: "?"
                        valueAbstract.text = Help.removeHtmlTags(onSuccessData.abstract)?: "?"
                        valueTittleDesimination.text = onSuccessData.title?: "?"
                        valueKeyword.text = onSuccessData.keywords?: "?"
                        valueLecturer.text = onSuccessData.lecturer.name?: "?"
                        valueIdDesimination.text = "#${onSuccessData.id}"
                        showVisibleCardPublisher(onSuccessData.journal.journalGradeCaption)
                        valueCategoryJournal.text = onSuccessData.journal.journalGradeCaption
                        valueNameJournal.text = onSuccessData.journal.publisherName
                        valueNumberIssn.text = onSuccessData.journal.publisherNumber
                        valuePagePublisher.text = onSuccessData.journal.publisherUrl
                        valuePageArticle.text = onSuccessData.journal.articleUrl
                        valueVolumeJournal.text = "${onSuccessData.journal.journalVolume}"
                        valueYearJournal.text = "${onSuccessData.journal.journalYear}"
                        val gradeJournal = intent.getStringExtra(Constant.EXTRA_GRADE_JOURNAL)
                        val journal = intent.getStringExtra(Constant.EXTRA_JOURNAL)
                        val typeJournal = "Jurnal"
                        if (typeJournal == journal) rbJournal.isChecked = true
                        else rbNotYetEmpty.isChecked = true
                        when(gradeJournal){
                            Constant.JOURNAL_INTERNATIONAL_REPUTATION -> rbJournalInternationalReputation.isChecked = true
                            Constant.JOURNAL_INTERNATIONAL -> rbJournalInternational.isChecked = true
                            Constant.JOURNAL_NATIONAL_REPUTATION -> rbJournalTerakditasi.isChecked = true
                            Constant.JOURNAL_NATIONAL -> rbJournalNational.isChecked = true
                            else -> rbEmpty.isChecked = true
                        }
                        adapterMember.submitListData(onSuccessData.members.toGenerateMemberStudent())
                        edtNameMember.setText(onSuccessData.journal.publisherName?: "")
                        edtNumberISSNISBN.setText(onSuccessData.journal.publisherNumber?: "")
                        edtYearJournal.setText(onSuccessData.journal.journalYear.toString() ?: "")
                        edtVolumeJournal.setText(onSuccessData.journal.journalVolume.toString() ?: "")
                        edtNumberJournal.setText(onSuccessData.journal.journalVolume.toString() ?: "")
                        edtPagePublisher.setText(onSuccessData.journal.publisherUrl?: "")
                        edtPageArticle.setText(onSuccessData.journal.articleUrl?: "")
                        nameFileArticle.text = onSuccessData.article.filename
                        nameFreeAdministration.text = onSuccessData.administration.filename
                        namePresentation.text = onSuccessData.presentation.filename
                        btnSave.setOnClickListener {
                            setAllDataEdit(token,onSuccessData.id)
                        }
                    }
                    is States.Failed -> {
                        Help.showToast(this@EditMemberActivity,student.message.toString())}
                    else -> {}
                }
            }
        }
    }

    private fun showVisibleCardPublisher(namePublisher: String){
        if (namePublisher.isEmpty()) isShowCardPublisher(false)
        else isShowCardPublisher(true)
    }

    private fun isShowCardPublisher(isShow: Boolean){
        binding.cardPulisher.visibility = if (isShow) View.VISIBLE else View.GONE
    }


    private fun setAllDataEdit(token: String,id: Int)
    {
        binding.apply {
            val titleDissemination = tilTitleDissemination.editText?.text.toString()
            val abstractDissemination = tilAbstract.editText?.text.toString()
            val keywordDissemination = tilKeyword.editText?.text.toString()
            val namePublisher = tilNamePublisher.editText?.text.toString()
            val tilNumberISSN = tilNumberISSNISBN.editText?.text.toString()
            val pagePublisher = tilPagePublisher.editText?.text.toString()
            val pageArticle = tilPageArticle.editText?.text.toString()
            val selectedGroupStatus = rgChoiseStatus.checkedRadioButtonId
            val selectedGroupStatusJournal = rgTypeJournal.checkedRadioButtonId
            val yearJournal = tilJournalYear.editText?.text.toString()
            val volumeJournal = tilVolumeJournal.editText?.text.toString()
            val numberJournal = tilNumberJournal.editText?.text.toString()
            if (titleDissemination.isEmpty() && abstractDissemination.isEmpty() && keywordDissemination.isEmpty() &&
                    namePublisher.isEmpty() && tilNumberISSN.isEmpty() && pagePublisher.isEmpty() && pageArticle.isEmpty())
            {
                Help.showToast(this@EditMemberActivity,"Inputan anda masih kosong,Silahkan isi terlebih dahulu")
            }else if (selectedGroupStatus == -1 ){
                Help.showToast(this@EditMemberActivity,"Harap memilih jenis publikasi")
            }else if(selectedGroupStatusJournal == -1){
                Help.showToast(this@EditMemberActivity,"Harap memilih kategori jurnal")
            }else{

                statusType = when(rgChoiseStatus.checkedRadioButtonId){
                    R.id.rb_journal -> 1
                    else -> 0
                }
                typeJournal = when(rgTypeJournal.checkedRadioButtonId){
                    R.id.rb_journal_international_reputation -> 1
                    R.id.rb_journal_international -> 2
                    R.id.rb_journal_terakditasi -> 3
                    R.id.rb_journal_national -> 4
                    else -> 0
                }

                val titleDisseminationRequestBody = titleDissemination.toRequestBody("text/plain".toMediaType())
                val abstractDisseminationRequestBody = abstractDissemination.toRequestBody("text/plain".toMediaType())
                val keywordDisseminationRequestBody = keywordDissemination.toRequestBody("text/plain".toMediaType())
                val statusTypeDisseminationRequestBody = statusType.toString().toRequestBody("text/plain".toMediaType())
                val typeJournalDisseminationRequestBody = typeJournal.toString().toRequestBody("text/plain".toMediaType())
                val namePublisherDisseminationRequestBody = namePublisher.toRequestBody("text/plain".toMediaType())
                val numberISSNDisseminationRequestBody = tilNumberISSN.toRequestBody("text/plain".toMediaType())
                val pagePublisherDisseminationRequestBody = pagePublisher.toRequestBody("text/plain".toMediaType())
                val pageArticleDisseminationRequestBody = pageArticle.toRequestBody("text/plain".toMediaType())
                val yearJournalDisseminationRequestBody = yearJournal.toRequestBody("text/plain".toMediaType())
                val volumeJournalDisseminationRequestBody = volumeJournal.toRequestBody("text/plain".toMediaType())
                val numberJournalDisseminationRequestBody = numberJournal.toRequestBody("text/plain".toMediaType())
                val pdfArticle = getFileAdministration!!.asRequestBody("application/pdf".toMediaTypeOrNull())
                val pdfArticleTempt: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "pdf_article",
                    getFileAdministration?.name,
                    pdfArticle
                )
                val pdfPresentation = getFileAdministration!!.asRequestBody("application/pdf".toMediaTypeOrNull())
                val pdfPresentationTempt: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "pdf_presentation",
                    getFileAdministration?.name,
                    pdfPresentation
                )

                val pdfAdministration = getFileAdministration!!.asRequestBody("application/pdf".toMediaTypeOrNull())
                val pdfAdministrationTempt: MultipartBody.Part = MultipartBody.Part.createFormData(
                    "pdf_administration",
                    getFileAdministration?.name,
                    pdfAdministration
                )

                viewModelDisseminationStudent.updateDissemination(token,id,
                    titleDisseminationRequestBody,abstractDisseminationRequestBody,
                    keywordDisseminationRequestBody,statusTypeDisseminationRequestBody
                ,namePublisherDisseminationRequestBody,
                    numberISSNDisseminationRequestBody,pagePublisherDisseminationRequestBody,
                    pageArticleDisseminationRequestBody,typeJournalDisseminationRequestBody,
                    yearJournalDisseminationRequestBody
                ,volumeJournalDisseminationRequestBody,numberJournalDisseminationRequestBody,pdfArticleTempt,pdfPresentationTempt,pdfAdministrationTempt)
            }
        }
    }

    private fun setRecyclerViewAdapterMember(){
        binding.rvTeamResearch.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@EditMemberActivity,2)
            else LinearLayoutManager(this@EditMemberActivity)
            setHasFixedSize(true)
            adapter = adapterMember
        }
    }

    private val callBack = object: OnBackPressedCallback(true){
        override fun handleOnBackPressed() {
            finish()
        }
    }

    private fun takeAFilePdfFromGallery(){
        val intent = Intent(Intent.ACTION_OPEN_DOCUMENT).apply {
            addCategory(Intent.CATEGORY_OPENABLE)
            type = "application/pdf"
        }
        val chooser = Intent.createChooser(intent,"Pilih File PDF")
        launcherGallery.launch(chooser)
    }

    private val launcherGallery = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ){
        if(it.resultCode == RESULT_OK){
            val file = getFileFromUri(it.data?.data as Uri)
            getFileAdministration = file
        }
    }

    private fun checkPermissionStorage(){
        Dexter.withContext(this@EditMemberActivity).withPermission(
            android.Manifest.permission.READ_EXTERNAL_STORAGE
        ).withListener(object: PermissionListener{
            override fun onPermissionGranted(p0: PermissionGrantedResponse?) {
                Help.showToast(this@EditMemberActivity,"Permission diizinikan")
                takeAFilePdfFromGallery()
            }

            override fun onPermissionDenied(p0: PermissionDeniedResponse?) {
                Help.showToast(this@EditMemberActivity,"Permission tidak diizinkan")
            }

            override fun onPermissionRationaleShouldBeShown(
                p0: PermissionRequest?,
                p1: PermissionToken?
            ) {
               p1?.continuePermissionRequest()
            }

        })
    }

   private fun getFileFromUri(uri: Uri): File? {
        val cursor = contentResolver.query(uri,null,null,null,null)
        cursor?.use { data ->
            val nameIndex = data.getColumnIndex(OpenableColumns.DISPLAY_NAME)
            data.moveToFirst()
            val fileName = data.getString(nameIndex)
            val cacheDir = externalCacheDir ?: cacheDir
            val tempFile = File(cacheDir, fileName)
            val inputStream = contentResolver.openInputStream(uri)
            inputStream?.use { input ->
                FileOutputStream(tempFile).use { output ->
                    val buffer = ByteArray(4 * 1024) // Adjust buffer size as needed
                    var bytesRead: Int
                    while (input.read(buffer).also { bytesRead = it } != -1) {
                        output.write(buffer, 0, bytesRead)
                    }
                    return tempFile
                }
            }
        }
        return null
    }
}