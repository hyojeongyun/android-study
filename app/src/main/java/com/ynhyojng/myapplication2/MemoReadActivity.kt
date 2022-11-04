package com.ynhyojng.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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

    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        when(item.itemId){
            android.R.id.home -> {
                finish()
            }
        }

        return super.onOptionsItemSelected(item)
    }
}