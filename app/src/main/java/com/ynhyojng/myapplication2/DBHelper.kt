package com.ynhyojng.myapplication2

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DBHelper :SQLiteOpenHelper {
    // 생성자에서 DB이름 정해줌
    constructor(context:Context) : super(context, "Memo.db", null, 1)

    // DB 파일 생성될 때 자동 호출되는 메서드
    // 테이블 생성
    override fun onCreate(p0: SQLiteDatabase?) {
        val sql = """
            create table MemoTable
            (memo_idx integer primary key autoincrement,
            memo_subject text not null,
            memo_text text not null,
            memo_date date not null)
        """.trimIndent()

        p0?.execSQL(sql)
    }

    override fun onUpgrade(p0: SQLiteDatabase?, p1: Int, p2: Int) {
        TODO("Not yet implemented")
    }
}