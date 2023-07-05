package com.hfad.qaydlar.fragments.list

import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.hfad.qaydlar.R
import com.hfad.qaydlar.data.QaydlarViewModel
import com.hfad.qaydlar.databinding.FragmentListBinding
import com.hfad.qaydlar.fragments.SharedViewModel

class ListFragment : Fragment() {
    private val mQaydlarViewModel: QaydlarViewModel by viewModels()
    private val mSharedViewModel: SharedViewModel by viewModels()
    private val adapter: ListAdapter by lazy { ListAdapter() }

    private var _binding: FragmentListBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentListBinding.inflate(inflater, container, false)
        setHasOptionsMenu(true)

        val recyclerView = binding.recyclerView
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(requireContext())
        mQaydlarViewModel.getAllData.observe(viewLifecycleOwner, Observer { data ->
            mSharedViewModel.checkIfDatabaseEmpty(data)
            adapter.setData(data)
        })

        binding.floatingActionButton.setOnClickListener {
            findNavController().navigate(R.id.action_listFragment_to_addFragment)
        }
        mSharedViewModel.emptyDatabase.observe(viewLifecycleOwner, Observer {
            showEmptyDatabaseView(it)
        })

        return binding.root
    }

    private fun showEmptyDatabaseView(emptyDatabase: Boolean) {
        if (emptyDatabase) {
            binding.noDateImageView.visibility = View.VISIBLE
            binding.noDateTextView.visibility = View.VISIBLE
        } else {
            binding.noDateImageView.visibility = View.INVISIBLE
            binding.noDateTextView.visibility = View.INVISIBLE
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.list_fragment_menu, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menu_delete_all -> confirmRemoval()
        }
        return super.onOptionsItemSelected(item)
    }

    private fun confirmRemoval() {
        val builder = AlertDialog.Builder(requireContext())
        builder.setPositiveButton("Ha") { _, _ ->
            mQaydlarViewModel.deleteAll()
            Toast.makeText(requireContext(), "Barchasi muvaffaqiyatli o'chirildi",
                Toast.LENGTH_SHORT).show()
        }
        builder.setNegativeButton("Yoq") { _, _ -> }
        builder.setTitle("O'chirish so'rovi")
        builder.setMessage("Barchasini oʻchirib tashlamoqchimisiz??")
        builder.create().show()
    }
}