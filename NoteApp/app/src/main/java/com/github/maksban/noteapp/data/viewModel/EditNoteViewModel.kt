package com.github.maksban.noteapp.data.viewModel

import androidx.lifecycle.*
import com.github.maksban.noteapp.data.CatalogRepository
import com.github.maksban.noteapp.data.NoteRepository
import com.github.maksban.noteapp.data.model.Catalog
import com.github.maksban.noteapp.data.model.Note
import kotlinx.coroutines.launch

class EditNoteViewModel(
    private val noteRepository: NoteRepository,
    private val catalogRepository: CatalogRepository,
) : ViewModel() {

    private val mutableLiveData = MutableLiveData<Note>(null)
    val editNoteLiveData: LiveData<Note> = mutableLiveData
    val allCatalogs: LiveData<List<Catalog>> = catalogRepository.allCatalogs.asLiveData()

    fun initialize(noteToEdit:Note){
        mutableLiveData.value = noteToEdit
    }

    fun noteDataConfirmed(title: String, description: String) {
        val newNote = editNoteLiveData.value!!.copy(
            title = title,
            description = description,
        )
        mutableLiveData.value = newNote
    }

    fun catalogChanged(catalog: Catalog?) {
        val newNote = editNoteLiveData.value!!.copy(
            catalogId = catalog?.id ?: 0
        )
        mutableLiveData.value = newNote

    }

    fun confirmUpdate() = viewModelScope.launch {
        noteRepository.updateNote(mutableLiveData.value!!)
    }




}

