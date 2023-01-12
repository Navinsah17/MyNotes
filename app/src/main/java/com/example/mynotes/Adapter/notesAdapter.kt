package com.example.mynotes.Adapter


import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.cardview.widget.CardView
import androidx.recyclerview.widget.RecyclerView
import com.example.mynotes.R
import com.example.mynotes.models.Note
import kotlin.random.Random


class notesAdapter(private val context : Context,val listener : NoteClickListener) : RecyclerView.Adapter<notesAdapter.NoteViewHolder>(){

    private val notelist = ArrayList<Note>()
    private val fulllist = ArrayList<Note>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        return NoteViewHolder(
            LayoutInflater.from(context).inflate(R.layout.list,parent,false)
        )
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val currentnote = notelist[position]
        holder.tiitleview.text=currentnote.tittle
        holder.tiitleview.isSelected=true

        holder.noteview.text=currentnote.note
        holder.dateview.text=currentnote.date
        holder.dateview.isSelected=true

        //---------------------------------------
        holder.noteslayout.setCardBackgroundColor(holder.itemView.resources.getColor(randomcolor(),null))

        holder.noteslayout.setOnClickListener{
            listener.OnClick(notelist[holder.adapterPosition])

        }
        holder.noteslayout.setOnLongClickListener{

            listener.OnLongClick(notelist[holder.adapterPosition],holder.noteslayout)
            true

        }
    }

    override fun getItemCount(): Int {
        return notelist.size
    }

    fun randomcolor() : Int{
        val list1 = ArrayList<Int>()
        list1.add(R.color.color2)
        list1.add(R.color.color6)
        list1.add(R.color.color5)
        list1.add(R.color.color4)
        list1.add(R.color.color3)
        list1.add(R.color.color7)
        list1.add(R.color.color1)

        val seed = System.currentTimeMillis().toInt()
        val random = Random(seed).nextInt(list1.size)
        return list1[random]
    }

    fun filterList(search: String){

        notelist.clear()

        for (item in fulllist){
            if(item.tittle?.lowercase()?.contains(search.lowercase()) ==true ||
            item.note?.lowercase()?.contains(search.lowercase()) == true){

                notelist.add(item)

            }
        }
        notifyDataSetChanged()

    }

    fun updateList(newList: List<Note>){

        fulllist.clear()
        fulllist.addAll(newList)

        notelist.clear()
        notelist.addAll(newList)
        notifyDataSetChanged()

    }

    inner class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){

        val noteslayout = itemView.findViewById<CardView>(R.id.cardview1)
        val tiitleview = itemView.findViewById<TextView>(R.id.tittleview)
        val noteview = itemView.findViewById<TextView>(R.id.note)
        val dateview = itemView.findViewById<TextView>(R.id.date)
    }

    interface NoteClickListener{
        fun OnClick(note: Note)
        fun OnLongClick(note: Note,cardView: CardView)


    }
}