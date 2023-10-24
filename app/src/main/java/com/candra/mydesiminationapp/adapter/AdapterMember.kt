package com.candra.mydesiminationapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.data.source.local.LocalMember
import com.candra.mydesiminationapp.databinding.ItemMembersBinding

class AdapterMember: RecyclerView.Adapter<AdapterMember.ViewHolder>()
{
    private val memberList = ArrayList<LocalMember>()

    inner class ViewHolder(private val binding: ItemMembersBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalMember){
            with(binding){
               memberTeamResearch.text = data.asX
               valueMemberTeamResearch.text = data.student.name
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AdapterMember.ViewHolder {
        return ViewHolder(
            ItemMembersBinding.inflate(LayoutInflater.from(parent.context),parent,false)
        )
    }

    override fun onBindViewHolder(holder: AdapterMember.ViewHolder, position: Int) {
        holder.bind(memberList[position])
    }

    override fun getItemCount(): Int = memberList.size

    fun submitListData(it: List<LocalMember>){
        memberList.clear()
        memberList.addAll(it)
        notifyDataSetChanged()
    }

}