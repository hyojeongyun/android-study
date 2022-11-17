package com.ynhyojng.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import com.ynhyojng.myapplication2.databinding.ActivityMemoReadBinding

class MemoReadActivity : AppCompatActivity() {

    lateinit var binding : ActivityMemoReadBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoReadBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.memoReadToolbar)
        title = "Memo"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onResume() {
        super.onResume()

        // 데이터베이스 오픈
        val helper = DBHelper(this)

        // query
        val sql = """
            select memo_subject, memo_date, memo_text
            from MemoTable
            where memo_idx = ?
        """.trimIndent()

        // 글 번호 추출
        val memoIdx = intent.getIntExtra("memoIdx", 0)

        // 쿼리실행
        val args = arrayOf(memoIdx.toString())
        val c1 = helper.writableDatabase.rawQuery(sql, args)
        c1.moveToNext()

        // 데이터 가져옴
        val idx1 = c1.getColumnIndex("memo_subject")
        val idx2 = c1.getColumnIndex("memo_date")
        val idx3 = c1.getColumnIndex("memo_text")

        val memoSubject = c1.getString(idx1)
        val memoDate = c1.getString(idx2)
        val memoText = c1.getString(idx3)

        helper.writableDatabase.close()

        binding.memoReadSubject.text = memoSubject
        binding.memoReadDate.text = memoDate
        binding.memoReadText.text = memoText
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.read_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
            // 메뉴 수정
            R.id.read_modify -> {
                var memoModifyIntent = Intent(this, MemoModifyActivity::class.java)

                val memoIdx = intent.getIntExtra("memoIdx", 0)
                memoModifyIntent.putExtra("memoIdx", memoIdx)

                startActivity(memoModifyIntent)
            }
            // 메뉴 삭제
            R.id.read_delete -> {
                var builder = AlertDialog.Builder(this)

                builder.setTitle("메모 삭제")
                builder.setMessage("메모를 삭제하시겠습니까?")

                builder.setPositiveButton("삭제"){ dialogInterface, i ->
                    val helper = DBHelper(this)

                    val sql = """
                        delete from MemoTable
                        where memo_idx = ?
                    """.trimIndent()

                    val memoIdx = intent.getIntExtra("memoIdx", 0)

                    val args = arrayOf(memoIdx.toString())
                    helper.writableDatabase.execSQL(sql, args)
                    helper.writableDatabase.close()

                    finish()
                }
                builder.setNegativeButton("취소", null)

                builder.show()
            }
        }

       return super.onOptionsItemSelected(item)
    }
}