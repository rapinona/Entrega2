package com.example.videogames.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

open class DbHelper(
    context: Context?,
) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object{
        private const val DATABASE_VERSION = 1
        private const val DATABASE_NAME = "videogames.db"
        const val TABLE_CAR = "car"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        db?.execSQL("CREATE TABLE $TABLE_CAR (id INTEGER PRIMARY KEY AUTOINCREMENT, marca TEXT NOT NULL, modelo TEXT NOT NULL, ano INT NOT NULL, color TEXT NOT NULL)")

    }

    override fun onUpgrade(db: SQLiteDatabase?, p1: Int, p2: Int) {
        db?.execSQL("DROP TABLE $TABLE_CAR")
        onCreate(db)
    }
}