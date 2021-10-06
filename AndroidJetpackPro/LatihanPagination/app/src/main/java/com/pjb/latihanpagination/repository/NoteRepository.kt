package com.pjb.latihanpagination.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.paging.DataSource
import com.pjb.latihanpagination.database.NoteDao
import com.pjb.latihanpagination.database.NoteDataBase
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.helper.SortUtils
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

class NoteRepository(application: Application) {

    private val noteDao: NoteDao
    private val executorService: ExecutorService = Executors.newSingleThreadExecutor()

    init {
        val db = NoteDataBase.getDataBase(application)
        noteDao = db.noteDao()
    }

    fun getAllNotes(sort: String): DataSource.Factory<Int, NoteEntity> {
        val query = SortUtils.getSortedQuery(sort)
        return noteDao.getAllNotes(query)
    }

    fun insertNote(note: NoteEntity) {
        executorService.execute{
            noteDao.insertNote(note)
        }
    }

    fun deleteNote(note: NoteEntity) {
        executorService.execute {
            noteDao.deleteNote(note)
        }
    }

    fun updateNote(note: NoteEntity) {
        executorService.execute {
            noteDao.updateNote(note)
        }
    }

}