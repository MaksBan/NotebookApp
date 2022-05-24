package com.github.maksban.noteapp.fragments.catalogs.add

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.maksban.noteapp.App
import com.github.maksban.noteapp.R
import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.data.viewModel.CatalogViewModel
import com.github.maksban.noteapp.data.viewModel.CatalogViewModelFactory
import com.github.maksban.noteapp.databinding.FragmentAddCatalogBinding


class AddCatalogFragment : Fragment(R.layout.fragment_add_catalog) {

    private lateinit var binding: FragmentAddCatalogBinding
    private val viewModel: CatalogViewModel by activityViewModels {
        CatalogViewModelFactory((activity?.application as App).catalogRepository)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddCatalogBinding.bind(view)

        binding.btnSaveNote.setOnClickListener {
            insertCatalogToDatabase()

        }
    }

    private fun insertCatalogToDatabase() {
        val title = binding.etTitle.text.toString()
        if(inputCheck(title)){
            val catalog = Catalog(0, title)
            viewModel.addCatalog(catalog)
            Toast.makeText(requireContext(), "Catalog successfully added", Toast.LENGTH_LONG).show()
            findNavController().popBackStack()
        } else Toast.makeText(requireContext(), "Please insert a title", Toast.LENGTH_LONG).show()
    }

    private fun inputCheck(title: String): Boolean {
        return (!TextUtils.isEmpty(title))
    }
}