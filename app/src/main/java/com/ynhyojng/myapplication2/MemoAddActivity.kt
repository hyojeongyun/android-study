package com.ynhyojng.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.ynhyojng.myapplication2.databinding.ActivityMemoAddBinding
import java.text.SimpleDateFormat
import java.util.*

class MemoAddActivity : AppCompatActivity() {

    lateinit var binding : ActivityMemoAddBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        // toolbar 설정
        setSupportActionBar(binding.memoAddToolbar)
        title = "Add Memo"

        // 이전 버튼 설정
        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        // thread
        Thread{
            SystemClock.sleep(300)
            runOnUiThread {
                // 자동 focus
                binding.addMemoSubject.requestFocus()
                // 키보드 올리기
                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.addMemoSubject, 0)
            }
        }.start()

    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.add_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            // 이전 버튼
            android.R.id.home -> {
                finish()
            }

            // 저장 버튼
            R.id.add_menu_write -> {
                // 사용자가 입력한 내용을 가지고 오기
                val memoSubject = binding.addMemoSubject.text
                val memoText = binding.addMemoText.text

                // 쿼리
                val sql = """
                    insert into MemoTable (memo_subject, memo_text, memo_date)
                    values(?, ?, ?)
                """.trimIndent()

                // db open
                val helper = DBHelper(this)

                // 현재 시간
                val sdf = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val now = sdf.format(Date())

                // 저장
                var arg1 = arrayOf(memoSubject, memoText, now)
                helper.writableDatabase.execSQL(sql, arg1)
                helper.writableDatabase.close()

                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}