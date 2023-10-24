package com.candra.mydesiminationapp.activity.admin

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
import com.candra.mydesiminationapp.activity.TempActivity
import com.candra.mydesiminationapp.adapter.AdapterIndex
import com.candra.mydesiminationapp.data.source.local.LocalIndexAdministratorItem
import com.candra.mydesiminationapp.data.source.remote.network.GetTokenLecturer
import com.candra.mydesiminationapp.data.source.remote.network.States
import com.candra.mydesiminationapp.databinding.IndexAdministratorActivityBinding
import com.candra.mydesiminationapp.helper.Constant
import com.candra.mydesiminationapp.helper.Help
import com.candra.mydesiminationapp.helper.TempDataTwo
import com.candra.mydesiminationapp.helper.viewmodel.administrator.ResearchDisseminationAdminViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class IndexAdministratorActivity: AppCompatActivity()
{
    private lateinit var binding: IndexAdministratorActivityBinding
    @Inject
    lateinit var localStore: TempDataTwo
    private val disseminationAdapterAdmin by lazy { AdapterIndex(::onClick) }
    private val adminViewModel by viewModels<ResearchDisseminationAdminViewModel>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = IndexAdministratorActivityBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.apply {
            imageLogo.load(Constant.URL_IMAGE_SISKA_DISSEMINATION){
                error(R.drawable.baseline_broken_image_24)
            }
        }
        observerAllData()
        addToRecyclerView()
        Help.setToolbar(1,supportActionBar,"Index Admin")
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu,menu)
        return super.onCreateOptionsMenu(menu)
    }

    private fun onClick(data: LocalIndexAdministratorItem){
        Intent(this@IndexAdministratorActivity,ShowDisseminationActivity::class.java).apply {
            putExtra(Constant.EXTRA_STUDENT_ID,data)
        }.also { startActivity(it) }
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == R.id.logout_lecturer){
            lifecycleScope.launch {
                localStore.putToken(GetTokenLecturer("","","",5,"")).also {
                    startActivity(Intent(this@IndexAdministratorActivity,TempActivity::class.java)).
                            also { finish() }
                }
            }
        }
        return super.onOptionsItemSelected(item)
    }


    private fun addToRecyclerView(){
        binding.rvAdmin.apply {
            layoutManager = if (applicationContext.resources.configuration.orientation == Configuration.ORIENTATION_LANDSCAPE
            ) GridLayoutManager(this@IndexAdministratorActivity,2)
            else LinearLayoutManager(this@IndexAdministratorActivity)
            setHasFixedSize(true)
            adapter = disseminationAdapterAdmin
        }
    }

    private fun setDataAllToRecyclerView(token: String){
        adminViewModel.getIndexResearchDisseminationAdministrator(token).observe(this@IndexAdministratorActivity)
        {
            when(it){
                is States.Loading -> showProgressBar(true)
                is States.Success -> {
                    showProgressBar(false)
                    disseminationAdapterAdmin.submitListData(it.data)
                }
                is States.Failed -> {
                    Help.showToast(this@IndexAdministratorActivity,"Terjadi kesalahan pada server")
                    showProgressBar(false)
                }
                else -> {}
            }
        }
    }

    private fun showProgressBar(isShow: Boolean){
        with(binding){
            progressBarIndex.visibility = if (isShow) View.VISIBLE else View.GONE
            rvAdmin.visibility = if (isShow) View.GONE else View.VISIBLE
        }
    }

    private fun observerAllData(){
        lifecycleScope.launch{
            localStore.getToken().collect{
                if (it.category == Constant.ADMINISTRATOR) setDataAllToRecyclerView(it.token)
            }
        }
    }
}