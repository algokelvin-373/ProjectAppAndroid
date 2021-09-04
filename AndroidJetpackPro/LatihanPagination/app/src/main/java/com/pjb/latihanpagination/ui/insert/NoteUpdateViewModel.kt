package com.pjb.latihanpagination.ui.insert

import android.app.Application
import androidx.lifecycle.ViewModel
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.repository.NoteRepository

class NoteUpdateViewModel(application: Application) : ViewModel() {

   private val noteRepository: NoteRepository = NoteRepository(application)

    fun insertNote(note: NoteEntity) {
        noteRepository.insertNote(note)
    }

    fun updateNote(note: NoteEntity) {
        noteRepository.updateNote(note)
    }

    fun deleteNote(note: NoteEntity) {
        noteRepository.deleteNote(note)
    }



}