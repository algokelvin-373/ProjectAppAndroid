package com.pjb.latihanpagination.ui.main

import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pjb.latihanpagination.R
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.databinding.ItemNoteBinding
import com.pjb.latihanpagination.helper.NoteDiffCallback
import com.pjb.latihanpagination.ui.insert.NoteUpdateActivity

class NoteAdapter(private val activity: Activity) : RecyclerView.Adapter<NoteAdapter.NoteViewHolder>() {

    private val listNotes = ArrayList<NoteEntity>()

    fun setNotes(notes: List<NoteEntity>) {
        val diffCallback = NoteDiffCallback(this.listNotes, notes)
        val diffResult = DiffUtil.calculateDiff(diffCallback)

        this.listNotes.clear()
        this.listNotes.addAll(notes)
        diffResult.dispatchUpdatesTo(this)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        holder.bind(listNotes[position])
    }

    override fun getItemCount(): Int = listNotes.size

    inner class NoteViewHolder(private val binding: ItemNoteBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(note: NoteEntity) {
            with(binding) {
                tvItemTitle.text = note.title
                tvItemDate.text = note.date
                tvItemDescription.text = note.description
                cvItemNote.setOnClickListener {
                    val intent = Intent(activity, NoteUpdateActivity::class.java)
                    intent.putExtra(NoteUpdateActivity.EXTRA_POSITION, adapterPosition)
                    intent.putExtra(NoteUpdateActivity.EXTRA_NOTE, note)
                    activity.startActivityForResult(intent, NoteUpdateActivity.REQUEST_UPDATE)
                }
            }
        }
    }

}