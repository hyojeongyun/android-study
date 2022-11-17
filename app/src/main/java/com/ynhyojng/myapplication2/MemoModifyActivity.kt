package com.ynhyojng.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import android.view.inputmethod.InputMethodManager
import com.ynhyojng.myapplication2.databinding.ActivityMemoModifyBinding

class MemoModifyActivity : AppCompatActivity() {

    lateinit var binding : ActivityMemoModifyBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMemoModifyBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setSupportActionBar(binding.memoModifyToolbar)
        title = "Edit Memo"

        supportActionBar?.setHomeButtonEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        val helper = DBHelper(this)

        val sql = """
            select memo_subject, memo_text
            from MemoTable
            where memo_idx = ?
        """.trimIndent()

        val memoIdx = intent.getIntExtra("memoIdx", 0)

        var args = arrayOf(memoIdx.toString())
        val c1 = helper.writableDatabase.rawQuery(sql, args)
        c1.moveToNext()

        val idx1 = c1.getColumnIndex("memo_subject")
        val idx2 = c1.getColumnIndex("memo_text")

        val memoSubject = c1.getString(idx1)
        val memoText = c1.getString(idx2)

        helper.writableDatabase.close()

        binding.memoModifySubject.setText(memoSubject)
        binding.memoModifyText.setText(memoText)

        Thread {
            SystemClock.sleep(500)

            runOnUiThread {
                binding.memoModifySubject.requestFocus()

                val imm = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
                imm.showSoftInput(binding.memoModifySubject, InputMethodManager.SHOW_IMPLICIT)
            }
        }.start()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.modify_menu, menu)

        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
            // 저장
            R.id.memo_modify_save -> {
                val helper  = DBHelper(this)

                val sql = """
                    update MemoTable
                    set memo_subject = ?, memo_text = ?
                    where memo_idx = ?
                """.trimIndent()

                val memoSubject = binding.memoModifySubject.text
                val memoText = binding.memoModifyText.text
                val memoIdx = intent.getIntExtra("memoIdx", 0)

                val args = arrayOf(memoSubject, memoText, memoIdx)

                helper.writableDatabase.execSQL(sql, args)
                helper.writableDatabase.close()

                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}