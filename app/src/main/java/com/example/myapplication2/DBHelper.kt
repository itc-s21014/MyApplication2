package com.example.myapplication2

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper(context: Context): SQLiteOpenHelper(context, "Userdata", null, 1) {
    override fun onCreate(p0: SQLiteDatabase?) {
        p0?.execSQL("create table Userdata (name TEXT primary key, contact TEXT)")
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        p0?.execSQL("drop table if exists Userdata")
    }

    fun saveuserdata(name: String, contact: String): Boolean {
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("name", name)
        cv.put("contact", contact)
        val result = p0.insert("Userdata", null, cv)
        if (result == -1 .toLong()){
            return false
        }
        return true
    }

    fun updateuserdata(name: String, contact: String): Boolean {
        val p0 = this.writableDatabase
        val cv = ContentValues()
        cv.put("contact", contact)
        val cursor: Cursor = p0.rawQuery("select * from Userdata where name = ?", arrayOf(name))
        if (cursor.count>0) {
            val result = p0.update("Userdata", cv, "name=?", arrayOf(name))
            return result != -1
        }
        else{
            return false
        }
    }

    fun deleteuserdata(name: String): Boolean {
        val p0 = this.writableDatabase
        val cursor: Cursor = p0.rawQuery("select * from Userdata where name = ?", arrayOf(name))
        if (cursor.count>0) {
            val result = p0.delete("Userdata", "name=?", arrayOf(name))
            return result != -1
        }
        else{
            return false
        }
    }

    fun gettext(): Cursor? {
        val p0 = this.writableDatabase
        val cursor = p0.rawQuery("select * from Userdata", null)
        return cursor
    }
}