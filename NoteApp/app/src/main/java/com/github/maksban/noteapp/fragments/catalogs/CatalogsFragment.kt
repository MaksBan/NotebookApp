package com.github.maksban.noteapp.fragments.catalogs

import android.app.AlertDialog
import android.content.DialogInterface
import android.os.Bundle
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
import com.github.maksban.noteapp.databinding.FragmentCatalogsBinding
import com.github.maksban.noteapp.extensions.putCatalogId
import com.google.android.material.snackbar.Snackbar

class CatalogsFragment : Fragment(R.layout.fragment_catalogs) {

    private lateinit var binding: FragmentCatalogsBinding
    private lateinit var catalogAdapter: CatalogAdapter
    private val viewModel: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory(
            (activity?.application as App).catalogRepository
        )
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentCatalogsBinding.bind(view)

        setupRecyclerView()
        observeAllCatalogs()

        handleButtonClick()
        handleMenuClick()


    }

    private fun setupRecyclerView() {
        catalogAdapter = CatalogAdapter {
            findNavController().navigate(
                R.id.action_catalogsFragment_to_catalogDetailFragment,
                Bundle().putCatalogId(it.id)
            )
        }
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = catalogAdapter

        // DELETE ON SWIPE
        recyclerView.addOnSwipeCallback(ItemTouchHelper.LEFT) {
            val catalog = catalogAdapter.getByPosition(it)
            viewModel.deleteCatalog(catalog)
            Snackbar.make(requireView(), "Deleted successfuly", Snackbar.LENGTH_LONG)
                .apply {
                    setAction("Undo") {
                        viewModel.addCatalog(catalog)
                    }
                        .show()
                }
        }
    }

    private fun observeAllCatalogs() {
        viewModel.allCatalogs.observe(this.viewLifecycleOwner, Observer { items ->
            items.let { catalogAdapter.submitList(it) }
        })
    }

    private fun handleButtonClick() {
        binding.btcAddNote.setOnClickListener {
            findNavController().navigate(R.id.action_catalogsFragment_to_addCatalogFragment)
        }
    }

    private fun handleMenuClick() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_delete_catalogs -> {
                    deleteCatalogs()
                    true
                }
                else -> false
            }
        }
    }

    private fun deleteCatalogs() {
        val builder = AlertDialog.Builder(this.context)
        builder.apply {
            builder.apply { }
                .setTitle("Confirm Delete")
                .setMessage("Are you sure you want to delete all catalogs?")
                .setPositiveButton("Yes", DialogInterface.OnClickListener { dialogInterface, id ->
                    viewModel.deleteAllCatalogs()
                    Toast.makeText(
                        requireContext(),
                        "All catalogs has been deleted",
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
}