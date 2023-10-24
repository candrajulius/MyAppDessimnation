package com.candra.mydesiminationapp.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Adapter
import com.candra.mydesiminationapp.data.source.local.LocalParticipants
import com.candra.mydesiminationapp.databinding.ItemParticipantsBinding

class ParticipantsAdapter: RecyclerView.Adapter<ParticipantsAdapter.ViewHolder>()
{

    private val listParticipantsData = ArrayList<LocalParticipants>()

    inner class ViewHolder(private val binding: ItemParticipantsBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalParticipants){
            with(binding){
                valueNameStudent.text = data.student.name
                valueNim.text = data.student.code
                valueStatusDesimination.text = data.statusCaption
                valueRegisterStudent.text = data.createAt
                valueNotePresence.text = data.updateAt
            }
        }
    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): ParticipantsAdapter.ViewHolder {
        return ViewHolder(ItemParticipantsBinding.inflate(LayoutInflater.from(parent.context),parent,false))
    }

    override fun onBindViewHolder(holder: ParticipantsAdapter.ViewHolder, position: Int) {
        holder.bind(listParticipantsData[position])
    }

    override fun getItemCount(): Int = listParticipantsData.size

    @SuppressLint("NotifyDataSetChanged")
    fun submitListData(listParticipants: List<LocalParticipants>)
    {
        listParticipantsData.clear()
        listParticipantsData.addAll(listParticipants)
        notifyDataSetChanged()
    }

}