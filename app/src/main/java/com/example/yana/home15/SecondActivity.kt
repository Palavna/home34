package com.example.yana.home15

import android.app.DatePickerDialog
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.DatePicker
import android.widget.EditText
import android.widget.TextView
import com.example.yana.home15.databinding.ActivityMainBinding
import com.example.yana.home15.databinding.ActivitySecondBinding
import java.text.SimpleDateFormat
import java.util.*

class SecondActivity : AppCompatActivity(), DatePickerDialog.OnDateSetListener {
    private lateinit var adapter: NotesAdapter
    private lateinit var binding: ActivitySecondBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySecondBinding.inflate(layoutInflater)
        setContentView(binding.root)
        setupListeners()
        val list = RoomApp.DB?.getUserDao()?.getAllUsers()
        adapter.addAll(list)
    }

    private fun setupListeners() {
        binding.save.setOnClickListener {
            RoomApp.DB?.getUserDao()?.saveUser(
                NotesModel(
                    title = binding.zagolovok.text.toString(),
                    content = binding.kontent.text.toString(),
                    dataTv = binding.data.text.toString()
                )
            )
            val list = RoomApp.DB?.getUserDao()?.getAllUsers()
            adapter.addAll(list)

            val intent = Intent()
            binding.zagolovok.text.toString()
            binding.kontent.text.toString()
            binding.data.text.toString()
            setResult(RESULT_OK, intent)
            finish()
        }
        binding.data.setOnClickListener {
            val calendar = Calendar.getInstance()
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val month = calendar.get(Calendar.MONTH)
            val year = calendar.get(Calendar.YEAR)
            DatePickerDialog(this, this, year, month, day).show()
            val data = "$day/${month + 1}/$year"
            val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            val sdfNew = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
            val newData = sdf.parse(data)
            binding.data.text = sdfNew.format(newData)
        }
    }

    override fun onDateSet(p0: DatePicker?, year: Int, month: Int, day: Int) {
        val data = "$day/${month + 1}/$year"
        val sdf = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
        val sdfNew = SimpleDateFormat("dd MMMM yyyy", Locale.getDefault())
        val newData = sdf.parse(data)
        binding.data.text = sdfNew.format(newData)
    }
}