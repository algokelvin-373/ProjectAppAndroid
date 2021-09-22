package com.pjb.latihanpagination.helper

import androidx.recyclerview.widget.DiffUtil
import com.pjb.latihanpagination.database.NoteEntity

class NoteDiffCallback(private val oldNoteList: List<NoteEntity>, private val newNoteList: List<NoteEntity>) : DiffUtil.Callback() {

    override fun getOldListSize(): Int = oldNoteList.size

    override fun getNewListSize(): Int = newNoteList.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        return oldNoteList[oldItemPosition].id == newNoteList[newItemPosition].id
    }

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldEmployee = oldNoteList[oldItemPosition]
        val newEmployee = newNoteList[newItemPosition]

        return oldEmployee.title == newEmployee.title && oldEmployee.description == newEmployee.description
    }
}