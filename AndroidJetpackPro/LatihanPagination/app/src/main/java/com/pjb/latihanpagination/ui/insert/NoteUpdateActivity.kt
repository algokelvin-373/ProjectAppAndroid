package com.pjb.latihanpagination.ui.insert

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.lifecycle.ViewModelProvider
import com.pjb.latihanpagination.R
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.helper.DataHelper
import com.pjb.latihanpagination.helper.ViewModelFactory
import com.pjb.latihanpagination.databinding.ActivityNoteUpdateBinding

class NoteUpdateActivity : AppCompatActivity() {

    companion object {
        const val EXTRA_NOTE = ""
        const val EXTRA_POSITION = "extra_position"
        const val REQUEST_ADD = 100
        const val RESULT_ADD = 101
        const val REQUEST_UPDATE = 200
        const val RESULT_UPDATE = 201
        const val RESULT_DELETE = 301
        const val ALERT_DIALOG_CLOSE = 10
        const val ALERT_DIALOG_DELETE = 20
    }

    private var isEdit = false
    private var note: NoteEntity? = null
    private var position = 0

    private lateinit var noteUpdateViewModel: NoteUpdateViewModel

    private var _activityNoteUpdateBinding: ActivityNoteUpdateBinding? = null
    private val binding get() = _activityNoteUpdateBinding


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityNoteUpdateBinding = ActivityNoteUpdateBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        noteUpdateViewModel = obtainViewModel(this@NoteUpdateActivity)

        note = intent.getParcelableExtra(EXTRA_NOTE)
        if (note != null) {
            position = intent.getIntExtra(EXTRA_POSITION, 0)
            isEdit = true
        } else {
            note = NoteEntity()
        }

        val actionBarTitle: String
        val btnTitle: String

        if (isEdit) {
            actionBarTitle = getString(R.string.change)
            btnTitle = getString(R.string.update)
            if (note != null) {
                note?.let { note->
                    binding?.edtTitle?.setText(note.title)
                    binding?.edtDescription?.setText(note.description)
                }
            }
        } else {
            actionBarTitle = getString(R.string.add)
            btnTitle = getString(R.string.save)
        }

        supportActionBar?.title = actionBarTitle
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        binding?.btnSubmit?.text = btnTitle
        binding?.btnSubmit?.setOnClickListener {
            val title = binding?.edtTitle?.text.toString().trim()
            val description = binding?.edtDescription?.text.toString().trim()
            if (title.isEmpty()) {
                binding?.edtTitle?.error = getString(R.string.empty)
            } else if (description.isEmpty()) {
                binding?.edtDescription?.error = getString(R.string.empty)
            } else {
                note.let { note ->
                    note?.title = title
                    note?.description = description
                }

                val intent = Intent().apply {
                    putExtra(EXTRA_NOTE, note)
                    putExtra(EXTRA_POSITION, position)
                }

                if (isEdit) {
                    noteUpdateViewModel.updateNote(note as NoteEntity)
                    setResult(RESULT_UPDATE, intent)
                    finish()
                } else {
                    note.let { note ->
                        note?.date = DataHelper.getCurrentDate()
                    }
                    noteUpdateViewModel.insertNote(note as NoteEntity)
                    setResult(RESULT_ADD, intent)
                    finish()
                }
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityNoteUpdateBinding = null
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        if (isEdit) {
            menuInflater.inflate(R.menu.menu_form, menu)
        }
        return super.onCreateOptionsMenu(menu)
    }

    override fun onBackPressed() {
        super.onBackPressed()
        showAlertDialog(ALERT_DIALOG_CLOSE)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.action_delete -> showAlertDialog(ALERT_DIALOG_DELETE)
            android.R.id.home -> showAlertDialog(ALERT_DIALOG_CLOSE)
        }

        return super.onOptionsItemSelected(item)
    }

    private fun obtainViewModel(activity: AppCompatActivity): NoteUpdateViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(NoteUpdateViewModel::class.java)
    }

    private fun showAlertDialog(type: Int) {
        val isDialogClose = type == ALERT_DIALOG_CLOSE
        val dialogTitle: String
        val dialogMessage: String
        if (isDialogClose) {
            dialogTitle = getString(R.string.cancel)
            dialogMessage = getString(R.string.message_cancel)
        } else {
            dialogMessage = getString(R.string.message_delete)
            dialogTitle = getString(R.string.delete)
        }
        val alertDialogBuilder = AlertDialog.Builder(this)
        with(alertDialogBuilder) {
            setTitle(dialogTitle)
            setMessage(dialogMessage)
            setCancelable(false)
            setPositiveButton(getString(R.string.yes)) { _, _ ->
                if (!isDialogClose) {
                    noteUpdateViewModel.deleteNote(note as NoteEntity)

                    val intent = Intent()
                    intent.putExtra(EXTRA_POSITION, position)
                    setResult(RESULT_DELETE, intent)
                }

                finish()
            }
            setNegativeButton(getString(R.string.no)) { dialog, _ -> dialog.cancel() }
        }
        val alertDialog = alertDialogBuilder.create()
        alertDialog.show()
    }




}