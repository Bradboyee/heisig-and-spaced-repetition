package com.example.finalproject.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.example.finalproject.roomdatabase.roomentity.Story
import com.example.finalproject.viewmodel.SpacedViewModel
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class DeleteStoryDialog(private val story: Story) : DialogFragment() {
    private val spacedViewModel by viewModels<SpacedViewModel>()

    @SuppressLint("InflateParams", "CheckResult")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle("Delete")
            .setMessage("Story : ${story.story}.")
            .setPositiveButton("DELETE"
            ) { _, _ -> spacedViewModel.delete(story) }
            .setNegativeButton("LATER"
            ) { dialogInterface, _ -> dialogInterface.dismiss() }
            .show()
    }
}