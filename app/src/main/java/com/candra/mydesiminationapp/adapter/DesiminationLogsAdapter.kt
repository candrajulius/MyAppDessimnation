package com.candra.mydesiminationapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.data.source.local.LocalLogDisseminationLecturerItem
import com.candra.mydesiminationapp.databinding.ItemDesiminationLogBinding
import com.candra.mydesiminationapp.helper.Help

class DesiminationLogsAdapter: RecyclerView.Adapter<DesiminationLogsAdapter.ViewHolder>()
{
    private var listDataLog = ArrayList<LocalLogDisseminationLecturerItem>()

    inner class ViewHolder(private val binding: ItemDesiminationLogBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bindData(data: LocalLogDisseminationLecturerItem)
        {
            with(binding){
                data.apply {
                    valueOnTheDay.text = createdAtCaption
                    valueByName.text = byName
                    valueNote.text = Help.removeHtmlTags(note)
                    valueStatusDesimination.text = statusCaption
                }
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): DesiminationLogsAdapter.ViewHolder =
        ViewHolder(
            ItemDesiminationLogBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )


    override fun getItemCount(): Int = listDataLog.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(listDataLog[position])
    }

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(listData: List<LocalLogDisseminationLecturerItem>){
        listDataLog.clear()
        listDataLog.addAll(listData)
        notifyDataSetChanged()
    }

}