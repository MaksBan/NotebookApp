package com.github.maksban.noteapp.data

import com.github.maksban.noteapp.data.model.Note
import kotlinx.coroutines.flow.Flow


class NoteRepository(private val dao: NoteDao) {

    val allNotes: Flow<List<Note>> = dao.getAllNotes()


    suspend fun addNote(note: Note) {
        dao.addNote(note)
    }

    suspend fun updateNote(note: Note) {
        dao.updateNote(note)
    }

    suspend fun deleteNote(note: Note) {
        dao.deleteNote(note)
    }

    suspend fun deleteAllNotes() {
        dao.deleteAllNotes()
    }

    suspend fun insert(notesToInsert: List<Note>) {
        dao.insert(notesToInsert)

    }


}