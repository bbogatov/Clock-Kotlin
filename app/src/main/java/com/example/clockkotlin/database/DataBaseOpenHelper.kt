package com.example.clockkotlin.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.example.clockkotlin.R

/**
 * Class that helps creating local database
 */
class DataBaseOpenHelper(context: Context, dbName: String, factory: SQLiteDatabase.CursorFactory?, version: Int) :
    SQLiteOpenHelper(context, dbName, factory, version) {

    val dbName = dbName

    override fun onCreate(db: SQLiteDatabase) {

        Log.d(R.string.data_base_log.toString(), "Create database")

        db.execSQL(
            "create table $dbName ("
                    + "id integer primary key autoincrement,"
                    + "time text,"
                    + "enable boolean" + ");"
        )

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

    }
}