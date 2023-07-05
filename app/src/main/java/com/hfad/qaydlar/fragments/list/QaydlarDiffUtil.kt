package com.hfad.qaydlar.fragments.list

import androidx.recyclerview.widget.DiffUtil
import com.hfad.qaydlar.data.QaydlarData

class QaydlarDiffUtil(
    private val oldList: List<QaydlarData>,
    private val newList: List<QaydlarData>,
) : DiffUtil.Callback() {
    override fun getOldListSize(): Int {
        return oldList.size
    }

    override fun getNewListSize(): Int {
        return newList.size
    }

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition] === newList[newItemPosition]
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldList[oldItemPosition].id == newList[newItemPosition].id
                &&
                oldList[oldItemPosition].title == newList[newItemPosition].title
                &&
                oldList[oldItemPosition].priority == newList[newItemPosition].priority
                &&
                oldList[oldItemPosition].description == newList[newItemPosition].description
    }
}