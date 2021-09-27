package com.pjb.latihanpagination.database

import androidx.paging.DataSource
import androidx.room.*
import androidx.sqlite.db.SupportSQLiteQuery

@Dao
interface NoteDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertNote(note: NoteEntity)

    @Update
    fun updateNote(note: NoteEntity)

    @Delete
    fun deleteNote(note: NoteEntity)

    @RawQuery(observedEntities = [NoteEntity::class])
    fun getAllNotes(query: SupportSQLiteQuery): DataSource.Factory<Int, NoteEntity>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insertAll(list: List<NoteEntity>)

}