package com.pjb.latihanpagination.ui.main

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Intent
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.databinding.ItemNoteBinding
import com.pjb.latihanpagination.ui.insert.NoteUpdateActivity

class NotePagedListAdapter(private val activity: Activity) :
    PagedListAdapter<NoteEntity, NotePagedListAdapter.NoteViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding = ItemNoteBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        getItem(position)?.let { holder.bind(it) }
    }

    inner class NoteViewHolder(private val binding: ItemNoteBinding) :
        RecyclerView.ViewHolder(binding.root) {
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

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<NoteEntity> = object :
            DiffUtil.ItemCallback<NoteEntity>(){
            override fun areItemsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
                return oldItem.title == newItem.title && oldItem.description == newItem.description
            }
            @SuppressLint("DiffUtilEquals")
            override fun areContentsTheSame(oldItem: NoteEntity, newItem: NoteEntity): Boolean {
                return oldItem == newItem
            }
        }
    }
}