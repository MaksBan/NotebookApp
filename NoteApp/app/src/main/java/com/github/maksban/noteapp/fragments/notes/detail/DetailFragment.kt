package com.github.maksban.noteapp.fragments.notes.detail

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.View
import android.widget.Toast
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.github.maksban.noteapp.App
import com.github.maksban.noteapp.R
import com.github.maksban.noteapp.data.viewModel.EditNoteViewModel
import com.github.maksban.noteapp.databinding.FragmentDetailBinding
import com.github.maksban.noteapp.fragments.dialog.FragmentDialog

class DetailFragment : Fragment(R.layout.fragment_detail) {
    private lateinit var binding: FragmentDetailBinding
    private val args by navArgs<DetailFragmentArgs>()

    private val viewModel: EditNoteViewModel by activityViewModels {
        (activity?.application as App).editNoteViewModelFactory

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentDetailBinding.bind(view)
        val noteToEdit = args.currentNote
        viewModel.initialize(noteToEdit)

        setDataToEditText()

//        viewModel.editNoteLiveData.observe(viewLifecycleOwner) {
//            println(it)
//        }


        // obsluga przyciskow
        handleBtnClick()
        handleMenuClick()



    }

    private fun setDataToEditText() {
        binding.apply {
            edTitle.setText(args.currentNote.title)
            edDescription.setText(args.currentNote.description)
        }
    }

    private fun uploadDataToDatabase() {
        val title = binding.edTitle.text.toString()
        val description = binding.edDescription.text.toString()

        viewModel.noteDataConfirmed(title, description)
        viewModel.confirmUpdate()
    }

    private fun handleBtnClick() {
        binding.btnEditNote.setOnClickListener {
            uploadDataToDatabase()
            findNavController().popBackStack()
            Toast.makeText(requireContext(), "Note uploaded.", Toast.LENGTH_LONG).show()
        }
    }

    private fun handleMenuClick() {
        binding.toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.action_add_to_catalog -> {
                    val dialog = FragmentDialog()
                    dialog.show(childFragmentManager, "customDialog")
                    true
                }
                else -> false
            }
        }
    }
}