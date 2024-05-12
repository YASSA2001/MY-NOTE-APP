package com.example.mynote

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.*

class AddNote : AppCompatActivity() {

    private lateinit var saveButton: Button
    private lateinit var viewButton: Button
    private lateinit var titleEditText: EditText
    private lateinit var noteEditText: EditText
    private lateinit var listView: ListView
    private lateinit var dataBaseHelper: DataBaseHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_note)

        saveButton = findViewById(R.id.addButton)
        viewButton = findViewById(R.id.viewButton)
        titleEditText = findViewById(R.id.title)
        noteEditText = findViewById(R.id.note)
        listView = findViewById(R.id.ItemList)
        dataBaseHelper = DataBaseHelper(this)

        saveButton.setOnClickListener {
            val titleText = titleEditText.text.toString()
            val noteText = noteEditText.text.toString()
            val noteModel = NoteModel(-1, titleText, noteText)
            val success = dataBaseHelper.addOne(noteModel)
            if (success) {
                Toast.makeText(this, " saved successfully", Toast.LENGTH_SHORT).show()
                titleEditText.text.clear()
                noteEditText.text.clear()
            } else {
                Toast.makeText(this, "Error saving note", Toast.LENGTH_SHORT).show()
            }
        }

        viewButton.setOnClickListener {
            displayNotes()
        }

        listView.setOnItemClickListener { _, _, position, _ ->
            val clickedNote = listView.getItemAtPosition(position) as NoteModel
            val isDeleted = dataBaseHelper.deleteOne(clickedNote)
            if (isDeleted) {
                displayNotes()
                Toast.makeText(this, " deleted successful", Toast.LENGTH_SHORT).show()
            } else {
                Toast.makeText(this, "Failed to delete note", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun displayNotes() {
        val noteList = dataBaseHelper.getEveryone()
        val noteArrayAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, noteList)
        listView.adapter = noteArrayAdapter
    }
}
