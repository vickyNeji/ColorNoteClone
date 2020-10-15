package com.example.colornoteclone.database

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.io.Serializable

@Entity(tableName = "note")
data class Note(
    @ColumnInfo(name="note_title")
    var noteTitle:String,
    @ColumnInfo(name = "note_content")
    var noteContent:String,
    @ColumnInfo(name = "note_date")
    var date:String
):Serializable {     @PrimaryKey(autoGenerate = true)
        var id:Int?=null
}