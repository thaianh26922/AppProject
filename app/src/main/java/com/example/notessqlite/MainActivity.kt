package com.example.notessqlite

import android.content.Intent
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.ImageButton
import android.widget.PopupMenu
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.notessqlite.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private lateinit var db: NoteDatabaseHelper
    private lateinit var notesAdapter: NotesAdapter


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate((layoutInflater));
        setContentView(binding.root);
        db = NoteDatabaseHelper(this)
        notesAdapter = NotesAdapter(db.getAllNotes(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter
        binding.addButton.setOnClickListener{
            val intent = Intent(this , AddNoteActivity::class.java)
            startActivity(intent)
        }

        binding.filterButton.setOnClickListener { showFilterMenu(it) }
        val backButton = findViewById<ImageButton>(R.id.backButton)
        backButton.setOnClickListener {
            onBackPressed()
        }
    }

    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNotes())
    }
    private fun showFilterMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.menuInflater.inflate(R.menu.filter_menu, popup.menu)
        popup.setOnMenuItemClickListener { item -> onMenuItemClick(item) }
        popup.show()
    }

    private fun onMenuItemClick(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.filter_done -> {
                filterNotes("done")
                true
            }
            R.id.filter_pending -> {
                filterNotes("pending")
                true
            }
            else -> false
        }
    }

    private fun filterNotes(status: String) {
        notesAdapter.refreshData(db.getNotesByStatus(status))
    }

}