package com.candra.mydesiminationapp.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.data.source.local.LocalMember
import com.candra.mydesiminationapp.databinding.ItemMemberResearchTeamBinding
import com.candra.mydesiminationapp.helper.Constant

class AdapterManagementMember(
    private val onCorrectionStudent: (LocalMember) -> Unit
): RecyclerView.Adapter<AdapterManagementMember.ViewHolder>()
{

    private val memberList = ArrayList<LocalMember>()

    inner class ViewHolder(private val binding: ItemMemberResearchTeamBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalMember){
            with(binding){
//                if(data.asX != Constant.LEADER_STUDENT) {
                    if (data.student.status.id == 0) {
                        valueName.visibility = View.GONE
                        valueNim.text = data.student.code
                        valueStatus.text = data.student.status.caption
                    } else {
                        valueName.text = data.student.name
                        valueName.visibility = View.VISIBLE
                        valueNim.text = data.student.code
                        valueStatus.text = data.student.status.caption
                    }
//                }
//                else{
//                    containerManagementMember.visibility = View.GONE
//                }
                btnActionEdit.setOnClickListener {
                    onCorrectionStudent(data)
                }
            }
        }

    }

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ): AdapterManagementMember.ViewHolder = ViewHolder(
        ItemMemberResearchTeamBinding.inflate(LayoutInflater.from(parent.context),parent,false)
    )

    override fun onBindViewHolder(holder: AdapterManagementMember.ViewHolder, position: Int) {
        holder.bind(data = memberList[position])
    }

    override fun getItemCount(): Int = memberList.size

    fun submitListData(it: List<LocalMember>){
        memberList.clear()
        memberList.addAll(it)
        memberList.removeAt(0)
        notifyDataSetChanged()
    }

}