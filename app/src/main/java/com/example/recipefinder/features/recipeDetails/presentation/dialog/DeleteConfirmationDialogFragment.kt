package com.example.recipefinder.features.recipeDetails.presentation.dialog

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import com.example.recipefinder.R
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class DeleteConfirmationDialogFragment(
    private val onConfirmDelete: () -> Unit
) : DialogFragment() {

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return MaterialAlertDialogBuilder(requireContext())
            .setTitle(getString(R.string.deleting_recipe))
            .setMessage(getString(R.string.are_you_sure))
            .setPositiveButton(getString(R.string.delete)) { _, _ ->
                onConfirmDelete()
            }
            .setNegativeButton(getString(R.string.cancel)) { dialog, _ ->
                dialog.dismiss()
            }
            .create()
    }

    companion object {
        const val TAG = "DeleteConfirmationDialog"
    }
}
