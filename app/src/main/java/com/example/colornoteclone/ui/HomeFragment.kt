package com.example.colornoteclone.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.Observer
import androidx.navigation.NavController
import androidx.navigation.Navigation
import com.example.colornoteclone.R
import com.example.colornoteclone.adapters.NotesAdapter
import com.example.colornoteclone.database.Note
import com.example.colornoteclone.utils.CustomBottomSheetDialogFragment

import com.example.colornoteclone.viewmodel.NoteViewModel
import kotlinx.android.synthetic.main.fragment_home.*

class HomeFragment : Fragment(),NotesAdapter.OnNoteCLicked,CustomBottomSheetDialogFragment.OnBottomSheetCLicked {

    private lateinit var drawerLayout:DrawerLayout
    private lateinit var viewModel:NoteViewModel
    private lateinit var notesAdapter:NotesAdapter
    private lateinit var navController: NavController
    private lateinit var customBottomSheetDialogFragment:CustomBottomSheetDialogFragment

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navController=Navigation.findNavController(view)
        drawerLayout= activity?.findViewById(R.id.drawerLayout)!!

        viewModel=(activity as MainActivity).noteViewModel
        setupRecyclerview()

        ivMenu.setOnClickListener {
                drawerLayout.openDrawer(GravityCompat.START)
        }

        ivAddNote.setOnClickListener {
            navController.navigate(R.id.action_homeFragment_to_noteEditFragment)
        }
        viewModel.getAllnotes().observe(viewLifecycleOwner, Observer {
            if(it.isNotEmpty()){
                notesAdapter.differ.submitList(it)
            }else{
                Toast.makeText(requireContext(), "No data available", Toast.LENGTH_SHORT).show()
            }

        })


    }

    private fun setupRecyclerview() {
        notesAdapter=NotesAdapter(this)
        noteRv.adapter=notesAdapter
    }

    override fun onNoteClicked(note: Note) {
        val action:HomeFragmentDirections.ActionHomeFragmentToNoteEditFragment2
        =HomeFragmentDirections.actionHomeFragmentToNoteEditFragment2(note)
        navController.navigate(action)
    }

    override fun onLongClicked(note: Note) {
         customBottomSheetDialogFragment=CustomBottomSheetDialogFragment(this,note)
        customBottomSheetDialogFragment.show(childFragmentManager,customBottomSheetDialogFragment.tag)
    }

    override fun onDeleteClicked(note: Note) {
        AlertDialog.Builder(context!!)
            .setTitle("Are you sure?")
            .setIcon(R.drawable.ic_baseline_delee)
            .setMessage("DO you really want to delete?")
            .setPositiveButton("Yes"){dialogInterface, i ->
                viewModel.deleteNote(note)
                customBottomSheetDialogFragment.dismiss()
                dialogInterface.dismiss()
            }.setNegativeButton("No"){dialogInterface, i ->
                dialogInterface.dismiss()
                customBottomSheetDialogFragment.dismiss()
            }
            .show()

    }
}