package com.example.yana.home15

import android.content.DialogInterface
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.widget.Toolbar
import androidx.recyclerview.widget.RecyclerView
import com.example.yana.home15.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(), NotesAdapterListeners {
    private lateinit var recView: RecyclerView
    private lateinit var adapter: NotesAdapter
    private lateinit var zagolovok: EditText
    private lateinit var kontent: EditText
    private lateinit var data: EditText
    private lateinit var delete: Button

    private val zagolovokKontentDataResult =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
            if (it.resultCode == RESULT_OK) {
                val zagolovok = it.data?.extras?.getString(ZAGOLOVOK)
                val kontent = it.data?.extras?.getString(KONTENT)
                val data = it.data?.extras?.getString(DATA)
                adapter.addNewItem(zagolovok, kontent, data)
            }
        }
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        val toolBar = findViewById<Toolbar>(R.id.toolbar)

    fun setupListeners(){
            binding.delete.setOnClickListener{
                adapter.deleteAll()
            }
            binding.plus.setOnClickListener{
                val intent = Intent(this, SecondActivity::class.java)
                zagolovokKontentDataResult.launch(intent)
            }
            binding.delete.setOnClickListener{
                AlertDialog.Builder(this)
                    .setTitle("???? ?????????????? ?????? ???????????? ?????????????? ?????? ?????????????")
                    .setPositiveButton("????") { _, _ -> adapter.deleteAll() }
                    .setNegativeButton("??????"){ _, _ -> }
                    .show()
            }
        }
        setupListeners()
        setSupportActionBar(toolBar)
        setupViews()
        setupRecycler()
    }
    private fun setupViews(){
        recView = findViewById(R.id.recView)
    }
    private fun setupRecycler(){
        adapter = NotesAdapter(this)
        binding.recView.adapter = adapter
    }

    override fun deleteItem(position: Int) {
        adapter.deleteItem(position)
    }
    companion object {
        const val ZAGOLOVOK = "zagolovok"
        const val KONTENT = "kontent"
        const val DATA = "data"
        const val KORZINA = "korzina"
    }
}