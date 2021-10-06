package com.pjb.latihanpagination.ui.main

import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.google.android.material.snackbar.Snackbar
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.paging.PagedList
import androidx.recyclerview.widget.LinearLayoutManager
import com.pjb.latihanpagination.R
import com.pjb.latihanpagination.database.NoteEntity
import com.pjb.latihanpagination.databinding.ActivityMainBinding
import com.pjb.latihanpagination.helper.SortUtils
import com.pjb.latihanpagination.helper.ViewModelFactory
import com.pjb.latihanpagination.ui.insert.NoteUpdateActivity

class MainActivity : AppCompatActivity() {
    private lateinit var noteAdapter: NoteAdapter
    private lateinit var notePagedAdapter: NotePagedListAdapter

    private var _activityMainBinding: ActivityMainBinding? = null
    private val binding get() = _activityMainBinding

    private lateinit var mainVM: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        mainVM = obtainViewModel(this)
        mainVM.getAllNotes(SortUtils.NEWEST).observe(this, noteObserver)

        noteAdapter = NoteAdapter(this)
        notePagedAdapter = NotePagedListAdapter(this)

        binding?.rvNotes?.layoutManager = LinearLayoutManager(this)
        binding?.rvNotes?.setHasFixedSize(true)
        binding?.rvNotes?.adapter = notePagedAdapter

        binding?.fab?.setOnClickListener { view ->
            if (view.id == R.id.fab) {
                val intent = Intent(this@MainActivity, NoteUpdateActivity::class.java)
                startActivityForResult(intent, NoteUpdateActivity.REQUEST_ADD)
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.sort_menu, menu)
        return super.onCreateOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        var sort = ""
        when (item.itemId) {
            R.id.action_newest -> sort = SortUtils.NEWEST
            R.id.action_oldest -> sort = SortUtils.OLDEST
            R.id.action_random -> sort = SortUtils.RANDOM
        }

        mainVM.getAllNotes(sort).observe(this, noteObserver)
        item.isChecked = true
        return super.onOptionsItemSelected(item)

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (data != null) {
            if (requestCode == NoteUpdateActivity.REQUEST_ADD){
                if (resultCode == NoteUpdateActivity.RESULT_ADD){
                    showSnackBarMessage(getString(R.string.added))
                }
            }else if (requestCode == NoteUpdateActivity.REQUEST_UPDATE) {
                if (resultCode == NoteUpdateActivity.RESULT_UPDATE) {
                    showSnackBarMessage(getString(R.string.changed))
                }else if (resultCode == NoteUpdateActivity.RESULT_DELETE) {
                    showSnackBarMessage(getString(R.string.deleted))
                }
            }
        }
    }

    private fun showSnackBarMessage(message: String) {
        Snackbar.make(binding?.root as View, message, Snackbar.LENGTH_SHORT).show()
    }

    private fun obtainViewModel(activity: AppCompatActivity): MainViewModel {
        val factory = ViewModelFactory.getInstance(activity.application)
        return ViewModelProvider(activity, factory).get(MainViewModel::class.java)
    }

    private val noteObserver = Observer<PagedList<NoteEntity>> {
        if (it != null) {
            notePagedAdapter.submitList(it)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _activityMainBinding = null
    }

}