package com.candra.mydesiminationapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import coil.load
import com.candra.mydesiminationapp.R
import com.candra.mydesiminationapp.data.source.local.LocalImage
import com.candra.mydesiminationapp.databinding.ItemImageBinding
import com.candra.mydesiminationapp.helper.Help

class ImageAdapter: RecyclerView.Adapter<ImageAdapter.ViewHolder>()
{

    private val listImage = ArrayList<com.candra.mydesiminationapp.data.source.local.LocalImage>()

    inner class ViewHolder(private val binding: ItemImageBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bindData(data: com.candra.mydesiminationapp.data.source.local.LocalImage){
            with(binding){
                imgLogo.load(data.image.url){ error(R.drawable.baseline_broken_image_24) }
                noteLogo.text = Help.removeHtmlTags(data.note)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImageAdapter.ViewHolder {
        return ViewHolder(
            ItemImageBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: ImageAdapter.ViewHolder, position: Int) {
        holder.bindData(data = listImage[position])
    }

    override fun getItemCount(): Int  = listImage.size

    fun submitListData(it: List<com.candra.mydesiminationapp.data.source.local.LocalImage>){
        listImage.clear()
        listImage.addAll(it)
        notifyDataSetChanged()
    }

}