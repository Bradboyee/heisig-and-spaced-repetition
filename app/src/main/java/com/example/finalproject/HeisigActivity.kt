package com.example.finalproject

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.data_kanji.Kanji
import com.example.finalproject.databinding.ActivityHeisigBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiEntity
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory

class HeisigActivity:AppCompatActivity() {
    lateinit var binding : ActivityHeisigBinding
    lateinit var kanjiViewModel: KanjiViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_heisig)
        initUI()
    }


    private fun initUI() {
        val dao = KanjiDatabase.getInstance(this).dao() // dao() is in Kanjidatabase that implement from Dao
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]

        //
        val selectedkanji = intent.getParcelableExtra<Kanji>("selectedKanji")
        binding.kanjitextview.text = selectedkanji?.Kanji
        binding.JLPT.text = selectedkanji?.JLPT.toString()


        binding.addbutton.setOnClickListener {
            val addkanjiobject = KanjiEntity(0,selectedkanji!!.Kanji,selectedkanji!!.JLPT)
            Log.i("item",addkanjiobject.toString())
            if (addkanjiobject != null) {
                kanjiViewModel.insert(addkanjiobject)
            }
            Toast.makeText(this,"added",Toast.LENGTH_SHORT).show()
        }
    }


}