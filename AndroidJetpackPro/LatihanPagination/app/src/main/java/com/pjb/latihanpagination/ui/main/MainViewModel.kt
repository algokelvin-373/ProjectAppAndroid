package com.pjb.latihanpagination.ui.main

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import androidx.paging.LivePagedListBuilder
import androidx.paging.PagedList
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.repository.NoteRepository

class MainViewModel(application: Application) : ViewModel() {

    private val noteRepository: NoteRepository = NoteRepository(application)

    fun getAllNotes(sort: String): LiveData<PagedList<NoteEntity>> =
        LivePagedListBuilder(noteRepository.getAllNotes(sort), 5).build()

}