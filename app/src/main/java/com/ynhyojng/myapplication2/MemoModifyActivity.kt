package com.ynhyojng.myapplication2

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
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