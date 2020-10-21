package com.example.colornoteclone.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.setupWithNavController
import com.example.colornoteclone.R
import com.example.colornoteclone.database.NoteDatabase
import com.example.colornoteclone.repository.NotesRepository
import com.example.colornoteclone.viewmodel.NoteViewModel
import com.example.colornoteclone.viewmodel.NoteViewModelFactory
import com.google.android.material.navigation.NavigationView
import com.google.firebase.auth.FirebaseAuth
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private lateinit var drawerLayout:DrawerLayout
    private lateinit var navcontroller:NavController
     lateinit var noteViewModel: NoteViewModel
    lateinit var headerLayout:ConstraintLayout

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        drawerLayout=findViewById(R.id.drawerLayout)
        navcontroller= Navigation.findNavController(this,R.id.fragment)
        homeNavView.setupWithNavController(fragment.findNavController())
        headerLayout=homeNavView.getHeaderView(0).findViewById(R.id.headerLayout)

        val noteDatabase= NoteDatabase(this)
        val noteRepository= NotesRepository(noteDatabase)
        val factory=NoteViewModelFactory(noteRepository)
        noteViewModel= ViewModelProvider(this,factory).get(NoteViewModel::class.java)

        headerLayout.setOnClickListener {

            if(FirebaseAuth.getInstance().currentUser!=null){
                navcontroller.navigate(R.id.onlineSyncFragment)
            }else{
                navcontroller.navigate(R.id.signInFragment)
            }

            drawerLayout.closeDrawer(GravityCompat.START)
        }



    }

    override fun onSupportNavigateUp(): Boolean {
        return navcontroller.navigateUp()
    }


    override fun onBackPressed() {
        if(drawerLayout.isDrawerOpen(GravityCompat.START)){
            drawerLayout.closeDrawer(GravityCompat.START)
        }else{
            super.onBackPressed()
        }
    }
}