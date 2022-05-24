package com.github.maksban.noteapp.fragments.notes

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
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
import com.github.maksban.noteapp.databinding.FragmentNotesBinding
import com.github.maksban.noteapp.extensions.putCatalogId
import com.github.maksban.noteapp.fragments.catalogs.CatalogAdapter
import com.github.maksban.noteapp.fragments.dialog.FragmentDialogApi
import com.google.android.material.snackbar.Snackbar

const val TAG = "NotesFragment"
class NotesFragment : Fragment(R.layout.fragment_notes) {

    private lateinit var binding: FragmentNotesBinding
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var catalogAdapter: CatalogAdapter
    private val catalogViewModel: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory(
            (activity?.application as App).catalogRepository
        )
    }

    private val noteViewModel: NoteViewModel by activityViewModels {
        NoteViewModelFactory(
            (activity?.application as App).repository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentNotesBinding.bind(view)
        setHasOptionsMenu(true)

        setupRecyclerView()

        observeAllNotes()
        observeAllCatalogs()

        handleMenuClick()
        handleBtnAddNote()
        handleBtnAddCatalog()
        handleBtnClickMe()



    }

    private fun handleBtnClickMe() {
        binding.btnFact.setOnClickListener {
            val dialog = FragmentDialogApi()
            dialog.show(childFragmentManager, "customDialog")
        }

    }

    private fun setupRecyclerView() {
        noteAdapter = NoteAdapter {
            val action = NotesFragmentDirections.actionNotesFragmentToDetailFragment(it)
            findNavController().navigate(action)
        }
        val recyclerView = binding.rvNotes
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = noteAdapter

        // DELETE ON SWIPE
        recyclerView.addOnSwipeCallback(ItemTouchHelper.LEFT) {
            val note = noteAdapter.getByPosition(it)
            noteViewModel.deleteNote(note)
            Snackbar.make(requireView(),"Deleted successfuly", Snackbar.LENGTH_LONG)
                .apply {
                    setAction("Undo"){
                        noteViewModel.addNote(note)
                    }
                    show()
                }
        }

        catalogAdapter = CatalogAdapter {
            findNavController().navigate(
                R.id.action_notesFragment_to_catalogDetailFragment,
                Bundle().putCatalogId(it.id)
            )
        }
        val catalogRecyclerView = binding.rvCatalogs
        catalogRecyclerView.layoutManager =
            LinearLayoutManager(this.context, LinearLayoutManager.HORIZONTAL, false)
        catalogRecyclerView.adapter = catalogAdapter
    }

    private fun observeAllNotes() {
        noteViewModel.allNotes.observe(this.viewLifecycleOwner, Observer { items ->
            items.let { noteAdapter.submitList(it) }
        })
    }

    private fun observeAllCatalogs() {
        catalogViewModel.allCatalogs.observe(this.viewLifecycleOwner, Observer { items ->
            items.let { catalogAdapter.submitList(it) }
        })
    }

    private fun handleBtnAddNote() {
        binding.btnAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addFragment)
        }
    }

    private fun handleBtnAddCatalog() {
        binding.btnAddCatalog.setOnClickListener {
            findNavController().navigate(R.id.action_notesFragment_to_addCatalogFragment)
        }
    }

    private fun handleMenuClick() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete_notes -> {
                    deleteNotes()
//                    noteViewModel.deleteAllNotes()
                    true
                }
                R.id.action_catalogs -> {
                    findNavController().navigate(R.id.action_notesFragment_to_catalogsFragment)
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.upper_menu, menu)
    }

    private fun deleteNotes() {
        val builder = AlertDialog.Builder(this.context)
        builder.apply { }
            .setTitle("Confirm Delete")
            .setMessage("Are you sure you want to delete all notes?")
            .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, id ->
                noteViewModel.deleteAllNotes()
                Toast.makeText(
                    requireContext(),
                    "All notes has been deleted",
                    Toast.LENGTH_LONG
                ).show()
                dialogInterface.cancel()
            })
            .setNegativeButton("No", DialogInterface.OnClickListener { dialogInterface, id ->
                dialogInterface.cancel()
            })
        val alert = builder.create()
        alert.show()
    }
}