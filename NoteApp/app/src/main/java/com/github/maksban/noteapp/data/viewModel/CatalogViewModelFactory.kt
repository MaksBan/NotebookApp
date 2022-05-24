package com.github.maksban.noteapp.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.maksban.noteapp.data.CatalogRepository


class CatalogViewModelFactory(private val catalogRepository: CatalogRepository): ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if(modelClass.isAssignableFrom(CatalogViewModel::class.java)){
            @Suppress("UNCHECKED_CAST")
            return CatalogViewModel(catalogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }

}