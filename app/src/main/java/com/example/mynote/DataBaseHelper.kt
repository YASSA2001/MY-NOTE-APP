package com.example.mynote

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log

class DataBaseHelper(context: Context) : SQLiteOpenHelper(context, "note.db", null, 1) {

    companion object {
        const val NOTE_TABLE = "NOTE_TABLE"
        const val COLUMN_TITLE = "COLUMN_TITLE"
        const val COLUMN_DESCRIPTION = "COLUMN_DESCRIPTION"
        const val NOTE_ID = "ID"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL(
            "CREATE TABLE $NOTE_TABLE ($NOTE_ID INTEGER PRIMARY KEY AUTOINCREMENT, " +
                    "$COLUMN_TITLE TEXT, $COLUMN_DESCRIPTION TEXT)"
        )
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        Log.w(
            DataBaseHelper::class.java.simpleName,
            "Upgrading database from version $oldVersion to $newVersion, which will destroy all old data"
        )
        db?.execSQL("DROP TABLE IF EXISTS $NOTE_TABLE")
        onCreate(db)
    }

    fun addOne(noteModel: NoteModel): Boolean {
        val db = this.writableDatabase
        val cv = ContentValues()
        cv.put(COLUMN_TITLE, noteModel.title)
        cv.put(COLUMN_DESCRIPTION, noteModel.description)
        val insert: Long = try {
            db.insert(NOTE_TABLE, null, cv)
        } catch (e: Exception) {
            Log.e(DataBaseHelper::class.java.simpleName, "Error inserting data", e)
            -1
        } finally {
            db.close()
        }
        return insert != -1L
    }

    fun getEveryone(): List<NoteModel> {
        val returnList: MutableList<NoteModel> = ArrayList()
        // Get data from the database
        val queryString = "SELECT * FROM $NOTE_TABLE"
        val db = this.readableDatabase
        val cursor = db.rawQuery(queryString, null)
        if (cursor.moveToFirst()) {
            do {
                val noteID = cursor.getInt(0)
                val title = cursor.getString(1)
                val description = cursor.getString(2)

                val noteModel = NoteModel(noteID, title, description)

                returnList.add(noteModel)
            } while (cursor.moveToNext())
        }
        cursor.close()
        db.close()
        return returnList
    }

    fun deleteOne(noteModel: NoteModel): Boolean {
        val db = this.writableDatabase
        val queryString = "DELETE FROM $NOTE_TABLE WHERE $NOTE_ID = ?"
        val selectionArgs = arrayOf(noteModel.id.toString())
        val cursor = db.rawQuery(queryString, selectionArgs)

        val deleted = cursor.moveToFirst()
        cursor.close()
        db.close()

        return deleted
    }
}
