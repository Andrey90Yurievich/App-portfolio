package com.dlyagoogleplay.notes

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.dlyagoogleplay.notes.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {
    //привзка макета
    private lateinit var binding: ActivityMainBinding
    //инициализируем базу данных
    private lateinit var db: NotesDatabaseHelper
    //инициализируем адаптер
    private lateinit var notesAdapter: NotesAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root) //отображаем содержимое
        //создаем базданых в этом экране
        db = NotesDatabaseHelper(this)

        //через адаптер показать в ресайкле вьюшки
        notesAdapter = NotesAdapter(db.getAllNoties(), this) //входят параметры существующего списка
        binding.notesRV.adapter = notesAdapter

        //к ресайклу прикручиваем линейный менеджер отображения
        binding.notesRV.layoutManager = LinearLayoutManager(this)

        //кнопка добавить заметку
        binding.addButton.setOnClickListener {
            val intent = Intent(this, AddNodeActivity::class.java)
            //запускается активити с заметкой
            startActivity(intent)
        }
    }
    //обновляем отображать список с новыми экзкмплярами
    override fun onResume() {
        super.onResume()
        notesAdapter.refreshData(db.getAllNoties())
        //refreshData - функция в адаптере для обновления данных
        //все новые экземпляры через адаптер  отображать в ресайкл вью
    }
}