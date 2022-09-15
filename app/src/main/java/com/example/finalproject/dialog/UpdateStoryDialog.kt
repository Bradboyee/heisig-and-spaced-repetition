package com.example.finalproject.dialog

import android.annotation.SuppressLint
import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.viewModels
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.input.input
import com.example.finalproject.roomdatabase.roomentity.Story
import com.example.finalproject.viewmodel.SpacedViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class UpdateStoryDialog(private val story: Story) : DialogFragment() {
    private val spacedViewModel by viewModels<SpacedViewModel>()

    @SuppressLint("CheckResult")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialDialog(requireContext()).show {
            input(hint = "story :",
                waitForPositiveButton = true,
                prefill = story.story) { dialog, text ->
                val updateStory = story.copy(story = text.toString())
                spacedViewModel.updateStory(updateStory)
                dialog.dismiss()
            }
            positiveButton(text = "UPDATE")
            negativeButton(text = "LATER")
        }
    }
}