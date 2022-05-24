package com.github.maksban.noteapp.data.viewModel

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.asLiveData
import androidx.lifecycle.viewModelScope
import com.github.maksban.noteapp.data.CatalogRepository
import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.data.model.CatalogsWithNotes
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.launch

class CatalogViewModel(private val catalogRepository: CatalogRepository): ViewModel() {

    val allCatalogs: LiveData<List<Catalog>> = catalogRepository.allCatalogs.asLiveData()
    val catalogsWithNotes: LiveData<List<CatalogsWithNotes>> = catalogRepository.catalogsWithNotes.asLiveData()

    fun getCatalogById(catalogId: Int): Flow<CatalogsWithNotes> = catalogRepository.getCatalogById(catalogId)

    fun addCatalog(catalog: Catalog) = viewModelScope.launch {
        catalogRepository.addCatalog(catalog)
    }

    fun updateCatalog(catalog: Catalog) = viewModelScope.launch {
        catalogRepository.updateCatalog(catalog)
    }

    fun deleteCatalog(catalog: Catalog) = viewModelScope.launch {
        catalogRepository.deleteCatalog(catalog)
    }

    fun deleteAllCatalogs() = viewModelScope.launch {
        catalogRepository.deleteAllCatalogs()
    }
}