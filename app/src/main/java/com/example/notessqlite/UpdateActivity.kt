package com.example.notessqlite

import android.os.Bundle
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.example.notessqlite.databinding.ActivityUpdateNoteBinding
import kotlin.math.log

class UpdateActivity : AppCompatActivity() {
    private lateinit var binding : ActivityUpdateNoteBinding
    private lateinit var db : NoteDatabaseHelper
    private var noteID : Int = -1


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateNoteBinding.inflate(layoutInflater)
        setContentView(binding.root)
        db = NoteDatabaseHelper(this)
        noteID = intent.getIntExtra("note_id" , -1)
        Toast.makeText(this , noteID.toString() , Toast.LENGTH_SHORT).show()
        if(noteID == -1){
            finish()
            return
        }
        val note = db.getNoteByID(noteID)
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)
        binding.updateSaveButton.setOnClickListener{
            val newtitle = binding.updateTitleEditText.text.toString()
            Toast.makeText(this , newtitle , Toast.LENGTH_SHORT).show()

            val newcontent = binding.updateContentEditText.text.toString()
            val status = "pending"
            val updateNote = Note(noteID , newtitle , newcontent, status)
            db.updateNote(updateNote)
            finish()
            Toast.makeText(this , "Saved" , Toast.LENGTH_SHORT).show()
            }

    }
}