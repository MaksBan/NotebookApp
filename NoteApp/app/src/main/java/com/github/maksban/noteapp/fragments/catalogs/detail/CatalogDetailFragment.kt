package com.github.maksban.noteapp.fragments.catalogs.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.maksban.noteapp.App
import com.github.maksban.noteapp.R
import com.github.maksban.noteapp.addOnSwipeCallback
import com.github.maksban.noteapp.data.viewModel.CatalogViewModel
import com.github.maksban.noteapp.data.viewModel.CatalogViewModelFactory
import com.github.maksban.noteapp.data.viewModel.NoteViewModel
import com.github.maksban.noteapp.data.viewModel.NoteViewModelFactory
import com.github.maksban.noteapp.databinding.FragmentCatalogDetailBinding
import com.github.maksban.noteapp.extensions.getCatalogId
import com.github.maksban.noteapp.fragments.notes.NoteAdapter
import com.google.android.material.snackbar.Snackbar
import java.lang.IllegalArgumentException

class CatalogDetailFragment : Fragment(R.layout.fragment_catalog_detail) {
    private lateinit var binding: FragmentCatalogDetailBinding
    private lateinit var adapter: NoteAdapter
    private val catalogViewModel: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory((activity?.application as App).catalogRepository)
    }

    private val noteViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory((activity?.application as App).repository)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogDetailBinding.bind(view)
        val catalogId = arguments?.getCatalogId()
            ?: throw IllegalArgumentException("Bundle supplied to CatalogDetailFragment doesn't contain catalog id")


        adapter = NoteAdapter {
            val action = CatalogDetailFragmentDirections.actionCatalogDetailFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = adapter

        // DELETE ON SWIPE
        recyclerView.addOnSwipeCallback(ItemTouchHelper.LEFT) {
            val note = adapter.getByPosition(it)
            noteViewModel.deleteNote(note)
            Snackbar.make(requireView(), "Deleted successfuly", Snackbar.LENGTH_LONG)
                .apply{
                    setAction("Undo"){
                        noteViewModel.addNote(note)
                    }
                        .show()
                }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenCreated {
            catalogViewModel.getCatalogById(catalogId).collect() {
                adapter.submitList(it.notes)
            }
        }
    }

}

