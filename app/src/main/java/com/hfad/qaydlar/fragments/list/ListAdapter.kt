package com.hfad.qaydlar.fragments.list

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.hfad.qaydlar.data.QaydlarData
import com.hfad.qaydlar.databinding.RowLayoutBinding

class ListAdapter : RecyclerView.Adapter<ListAdapter.MyViewHolder>() {

    var dataList = emptyList<QaydlarData>()

    class MyViewHolder(internal val binding: RowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(qaydlarData: QaydlarData) {
            binding.qaydlarData = qaydlarData
            binding.executePendingBindings()
        }

        companion object {
            fun from(parent: ViewGroup): MyViewHolder {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = RowLayoutBinding.inflate(layoutInflater, parent, false)
                return MyViewHolder(binding)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder.from(parent)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = dataList[position]
        holder.bind(currentItem)
    }

    fun setData(qaydlarData: List<QaydlarData>) {
        val qaydlarDiffUtil = QaydlarDiffUtil(dataList, qaydlarData)
        val qaydlarDiffResult = DiffUtil.calculateDiff(qaydlarDiffUtil)
        this.dataList = qaydlarData
        qaydlarDiffResult.dispatchUpdatesTo(this)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }
}