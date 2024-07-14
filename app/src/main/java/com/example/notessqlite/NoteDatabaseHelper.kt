package com.example.notessqlite

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class NoteDatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        private const val DATABASE_NAME = "notesapp.db"
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes"
        private const val COLUMN_ID = "id"
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
        private const val COLUMN_STATUS = "status"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT, $COLUMN_STATUS TEXT)"
        db?.execSQL(createTableQuery)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    fun insertNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_STATUS, note.status)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getAllNotes(): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME"
        val cursor = db.rawQuery(query, null)
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))

            val note = Note(id, title, content, status)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun updateNote(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_STATUS, note.status)
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString())
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close()
    }

    fun deleteNoteById(noteId: Int) {
        val db = writableDatabase
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(noteId.toString())
        db.delete(TABLE_NAME, whereClause, whereArgs)
        db.close()
    }

    fun getNoteById(noteId: Int): Note {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId"
        val cursor = db.rawQuery(query, null)

        var id = -1
        var title = ""
        var content = ""
        var status = ""

        if (cursor.moveToFirst()) {
            id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
        }

        cursor.close()
        db.close()

        return Note(id, title, content, status)
    }

    fun updateNoteStatus(noteId: Int, status: String) {
        val db = writableDatabase
        val contentValues = ContentValues().apply {
            put(COLUMN_STATUS, status)
        }
        db.update(TABLE_NAME, contentValues, "$COLUMN_ID=?", arrayOf(noteId.toString()))
        db.close()
    }

    fun getNotesByStatus(status: String): List<Note> {
        val notesList = mutableListOf<Note>()
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_STATUS = ?"
        val cursor = db.rawQuery(query, arrayOf(status))
        while (cursor.moveToNext()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))

            val note = Note(id, title, content, status)
            notesList.add(note)
        }
        cursor.close()
        db.close()
        return notesList
    }

    fun insertNode(note: Note) {
        val db = writableDatabase
        val values = ContentValues().apply {
            put(COLUMN_TITLE, note.title)
            put(COLUMN_CONTENT, note.content)
            put(COLUMN_STATUS, note.status)
        }
        db.insert(TABLE_NAME, null, values)
        db.close()
    }

    fun getNoteByID(noteID: Int): Note? {
        val db = readableDatabase
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = ?"
        val cursor = db.rawQuery(query, arrayOf(noteID.toString()))

        var note: Note? = null
        if (cursor.moveToFirst()) {
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID))
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE))
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT))
            val status = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_STATUS))
            note = Note(id, title, content, status)
        }

        cursor.close()
        db.close()

        return note
    }

}
