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
class InsertStoryDialog(private val character: String) : DialogFragment() {
    private val spacedViewModel by viewModels<SpacedViewModel>()

    @SuppressLint("CheckResult")
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialDialog(requireContext()).show {
            input(hint = "story :", waitForPositiveButton = true) { dialog, text ->
                val story = Story(0, character, text.toString())
                    spacedViewModel.insertStory(story)
                dialog.dismiss()
            }
            positiveButton(text = "SUBMIT")
            negativeButton(text = "LATER")
        }
    }
}