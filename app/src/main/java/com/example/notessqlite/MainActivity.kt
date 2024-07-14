package com.example.notessqlite

import android.content.Intent
import android.os.Bundle
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
        notesAdapter = NotesAdapter(db.getAllNoteNotes(), this)
        binding.notesRecyclerView.layoutManager = LinearLayoutManager(this)
        binding.notesRecyclerView.adapter = notesAdapter
        binding.addButton.setOnClickListener{
            val intent = Intent(this , AddNoteActivity::class.java)
            startActivity(intent)
        }



    }
    override fun onResume(){
        super.onResume()
        notesAdapter.refreshData(db.getAllNoteNotes())
    }

}