package com.example.notessqlite

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, private val context: Context) : RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {
    private val db: NoteDatabaseHelper = NoteDatabaseHelper(context)

    // ViewHolder class
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextView)
        val contentTextView: TextView = itemView.findViewById(R.id.contentTextView)
        val updateButton: ImageView = itemView.findViewById(R.id.editButton)
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteButton)
        val ringButton: ImageView = itemView.findViewById(R.id.ringButton)
        val cardView: CardView = itemView.findViewById(R.id.cardView)
        val doneButton: ImageView = itemView.findViewById(R.id.doneButton)
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

        // Check status and update card background color
        if (note.status == "done") {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_done_color))
        } else {
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_default_color))
        }

        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.ringButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, Alarm::class.java).apply {
                putExtra("note_id", note.id)
            }
            holder.itemView.context.startActivity(intent)
        }

        holder.deleteButton.setOnClickListener {
            db.deleteNoteById(note.id)
            refreshData(db.getAllNotes())
        }

        holder.doneButton.setOnClickListener {
            db.updateNoteStatus(note.id, "done")
            note.status = "done"

            // Change background when done
            holder.cardView.setCardBackgroundColor(ContextCompat.getColor(context, R.color.card_done_color))
            Toast.makeText(context, "Has completed the job", Toast.LENGTH_SHORT).show()

            // Refresh data
            refreshData(db.getAllNotes())
        }
    }

    // Updates the data and refreshes the RecyclerView
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes
        notifyDataSetChanged()
    }
}
