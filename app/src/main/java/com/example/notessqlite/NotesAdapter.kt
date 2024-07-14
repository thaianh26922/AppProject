package com.example.notessqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, private val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private  var db : NoteDatabaseHelper = NoteDatabaseHelper(context)

    // ViewHolder class
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButon: ImageView= itemView.findViewById(R.id.editButton)
        val DeleteButon: ImageView= itemView.findViewById(R.id.deleteButton)
        val RingButton: ImageView= itemView.findViewById(R.id.ringButton)

    }

    // Inflates the item layout and creates the ViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view)
    }

    // Returns the size of the dataset
    override fun getItemCount(): Int {
        return notes.size       
    }

    // Binds data to the item at the specified position
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title
        holder.contentTextView.text = note.content
        holder.updateButon.setOnClickListener{
            val intent = Intent(holder.itemView.context , UpdateActivity::class.java).apply {
                putExtra("note_id" , note.id)
            }
            holder.itemView.context.startActivity(intent)

        }
        holder.RingButton.setOnClickListener{
            val intent = Intent(holder.itemView.context , Alarm::class.java).apply {
                putExtra("note_id" , note.id)
            }
            holder.itemView.context.startActivity(intent)

        }
        holder.DeleteButon.setOnClickListener{
            db.deleteNoteById(note.id)
            refreshData(db.getAllNoteNotes())


        }
    }

    // Updates the data and refreshes the RecyclerView
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
