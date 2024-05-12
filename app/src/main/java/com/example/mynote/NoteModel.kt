package com.example.mynote

import android.icu.text.CaseMap.Title

class NoteModel {

    val id: Int
    val title: String
    val description: String

    // Constructor
    constructor(id: Int,  title:String, description: String) {
        this.id = id
        this.title = title
        this.description = description

    }

    override fun toString(): String {
        return "Title=$title\n" +
                "Description=$description\n"
    }
}