package com.candra.mydesiminationapp.activity

import android.content.Intent
import android.content.res.Configuration
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import coil.load
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.adapter.DesiminationAdapter
import com.candra.mydesiminationapp.data.source.local.LocalIndexLecturerItem
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.ActivityDesiminationBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.Lecturer.ResearchDisseminationViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class LecturerActivity: AppCompatActivity()
{
    private lateinit var lecturerBinding: ActivityDesiminationBinding
    @Inject
    lateinit var localStore: TempDataTwo
    private val lecturerViewModel by viewModels<ResearchDisseminationViewModel>()
    private val lecturerAdapter by lazy { DesiminationAdapter(::onClick) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        lecturerBinding = ActivityDesiminationBinding.inflate(layoutInflater)
        setContentView(lecturerBinding.root)

        lecturerBinding.imageLogo.load(Constant.URL_IMAGE_SISKA_DISSEMINATION){
            error(R.drawable.baseline_broken_image_24)
        }
        lecturerBinding.btnAddDesimination.setOnClickListener {
            addLeaderDissemination()
        }
        responseViewModel()

        addToRecyclerView()

        Help.setToolbar(1,supportActionBar,Constant.TITTLE_DISSEMINATION)

    }

    private fun addLeaderDissemination(){
        startActivity(Intent(this@LecturerActivity,AddLeaderDisseminationActivity::class.java))
    }


    private fun onClick(disseminationLecturer: LocalIndexLecturerItem){
        val targetActivity = if (disseminationLecturer.statusCaption == Constant.STATUS_NEW
            || disseminationLecturer.statusCaption == Constant.STATUS_DRAFT_REGISTER) DetailDisseminationOne::class.java
        else DetailDisseminationTwo::class.java
        Intent(this@LecturerActivity,targetActivity).apply {
            putExtra(Constant.EXTRA_STUDENT_ID,disseminationLecturer)
        }.also { startActivity(it).also { finish() } }
    }

    private fun responseViewModel(){
        lifecycleScope.launch {
            localStore.getToken().collect{
                if(it.category == Constant.LECTURER) setDataToRecyclerView(it.token)
            }
        }

    }
    private fun setDataToRecyclerView(token: String){
        lecturerViewModel.getIndexLecturerItem(token).observe(this){
            when(it){
                is States.Loading -> showProgressBar(true)
                is States.Success ->{
                    showProgressBar(false)
                    lecturerAdapter.submitListData(it.data)
                }
                is States.Failed -> {
                    Help.showToast(this@LecturerActivity,it.message.toString())
                }
                else -> {}
            }
        }
    }

    private fun showProgressBar(isShow:Boolean){
        with(lecturerBinding){
            progressBarIndex.visibility = if (isShow) View.VISIBLE else View.GONE
            recyclerviewIndex.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun addToRecyclerView(){
        lecturerBinding.recyclerviewIndex.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@LecturerActivity,2)
            else LinearLayoutManager(this@LecturerActivity)
            setHasFixedSize(true)
            adapter = lecturerAdapter
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
                   .also { startActivity(Intent(this@LecturerActivity,TempActivity::class.java))
                       .also { finish() }}
            }
        }
        return super.onOptionsItemSelected(item)
    }

}