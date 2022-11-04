package com.ynhyojng.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
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

        val helper = DBHelper(this)
        helper.writableDatabase.close()
    }
}