package com.example.finalproject.fragment.spaced

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.GridLayoutManager
import com.example.finalproject.QuizActivity
import com.example.finalproject.databinding.FragmentSpacedBinding
import com.example.finalproject.roomdatabase.KanjiDatabase
import com.example.finalproject.roomdatabase.KanjiRepository
import com.example.finalproject.viewmodel.KanjiViewModel
import com.example.finalproject.viewmodel.KanjiViewModelFactory
import com.example.finalproject.utils.SpacedRecyclerAdapter


class SpacedFragment : Fragment() {
    private var _binding : FragmentSpacedBinding? = null
    private lateinit var kanjiViewModel: KanjiViewModel
    private val binding get() = _binding!!


    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle? ): View {
        _binding = FragmentSpacedBinding.inflate(inflater,container,false)
        val dao = KanjiDatabase.getInstance(requireContext()).dao()
        val repository = KanjiRepository(dao)
        val factory = KanjiViewModelFactory(repository)
        kanjiViewModel = ViewModelProvider(this,factory)[KanjiViewModel::class.java]
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        btn()
    }

    private fun btn() {
        val startForResult = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result: ActivityResult ->
            if (result.resultCode == Activity.RESULT_OK) {
                // Handle the Intent
                val bundle = result.data!!.getBundleExtra("correctAnswer")
                val wrongBundle = result.data!!.getBundleExtra("wrongAnswer")
                val correctData = bundle?.getStringArrayList("correctBundle")
                val wrongData = wrongBundle?.getStringArrayList("wrongBundle")
                Log.i("correct",correctData.toString())
                kanjiViewModel.updateCorrectByList(correctData!!)
                Log.i("wrong",wrongData.toString())
                kanjiViewModel.updateWrongByList(wrongData!!)
            }
        }
        kanjiViewModel.spacedKanji.observe(viewLifecycleOwner){ data ->
            val showData = data.map { it.kanji }
            binding.deleteDatabaseBtn.setOnClickListener {
                if (showData.isEmpty()){
                    Toast.makeText(this.requireContext(),"null",Toast.LENGTH_SHORT).show()
                }
                else{
                    Toast.makeText(this.requireContext(),showData.toString(),Toast.LENGTH_SHORT).show()
                }
            }
            binding.floatingActionButton2.setOnClickListener {
                if (showData.isEmpty()){
                    Toast.makeText(this.requireContext(),"You don't have todo Today!!",Toast.LENGTH_SHORT).show()
                }
                else{
                    val intent = Intent(this.context,QuizActivity::class.java)
                    startForResult.launch(intent)
                }
            }
        }
    }

    private fun getData() {
        binding.spacedRecyclerview.layoutManager = GridLayoutManager(this.context,5)
        binding.spacedRecyclerview1day.layoutManager = GridLayoutManager(this.context,5)
        binding.spacedRecyclerview3days.layoutManager = GridLayoutManager(this.context,5)
        binding.spacedRecyclerview1week.layoutManager = GridLayoutManager(this.context,5)
        binding.spacedRecyclerview1month.layoutManager = GridLayoutManager(this.context,5)
        binding.spacedRecyclerview6month.layoutManager = GridLayoutManager(this.context,5)
        val adapterSpaced = SpacedRecyclerAdapter()
        val adapterSpaced1day = SpacedRecyclerAdapter()
        val adapterSpaced3days = SpacedRecyclerAdapter()
        val adapterSpaced1week = SpacedRecyclerAdapter()
        val adapterSpaced1month = SpacedRecyclerAdapter()
        val adapterSpaced6month = SpacedRecyclerAdapter()
        binding.spacedRecyclerview.adapter = adapterSpaced
        binding.spacedRecyclerview1day.adapter = adapterSpaced1day
        binding.spacedRecyclerview3days.adapter = adapterSpaced3days
        binding.spacedRecyclerview1week.adapter = adapterSpaced1week
        binding.spacedRecyclerview1month.adapter = adapterSpaced1month
        binding.spacedRecyclerview6month.adapter = adapterSpaced6month

        kanjiViewModel.kanjiList.observe(this.viewLifecycleOwner) {
                items -> items.let { data ->
            adapterSpaced.submitList(data.filter { it.spacedStatus == 0 })
            adapterSpaced1day.submitList(data.filter { it.spacedStatus == 1 })
            adapterSpaced3days.submitList(data.filter { it.spacedStatus == 2 })
            adapterSpaced1week.submitList(data.filter { it.spacedStatus == 3 })
            adapterSpaced1month.submitList(data.filter { it.spacedStatus == 4 })
            adapterSpaced6month.submitList(data.filter { it.spacedStatus == 5 })
            //reassign
            binding.spacedRecyclerview.adapter = adapterSpaced
            binding.spacedRecyclerview1day.adapter = adapterSpaced1day
            binding.spacedRecyclerview3days.adapter = adapterSpaced3days
            binding.spacedRecyclerview1week.adapter = adapterSpaced1week
            binding.spacedRecyclerview1month.adapter = adapterSpaced1month
            binding.spacedRecyclerview6month.adapter = adapterSpaced6month
        }
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}
