package com.github.maksban.noteapp.data

import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.data.model.CatalogsWithNotes
import kotlinx.coroutines.flow.Flow

class CatalogRepository(private val catalogDao: CatalogDao) {

    val allCatalogs: Flow<List<Catalog>> = catalogDao.getAllCatalogs()
    val catalogsWithNotes: Flow<List<CatalogsWithNotes>> = catalogDao.getCatalogsWithNotes()

    fun getCatalogById(catalogId: Int): Flow<CatalogsWithNotes> {
        return catalogDao.getCatalogById(catalogId)
    }

    suspend fun addCatalog(catalog: Catalog){
        catalogDao.addCatalog(catalog)
    }

    suspend fun updateCatalog(catalog: Catalog){
        catalogDao.updateCatalog(catalog)
    }

    suspend fun deleteCatalog(catalog: Catalog){
        catalogDao.deleteCatalog(catalog)
    }

    suspend fun deleteAllCatalogs(){
        catalogDao.deleteAllCatalogs()
    }

    suspend fun insert(catalogsToInsert: List<Catalog>) {
        catalogDao.insert(catalogsToInsert)

    }

}