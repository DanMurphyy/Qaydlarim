package com.hfad.qaydlar.fragments

import android.annotation.SuppressLint
import android.view.View
import android.widget.Spinner
import androidx.cardview.widget.CardView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.hfad.qaydlar.R
import com.hfad.qaydlar.data.Priority
import com.hfad.qaydlar.data.QaydlarData
import com.hfad.qaydlar.fragments.list.ListFragmentDirections

class BindingAdapters {

    companion object{

        @BindingAdapter("android:navigateToAddFragment")
        @JvmStatic
        fun navigateToAddFragment(view: FloatingActionButton, navigate: Boolean){
            view.setOnClickListener {
                if (navigate){
                    view.findNavController().navigate(R.id.action_listFragment_to_addFragment)
                }
            }
        }

        @BindingAdapter("android:emptyDatabase")
        @JvmStatic
        fun emptyDatabase(view: View, emptyDatabase: MutableLiveData<Boolean>){
            when (emptyDatabase.value) {
                true -> view.visibility = View.VISIBLE
                false -> view.visibility = View.INVISIBLE
                null -> TODO()
            }
        }
        @BindingAdapter("android:parsePriorityToInt")
        @JvmStatic
        fun parsePriorityToInt(view: Spinner, priority: Priority){
            when(priority) {
                Priority.HIGH ->(view.setSelection(0))
                Priority.MEDIUM ->(view.setSelection(1))
                Priority.LOW ->(view.setSelection(2))
            }
        }
        @SuppressLint("NewApi")
        @BindingAdapter("android:ParsePriorityColor")
        @JvmStatic
        fun ParsePriorityColor(cardView: CardView, priority: Priority){
            when(priority){
                Priority.HIGH ->{cardView.setCardBackgroundColor(cardView.context.getColor(R.color.red))}
                Priority.MEDIUM ->{cardView.setCardBackgroundColor(cardView.context.getColor(R.color.yellow))}
                Priority.LOW ->{cardView.setCardBackgroundColor(cardView.context.getColor(R.color.green))}
            }
        }

        @BindingAdapter("android:sendDataToUpdateFragment")
        @JvmStatic
        fun sendDataToUpdateFragment(view: ConstraintLayout, currentItem: QaydlarData){
            view.setOnClickListener {
                val action = ListFragmentDirections.actionListFragmentToUpdateFragment(currentItem)
                view.findNavController().navigate(action)
            }
        }
    }
}