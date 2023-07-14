package com.hfad.qaydlar.fragments.add

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.google.android.gms.ads.AdRequest
import com.google.android.gms.ads.MobileAds
import com.hfad.qaydlar.R
import com.hfad.qaydlar.data.QaydlarData
import com.hfad.qaydlar.data.QaydlarViewModel
import com.hfad.qaydlar.databinding.FragmentAddBinding
import com.hfad.qaydlar.fragments.SharedViewModel

class AddFragment : Fragment() {

    private val mQaydlarViewModel: QaydlarViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()

    private var _binding: FragmentAddBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentAddBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)
        binding.prioritiesSpinner.onItemSelectedListener = mSharedViewModel.listener

        MobileAds.initialize(requireContext())
        val adRequest = AdRequest.Builder().build()
        binding.adView2.loadAd(adRequest)

        return binding.root
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.add_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_add -> insertDataToDB()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun insertDataToDB() {
        val mTitle = binding.titleEt.text.toString()
        val mPriority = binding.prioritiesSpinner.selectedItem.toString()
        val mDescription = binding.descriptionEt.text.toString()

        val validation = mSharedViewModel.verifyDataFromUser(mTitle, mDescription)

        if (validation) {
            //Insert Data to Database
            val newData = QaydlarData(
                0,
                mTitle,
                mSharedViewModel.parsePriority(mPriority),
                mDescription
            )
            mQaydlarViewModel.insertData(newData)
            Toast.makeText(requireContext(), "Muvaffaqiyatli qo'shildi!", Toast.LENGTH_SHORT).show()
            findNavController().navigate(R.id.action_addFragment_to_listFragment)
        } else {
            Toast.makeText(requireContext(),
                "Iltimos, barcha joylarni toʻldiring.",
                Toast.LENGTH_SHORT).show()
        }
    }
}