package com.example.colornoteclone.utils

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.example.colornoteclone.R
import com.example.colornoteclone.database.Note
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import kotlinx.android.synthetic.main.bottom_sheet.*

class CustomBottomSheetDialogFragment(private val onBottomSheetCLicked: OnBottomSheetCLicked,val currentNote:Note) : BottomSheetDialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.bottom_sheet,container,false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        ivDelete.setOnClickListener {
                onBottomSheetCLicked.onDeleteClicked(currentNote)
        }
    }

    interface OnBottomSheetCLicked{
        fun onDeleteClicked(note: Note)
    }
}