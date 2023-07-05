package com.hfad.qaydlar.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.hfad.qaydlar.R
import com.hfad.qaydlar.data.Priority
import com.hfad.qaydlar.data.QaydlarData
import com.hfad.qaydlar.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<QaydlarData>()

    class MyViewHolder(internal val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RowLayoutBinding.inflate(LayoutInflater.from(parent.context),
            parent,
            false))
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        val binding = holder.binding

        binding.titleTxt.text = currentItem.title
        binding.descriptionTxt.text = currentItem.description
        binding.rowBackground.setOnClickListener {
            val action =
                ListFragmentDirections.actionListFragmentToUpdateFragment(dataList[position])
            holder.itemView.findNavController().navigate(action)
        }
        when (currentItem.priority) {
            Priority.HIGH -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.red))
            Priority.MEDIUM -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.yellow))
            Priority.LOW -> binding.priorityIndicator.setCardBackgroundColor(ContextCompat.getColor(
                holder.itemView.context,
                R.color.green))
        }
    }

    fun setData(qaydlarData: List<QaydlarData>){
        this.dataList = qaydlarData
        notifyDataSetChanged()
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}