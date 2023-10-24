package com.candra.mydesiminationapp.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.candra.mydesiminationapp.data.source.local.LocalIndexAdministratorItem
import com.candra.mydesiminationapp.databinding.ItemDisseminationAdminBinding
import com.candra.mydesiminationapp.databinding.ItemTextBinding

class AdapterIndex(
    private val onCLick: (LocalIndexAdministratorItem) -> Unit
): RecyclerView.Adapter<RecyclerView.ViewHolder>()
{
    private val items = ArrayList<Any>()

    companion object{
        private const val ITEM_HEADER = 0
        private const val ITEM_MENU = 1
    }

    inner class TextViewHolder(private val binding: ItemTextBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalIndexAdministratorItem){
            with(binding){
                mtvStatus.text = data.status_caption
            }
        }
    }

    inner class MenuViewHolder(private val binding: ItemDisseminationAdminBinding): RecyclerView.ViewHolder(binding.root)
    {
        fun bind(data: LocalIndexAdministratorItem){
            with(binding){
                valueIdDesimination.text = "#${data.id}"
                valueTitleDesimination.text = data.title
                valueLeaderDesimination.text = data.student.name
                valueStatusDesimination.text = data.status_caption
                valueLecturer.text = data.lectruer
                btnActionDesimination.setOnClickListener {
                    onCLick(data)
                }
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when(items[position]){
            is String -> ITEM_HEADER
            is LocalIndexAdministratorItem -> ITEM_MENU
            else -> throw IllegalArgumentException("Undefined view type")
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when(viewType){
            ITEM_HEADER -> TextViewHolder(ItemTextBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            ITEM_MENU -> MenuViewHolder(ItemDisseminationAdminBinding.inflate(LayoutInflater.from(parent.context),parent,false))
            else -> throw IllegalArgumentException("Invalid view type")
        }
    }

    override fun getItemCount(): Int = items.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when(holder){
            is TextViewHolder -> holder.bind(items[position] as LocalIndexAdministratorItem)
            is MenuViewHolder -> holder.bind(items[position] as LocalIndexAdministratorItem)
        }
    }

    fun submitListData(it: List<LocalIndexAdministratorItem>){
        items.clear()
        items.addAll(it)
        notifyDataSetChanged()
    }


}