package com.example.colornoteclone.ui

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.*
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.example.colornoteclone.R
import com.example.colornoteclone.database.Note
import com.example.colornoteclone.utils.showLog
import com.example.colornoteclone.utils.showToast
import com.example.colornoteclone.viewmodel.NoteViewModel
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.CollectionReference
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.android.synthetic.main.fragment_online_sync.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.lang.Exception

class OnlineSyncFragment : Fragment() {

    private lateinit var notesViewModel:NoteViewModel
    private lateinit var notesReference:CollectionReference
    private  val TAG = "OnlineSyncFragment"

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_online_sync, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        notesViewModel=(activity as MainActivity).noteViewModel
       notesReference= FirebaseFirestore.getInstance().collection("notes")

        (activity as AppCompatActivity).setSupportActionBar(toolbar_google)
        setHasOptionsMenu(true)

        tvUserName.text=FirebaseAuth.getInstance().currentUser!!.displayName
        tvEmail.text=FirebaseAuth.getInstance().currentUser!!.email

        tvSync.setOnClickListener {
            uploadNotes()
        }
    }


    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.logout_menu,menu)
        super.onCreateOptionsMenu(menu, inflater)

    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when(item.itemId){
             R.id.syncNow->{
                 context!!.showToast("Clicked")
                 true
             }
            R.id.logOut->{
                FirebaseAuth.getInstance().signOut()
                startActivity(Intent(context!!,MainActivity::class.java))
                activity!!.finishAffinity()
                true
            }

            else -> super.onOptionsItemSelected(item)
        }
    }

    private fun uploadNotes(){
        Toast.makeText(context, "Syncing data", Toast.LENGTH_SHORT).show()

            try {
                notesViewModel.getAllnotes().observe(viewLifecycleOwner, Observer {
                    for(element in it){
                        if(element.userId==""){
                            element.userId=FirebaseAuth.getInstance().currentUser!!.uid
                        }
                        notesReference.add(element)
                            .addOnSuccessListener {
                                Toast.makeText(context, "Successfully uploaded", Toast.LENGTH_SHORT)
                                    .show()
                            }.addOnFailureListener {
                                context!!.showLog("",it.message.toString())
                            }

//                        notesReference.add(element)
//                            .addOnCompleteListener {task->
//                                if(task.isSuccessful){
//                                    //Toast.makeText(context, "successfully uploaded", Toast.LENGTH_SHORT).show()
//                                    Log.d(TAG, "uploadNotes: Notes uploaded")
//                                }
//                            }.addOnFailureListener {e->
//                                //Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()
//                                Log.d(TAG, "uploadNotes: "+e.message)
//                            }
                    }
                })

            }catch (e:Exception){

                    Toast.makeText(context, e.message, Toast.LENGTH_SHORT).show()

            }

    }

}