package com.candra.mydesiminationapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.data.source.local.LocalIndexLecturerItem
import com.candra.mydesiminationapp.databinding.ItemDesiminationBinding

class DesiminationAdapter(
    private val onClick: (LocalIndexLecturerItem) -> Unit,

): RecyclerView.Adapter<DesiminationAdapter.ViewHolder>()
{
    var nameStudent = ""
    inner class ViewHolder(private val binding: ItemDesiminationBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalIndexLecturerItem)
        {
            with(binding){
                data.apply {
                    valueIdDesimination.text = "#$id"
                    valueTitleDesimination.text = title
                    valueLeaderDesimination.text = headName
                    valueStatusDesimination.text = statusCaption
                }
                btnActionDesimination.setOnClickListener {
                    onClick(data)
                }
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): DesiminationAdapter.ViewHolder = ViewHolder(
        ItemDesiminationBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: DesiminationAdapter.ViewHolder, position: Int) {
        holder.bind(differ.currentList[position])
    }

    override fun getItemCount(): Int = differ.currentList.size

    private val differ = AsyncListDiffer(this,object: DiffUtil.ItemCallback<LocalIndexLecturerItem>(){
        override fun areItemsTheSame(
            oldItem: LocalIndexLecturerItem,
            newItem: LocalIndexLecturerItem
        ): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(
            oldItem: LocalIndexLecturerItem,
            newItem: LocalIndexLecturerItem
        ): Boolean {
           return oldItem == newItem
        }
    })

    fun submitListData(listData: List<LocalIndexLecturerItem>){
        differ.submitList(listData)
    }

}