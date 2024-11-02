// BottomSheetFragment.kt
package com.algokelvin.movieapp.presentation.product

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.TextView
import com.algokelvin.movieapp.R
import com.google.android.material.bottomsheet.BottomSheetDialogFragment

class BottomSheetFragment : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.profile_page_bottom_sheet, container, false)

        // Get views from the layout and set listeners if necessary
        val titleTextView: TextView = view.findViewById(R.id.title)
        val actionButton: Button = view.findViewById(R.id.action_button)

        // Set any necessary data or listeners
        titleTextView.text = "Hello from Bottom Sheet!"
        actionButton.setOnClickListener {
            // Perform an action
            dismiss() // Close the bottom sheet
        }

        return view
    }
}
