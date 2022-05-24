package com.github.maksban.noteapp

import android.app.Application
import android.content.Context
import com.github.maksban.noteapp.data.CatalogRepository
import com.github.maksban.noteapp.data.NoteDatabase
import com.github.maksban.noteapp.data.NoteRepository
import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.data.model.Note
import com.github.maksban.noteapp.data.viewModel.EditNoteViewModelFactory
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class App : Application() {

    val database: NoteDatabase by lazy { NoteDatabase.getDatabase(this) }
    val repository by lazy { NoteRepository(database.noteDao()) }
    val catalogRepository by lazy { CatalogRepository(database.catalogDao()) }
    val editNoteViewModelFactory by lazy {
        EditNoteViewModelFactory(
            repository,
            catalogRepository
        )
    }

    override fun onCreate() {
        super.onCreate()

        val preferences = applicationContext.getSharedPreferences("Notes", Context.MODE_PRIVATE)
        val applicationWasAlreadyStarted = preferences.getBoolean("APP_WAS_STARTED", false)

        if(applicationWasAlreadyStarted.not()){
            preferences.edit().putBoolean("APP_WAS_STARTED", true).apply()

            val catalogsToInsert = catalogsFromFile()
            val notesToInsert = notesFromFile()

            GlobalScope.launch {
                catalogRepository.insert(catalogsToInsert)
                repository.insert(notesToInsert)
            }
        }
    }

    private fun notesFromFile(): List<Note> {
        val notesToInsert = assets.open("notes.csv")
            .bufferedReader()
            .readLines()
            .drop(1)
            .map {
                val (id, title, description, catalogId) = it.split(",")
                Note(id.toInt(), title, description, catalogId.toInt())
            }
        return notesToInsert

    }

    private fun catalogsFromFile(): List<Catalog> {
        val catalogsToInsert = assets.open("catalogs.csv")
            .bufferedReader()
            .readLines()
            .drop(1)
            .map{
                val(id, title) = it.split(",")
                Catalog(id.toInt(), title)
            }
        return catalogsToInsert
    }

}