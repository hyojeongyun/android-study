package com.ynhyojng.myapplication2

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.SystemClock
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.view.View.OnClickListener
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ynhyojng.myapplication2.databinding.ActivityMainBinding
import com.ynhyojng.myapplication2.databinding.MainRecyclerRowBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    // 제목을 담을 arrayList
    val subjectList = ArrayList<String>()
    // 작성 날짜를 담을 arrayList
    val dateList = ArrayList<String>()
    // 메모 번호 담을 arrayList
    val idxList = ArrayList<Int>()

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

        // RecyclerView setting
        val mainRecyclerAdapter = MainRecyclerAdapter()
        binding.mainRecycler.adapter = mainRecyclerAdapter
        binding.mainRecycler.layoutManager = LinearLayoutManager(this)
    }

    override fun onResume() {
        super.onResume()

        // arrayList clear
        subjectList.clear()
        dateList.clear()
        idxList.clear()

        // 데이터베이스 오픈
        val helper = DBHelper(this)

        // query
        val sql = """
            select memo_subject, memo_date, memo_idx
            from MemoTable
            order by memo_idx desc
        """.trimIndent()

        val c1 = helper.writableDatabase.rawQuery(sql, null)

        while(c1.moveToNext()) {
            // column index
            val idx1 = c1.getColumnIndex("memo_subject")
            val idx2 = c1.getColumnIndex("memo_date")
            val idx3 = c1.getColumnIndex("memo_idx")

            // 데이터 가져오기
            val memoSubject = c1.getString(idx1)
            val memoDate = c1.getString(idx2)
            val memoIdx = c1.getInt(idx3)

            // 데이터를 담기
            subjectList.add(memoSubject)
            dateList.add(memoDate)
            idxList.add(memoIdx)

            // RecyclerView에게 갱신 요청
            binding.mainRecycler.adapter?.notifyDataSetChanged()
        }
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

    // RecyclerView 의 어댑터
    inner class MainRecyclerAdapter : RecyclerView.Adapter<MainRecyclerAdapter.ViewHolderClass>(){

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolderClass {
            val mainRecyclerBinding = MainRecyclerRowBinding.inflate(layoutInflater)
            val holder = ViewHolderClass(mainRecyclerBinding)

            // 생성되는 항목 View의 width, height
            val layoutParams = RecyclerView.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            mainRecyclerBinding.root.layoutParams = layoutParams

            // 항목 View에 이벤트 설정
            mainRecyclerBinding.root.setOnClickListener(holder)

            return holder
        }

        override fun onBindViewHolder(holder: ViewHolderClass, position: Int) {
            holder.rowMemoSubject.text = subjectList[position]
            holder.rowMemoDate.text = dateList[position]
        }

        override fun getItemCount(): Int {
            return subjectList.size
        }

        // HolderClass
        inner class ViewHolderClass(mainRecyclerBinding: MainRecyclerRowBinding) : RecyclerView.ViewHolder(mainRecyclerBinding.root), OnClickListener {
            // view의 주소값을 담음
            val rowMemoSubject = mainRecyclerBinding.memoSubject
            val rowMemoDate = mainRecyclerBinding.memoDate

            override fun onClick(p0: View?) {
                // Log.d("memo_app", "adapterPosition = $adapterPosition")

                val memoIdx = idxList[adapterPosition]

                // MemoReadActivity 실행
                val memoReadIntent = Intent(baseContext, MemoReadActivity::class.java)
                memoReadIntent.putExtra("memoIdx", memoIdx)
                startActivity(memoReadIntent)
            }
        }
    }
}