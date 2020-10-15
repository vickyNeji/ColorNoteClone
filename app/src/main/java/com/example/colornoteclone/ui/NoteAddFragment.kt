package com.example.colornoteclone.ui

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import com.example.colornoteclone.R
import com.example.colornoteclone.database.Note
import com.example.colornoteclone.utils.showToast
import com.example.colornoteclone.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note_add.*


class NoteAddFragment : Fragment() {

    private lateinit var viewmodel:NoteViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_note_add, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewmodel= (activity as MainActivity).noteViewModel


        openSoftKeyboard(context!!,etTitle)

        ivSubmit.setOnClickListener {
            if(etTitle.text.toString().isNotEmpty() && etContent.text.toString().isNotEmpty()){
                viewmodel.upsertNote(Note(etTitle.text.toString(),etContent.text.toString()
                    ,viewmodel.getDAte()))
                it.hideKeyboard()
                requireActivity().onBackPressed()
            }else{
                requireContext().showToast("Empty Fields not allowed")
            }
        }

    }
    private fun openSoftKeyboard(context: Context, view: EditText) {
        view.requestFocus()
        // open the soft keyboard
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }
}