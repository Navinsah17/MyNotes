package com.example.mynotes

import android.app.Activity
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.widget.Adapter
import android.widget.LinearLayout
import android.widget.PopupMenu
import android.widget.SearchView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.cardview.widget.CardView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.example.mynotes.Adapter.notesAdapter
import com.example.mynotes.Database.NoteDatabase
import com.example.mynotes.databinding.ActivityMainBinding
import com.example.mynotes.models.Note
import com.example.mynotes.models.NoteViewModel

class MainActivity : AppCompatActivity(),notesAdapter.NoteClickListener,PopupMenu.OnMenuItemClickListener{

    private lateinit var binding: ActivityMainBinding
    private lateinit var database: NoteDatabase
    private lateinit var viewModel : NoteViewModel
    lateinit var adapter: notesAdapter
    lateinit var selectedNote: Note

    private var updateNote = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

     if(result.resultCode == Activity.RESULT_OK){

         val note = result.data?.getSerializableExtra("note") as? Note
         if(note != null){

             viewModel.updateNote(note)
         }
     }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initUI()

        viewModel = ViewModelProvider(this,
            ViewModelProvider.AndroidViewModelFactory.getInstance(application)).get(NoteViewModel::class.java)
        viewModel.allNotes.observe(this) { list ->

            list?.let {

                adapter.updateList(list)
            }
        }
        database = NoteDatabase.getDatabase(this)
    }

    private fun initUI() {
        binding.recyclerView.setHasFixedSize(true)
        binding.recyclerView.layoutManager = StaggeredGridLayoutManager(2,LinearLayout.VERTICAL)
        adapter = notesAdapter(this,this)
        binding.recyclerView.adapter = adapter

        val getContent = registerForActivityResult(ActivityResultContracts.StartActivityForResult()){ result ->

            if(result.resultCode == Activity.RESULT_OK){

                val note = result.data?.getSerializableExtra("note")as? Note
                if(note != null){
                    viewModel.insertNote(note)
                }

            }
        }

        binding.floatingActionButton.setOnClickListener{

            val intent = Intent(this,add::class.java)
            getContent.launch(intent)
        }
        binding.searchview.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(p0: String?): Boolean {

                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {

                if(newText != null){
                    adapter.filterList(newText)
                }

                return true

            }

        })

    }

    override fun OnClick(note: Note) {

        val intent = Intent(this@MainActivity,add::class.java)
        intent.putExtra("current_note",note)
        updateNote.launch(intent)
    }

    override fun OnLongClick(note: Note, cardView: CardView) {

        selectedNote = note
        popUpDisplay(cardView)


    }

    private fun popUpDisplay(cardView: CardView) {
        val popup = PopupMenu(this,cardView)
        popup.setOnMenuItemClickListener(this@MainActivity)
        popup.inflate(R.menu.popup)
        popup.show()
    }

    override fun onMenuItemClick(item: MenuItem?): Boolean {
        if(item?.itemId == R.id.delete){

            viewModel.deleteNote(selectedNote)
            return true
        }
        return false
    }

}


