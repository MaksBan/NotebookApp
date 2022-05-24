package com.github.maksban.noteapp.data

import androidx.room.*
import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.data.model.CatalogsWithNotes
import kotlinx.coroutines.flow.Flow

@Dao
interface CatalogDao {

    @Query("SELECT * FROM catalog_table")
    fun getAllCatalogs(): Flow<List<Catalog>>

    @Query("SELECT * FROM catalog_table WHERE id = :catalogId")
    fun getCatalogById(catalogId: Int): Flow<CatalogsWithNotes>

    @Transaction
    @Query("SELECT * FROM catalog_table")
    fun getCatalogsWithNotes(): Flow<List<CatalogsWithNotes>>

    @Insert()
    suspend fun addCatalog(catalog: Catalog)

    @Update
    suspend fun updateCatalog(catalog: Catalog)

    @Delete
    suspend fun deleteCatalog(catalog: Catalog)

    @Query("DELETE FROM catalog_table")
    suspend fun deleteAllCatalogs()

    @Insert
    fun insert(catalogsToInsert: List<Catalog>)

}