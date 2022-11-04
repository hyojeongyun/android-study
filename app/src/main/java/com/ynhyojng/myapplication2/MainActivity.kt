package com.ynhyojng.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.view.Menu
import android.view.MenuItem
import com.ynhyojng.myapplication2.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //splash delay
        SystemClock.sleep(1000)
        //splash 화면 이후 테마로 설정
        setTheme(R.style.Theme_MyApplication2)

        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //val helper = DBHelper(this)
        //helper.writableDatabase.close()

        // toolbar 설정
        setSupportActionBar(binding.mainToolbar)
        title = "Memo App"
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId) {
            R.id.main_menu_add -> {
                val memoAddIntent = Intent(this, MemoAddActivity::class.java)
                startActivity(memoAddIntent)
            }
        }

        return super.onOptionsItemSelected(item)
    }
}