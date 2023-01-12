package com.example.mynotes

import android.app.Activity
import android.content.Intent
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.mynotes.databinding.ActivityAddBinding
import com.example.mynotes.models.Note
import java.lang.Exception
import java.text.SimpleDateFormat
import java.util.*
import java.util.logging.SimpleFormatter

class add : AppCompatActivity() {

    private lateinit var binding: ActivityAddBinding
    private lateinit var note: Note
    private lateinit var oldNote: Note
    var isUpdate = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityAddBinding.inflate(layoutInflater)
        setContentView(binding.root)

        try {
            oldNote = intent.getSerializableExtra("current_note") as Note
            binding.addTittle.setText(oldNote.tittle)
            binding.addNote.setText(oldNote.note)
            isUpdate = true
        }catch (e : Exception){
            e.printStackTrace()
        }

        binding.check.setOnClickListener{
            val title = binding.addTittle.text.toString()
            val note_des = binding.addNote.text.toString()

            if(title.isNotEmpty() || note_des.isNotEmpty()){
                val formatter = SimpleDateFormat("D MMM YYY, HH:MM a")

                if (isUpdate){
                    note=Note(
                        oldNote.id,title,note_des,formatter.format(Date())
                    )
                }else{
                    note = Note(
                        null,title,note_des,formatter.format(Date())
                    )
                }
                val intent = Intent()
                intent.putExtra("note",note)
                setResult(Activity.RESULT_OK,intent)
                finish()
            }else{
                Toast.makeText(this@add,"Please enter some data",Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }

        }
        binding.back.setOnClickListener{
            onBackPressed()
        }
    }
}