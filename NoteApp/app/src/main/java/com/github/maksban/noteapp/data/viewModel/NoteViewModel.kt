package com.github.maksban.noteapp.data.viewModel

import androidx.lifecycle.*
import com.github.maksban.noteapp.data.NoteRepository
import com.github.maksban.noteapp.data.model.Note
import kotlinx.coroutines.launch

class NoteViewModel(private val noteRepository: NoteRepository): ViewModel() {

    val allNotes: LiveData<List<Note>> = noteRepository.allNotes.asLiveData()

    fun addNote(note: Note) = viewModelScope.launch {
        noteRepository.addNote(note)
    }

    fun updateNote(note: Note) = viewModelScope.launch {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: Note) = viewModelScope.launch {
        noteRepository.deleteNote(note)
    }

    fun deleteAllNotes() = viewModelScope.launch {
        noteRepository.deleteAllNotes()
    }

}

