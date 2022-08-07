package com.example.finalproject

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.finalproject.databinding.ActivityMainBinding
import com.example.finalproject.fragment.HomeFragment
import com.example.finalproject.fragment.SpacedFragment
import com.example.finalproject.fragment.StatFragment
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.ui.KanjiViewModel
import com.example.finalproject.ui.KanjiViewModelFactory

class MainActivity : AppCompatActivity(){
    private lateinit var binding: ActivityMainBinding
    private lateinit var kanjiViewModel: KanjiViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        replaceFragment(HomeFragment())
        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId){
                R.id.nav_home -> replaceFragment(HomeFragment())
                R.id.nav_spaced -> replaceFragment(SpacedFragment())
                R.id.nav_static -> replaceFragment(StatFragment())
                else ->{}
            }
            true
        }
        //startClicked
        binding.bottomNavigationView.selectedItemId = R.id.nav_home
        val dao = KanjiDatabase.getInstance(this).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
        val badgeValue = 0
//        binding.textView5.text = badgeValue.toString()
        kanjiViewModel.spacedKanji.observe(this){
//            binding.textView5.text = data.size.toString()
        }
        val badge = binding.bottomNavigationView.getOrCreateBadge(R.id.nav_spaced)
        //badge
        if(badgeValue>0){
            badge.isVisible = true
            badge.number = badgeValue
        }else{
            badge.isVisible = false
            badge.clearNumber()
        }
    }

    private fun replaceFragment(fragment : Fragment){
        val fragmentManager = supportFragmentManager
        val fragmentTransaction = fragmentManager.beginTransaction()
        fragmentTransaction.replace(R.id.frameLayout,fragment)
        fragmentTransaction.commit()
    }

}
