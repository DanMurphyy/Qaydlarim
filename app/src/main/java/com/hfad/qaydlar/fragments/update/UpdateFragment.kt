package com.hfad.qaydlar.fragments.update

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.hfad.qaydlar.R
import com.hfad.qaydlar.data.QaydlarData
import com.hfad.qaydlar.data.QaydlarViewModel
import com.hfad.qaydlar.databinding.FragmentUpdateBinding
import com.hfad.qaydlar.fragments.SharedViewModel

class UpdateFragment : Fragment() {
    private val args by navArgs<UpdateFragmentArgs>()
    private val mQaydlarViewModel: QaydlarViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentUpdateBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentUpdateBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        showInfo()
        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.update_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_save -> updateItem()
            R.id.menu_delete ->confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun updateItem() {
        val mTitle = binding.currentTitleEt.text.toString()
        val mPriority = binding.currentPrioritiesSpinner.selectedItem.toString()
        val mDescription = binding.currentDescriptionEt.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            //Insert Data to Database
            val updatedData = QaydlarData(
                args.currentItem.id,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mQaydlarViewModel.updateData(updatedData)
            Toast.makeText(requireContext(), "Muvaffaqiyatli o'zgartirildi!", Toast.LENGTH_SHORT)
                .show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(),
                "Iltimos, barcha joylarni toʻldiring.",
                Toast.LENGTH_SHORT).show()
        }
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ha"){_,_ ->
            mQaydlarViewModel.deleteItem(args.currentItem)
            Toast.makeText(requireContext(),"${args.currentItem.title} : Muvaffaqiyatli o'chirildi",Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_updateFragment_to_listFragment)
        }
        builder.setNegativeButton("Yoq"){_,_ ->}
        builder.setTitle("O'chirish so'rovi")
        builder.setMessage("Haqiqatan ham \b''${args.currentItem.title}''ni oʻchirib tashlamoqchimisiz?")
        builder.create().show()
    }

    private fun showInfo() {
        binding.currentPrioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener
        binding.currentTitleEt.setText(args.currentItem.title)
        binding.currentDescriptionEt.setText(args.currentItem.description)
        binding.currentPrioritiesSpinner.setSelection(mSharedViewModel.parsePriorityToInt(args.currentItem.priority))
    }
}