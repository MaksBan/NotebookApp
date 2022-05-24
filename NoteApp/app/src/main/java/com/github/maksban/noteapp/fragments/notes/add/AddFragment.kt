package com.github.maksban.noteapp.fragments.notes.add

import android.os.Bundle
import android.text.TextUtils
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import com.github.maksban.noteapp.App
import com.github.maksban.noteapp.R
import com.github.maksban.noteapp.data.model.Note
import com.github.maksban.noteapp.data.viewModel.NoteViewModel
import com.github.maksban.noteapp.data.viewModel.NoteViewModelFactory
import com.github.maksban.noteapp.databinding.FragmentAddBinding

class AddFragment : Fragment(R.layout.fragment_add) {

    private lateinit var binding: FragmentAddBinding
    private val viewModel: NoteViewModel by activityViewModels{
        NoteViewModelFactory(
            (activity?.application as App).repository
        )
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentAddBinding.bind(view)

        binding.btnSaveNote.setOnClickListener {
            insertDataToDatabase()

        }
    }

    private fun insertDataToDatabase() {
        val title = binding.edTitle.text.toString()
        val description = binding.edDescription.text.toString()

        if (inputCheck(title, description)) {
            // make note object
            val note = Note(0, title, description)
            // add note to database
            viewModel.addNote(note)
            Toast.makeText(requireContext(), "Note successfully added.", Toast.LENGTH_LONG).show()
            //navigate back to notes fragment
            findNavController().popBackStack()
        } else{
            Toast.makeText(requireContext(), "Fill out all fields", Toast.LENGTH_LONG).show()
        }
    }

    private fun inputCheck(title: String, description: String): Boolean {
        return (!TextUtils.isEmpty(title) && !TextUtils.isEmpty(description))
    }

}