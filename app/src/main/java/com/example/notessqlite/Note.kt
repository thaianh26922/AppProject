package com.example.notessqlite

data class Note(val id: Int, val title: String, val content: String, var status: String)

//thêm cột status check done