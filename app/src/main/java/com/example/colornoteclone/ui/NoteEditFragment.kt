package com.example.colornoteclone.ui

import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.fragment.app.Fragment
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import com.example.colornoteclone.R
import com.example.colornoteclone.database.Note
import com.example.colornoteclone.utils.showLog
import com.example.colornoteclone.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_note_edit.*


class NoteEditFragment : Fragment() {

    private lateinit var viewModel:NoteViewModel
    private lateinit var noteFinal:Note

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_note_edit, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        (activity as AppCompatActivity).setSupportActionBar(toolbar)
        setHasOptionsMenu(true)
        super.onViewCreated(view, savedInstanceState)

        viewModel= (activity as MainActivity).noteViewModel

        viewMode()

            arguments?.let {
            val args=NoteEditFragmentArgs.fromBundle(it)
                noteFinal=args.note
            tvTitleEditNote.text=args.note.noteTitle
             etContentEdit.setText(args.note.noteContent)
                etTitleEdit.setText(args.note.noteTitle)
            }

        ivEdit.setOnClickListener {
            editMode()
        }

        ivSubmitEdit.setOnClickListener {
            if(etContentEdit.text.isNotEmpty()&&etTitleEdit.text.isNotEmpty()){
                if(noteFinal.noteTitle == etTitleEdit.text.toString() && noteFinal.noteContent==etContentEdit.text.toString()){

                }else{
                    noteFinal.date=viewModel.getDAte()
                }
               noteFinal.noteTitle=etTitleEdit.text.toString()
                noteFinal.noteContent=etContentEdit.text.toString()
                viewModel.upsertNote(noteFinal)
                it.hideKeyboard()
                requireActivity().onBackPressed()
            }else{
                Toast.makeText(requireContext(), "All fields needed", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun editMode(){
        etContentEdit.isEnabled=true
        etTitleEdit.isEnabled=true
        etTitleEdit.isVisible=true
        openSoftKeyboard(context!!,etTitleEdit,etTitleEdit.text.toString())
        ivBack.isVisible=false
        ivEdit.isVisible=false
        ivSubmitEdit.isVisible=true
        tvTitleEditNote.isVisible=false
    }

    private fun viewMode(){
        tvTitleEditNote.isVisible=true
        etContentEdit.isEnabled=false
        etTitleEdit.isEnabled=false
        etTitleEdit.isVisible=false
        ivBack.isVisible=true
        ivSubmitEdit.visibility=View.INVISIBLE
    }

    private fun openSoftKeyboard(context: Context, view: EditText, title:String) {
        view.requestFocus()
        //context.showLog("frombjk",noteFinal.noteTitle)
        view.setSelection(title.length)
        val imm = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT)
    }

    private fun View.hideKeyboard() {
        val inputManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputManager.hideSoftInputFromWindow(windowToken, 0)
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.edit_note_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)
    }
}