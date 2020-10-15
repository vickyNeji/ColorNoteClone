package com.example.colornoteclone.adapters

import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.AsyncListDiffer
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.example.colornoteclone.R
import com.example.colornoteclone.database.Note
import kotlinx.android.synthetic.main.note_recyclerview.view.*

class NotesAdapter(val onNoteCLicked: OnNoteCLicked) : RecyclerView.Adapter<NotesAdapter.ArticleViewHolder>() {

    private  val TAG = "NotesAdapter"
    inner class ArticleViewHolder(itemView: View) :RecyclerView.ViewHolder(itemView)

    private val differCallback=object :DiffUtil.ItemCallback<Note>(){
        override fun areItemsTheSame(oldItem: Note, newItem: Note): Boolean {
            return oldItem.id==newItem.id
        }

        override fun areContentsTheSame(oldItem: Note, newItem: Note): Boolean {
                return oldItem==newItem
        }


    }

    val differ=AsyncListDiffer(this,differCallback)
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ArticleViewHolder {
        return ArticleViewHolder(LayoutInflater.from(
            parent.context).inflate(R.layout.note_recyclerview,parent,false))

    }

    override fun getItemCount(): Int {
        return differ.currentList.size
    }

    override fun onBindViewHolder(holder: ArticleViewHolder, position: Int) {
        val note=differ.currentList[position]

        holder.itemView.apply {
            tvDate.text=note.date
            tvTitle.text=note.noteTitle
            setOnClickListener {
                onNoteCLicked.onNoteClicked(note)
            }

            setOnLongClickListener {
                onNoteCLicked.onLongClicked(note)
                true
            }
        }
    }

    interface OnNoteCLicked{
        fun onNoteClicked(note:Note)
        fun onLongClicked(note: Note)
    }
}