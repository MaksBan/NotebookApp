package com.github.maksban.noteapp.fragments.dialog

import android.os.Bundle
import android.view.View
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.github.maksban.noteapp.App
import com.github.maksban.noteapp.R
import com.github.maksban.noteapp.data.viewModel.EditNoteViewModel
import com.github.maksban.noteapp.databinding.DialogChooseCatalogBinding
import com.github.maksban.noteapp.fragments.catalogs.CatalogAdapter

class FragmentDialog: DialogFragment(R.layout.dialog_choose_catalog) {
    private lateinit var binding: DialogChooseCatalogBinding
    private lateinit var catalogAdapter: CatalogAdapter

    private val viewModel: EditNoteViewModel by activityViewModels {
        (activity?.application as App).editNoteViewModelFactory
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogChooseCatalogBinding.bind(view)

        catalogAdapter = CatalogAdapter {
            viewModel.catalogChanged(it)
            dismiss()
        }
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this.context)
        recyclerView.adapter = catalogAdapter
        viewModel.allCatalogs.observe(this.viewLifecycleOwner, Observer { items ->
            items.let { catalogAdapter.submitList(it) }

        })

        binding.btnCancel.setOnClickListener {
            dismiss()
        }

    }
}