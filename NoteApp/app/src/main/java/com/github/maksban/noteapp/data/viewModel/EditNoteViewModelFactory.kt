package com.github.maksban.noteapp.data.viewModel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.github.maksban.noteapp.data.CatalogRepository
import com.github.maksban.noteapp.data.NoteRepository

class EditNoteViewModelFactory(
    private val noteRepository: NoteRepository,
    private val catalogRepository: CatalogRepository,
) : ViewModelProvider.Factory {
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(EditNoteViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return EditNoteViewModel(noteRepository, catalogRepository) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}