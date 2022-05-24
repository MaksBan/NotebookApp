package com.github.maksban.noteapp.fragments.dialog

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.view.isVisible
import androidx.fragment.app.DialogFragment
import androidx.lifecycle.lifecycleScope
import com.github.maksban.noteapp.R
import com.github.maksban.noteapp.data.api.RetrofitInstance
import com.github.maksban.noteapp.databinding.DialogApiBinding
import com.github.maksban.noteapp.fragments.notes.TAG
import retrofit2.HttpException
import java.io.IOException

class FragmentDialogApi : DialogFragment(R.layout.dialog_api) {
    private lateinit var binding: DialogApiBinding

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = DialogApiBinding.bind(view)


        lifecycleScope.launchWhenCreated {
            binding.progressBar.isVisible = true
            val response = try {
                RetrofitInstance.api.getFact()
            } catch (e: IOException) {
                Log.e(TAG, "IOException, might not have internet connection")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            } catch (e: HttpException) {
                Log.e(TAG, "HttpException, unexpected response")
                binding.progressBar.isVisible = false
                return@launchWhenCreated
            }

            val body = response.body()
            if (response.isSuccessful && body != null) {
                binding.activityName.text = body.first().toString()
            } else {
                Log.e(TAG, "${response}")
            }
            binding.progressBar.isVisible = false
        }

        binding.btnDismiss.setOnClickListener {
            dismiss()
        }
    }
}