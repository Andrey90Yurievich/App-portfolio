package com.dlyagoogleplay.notes

import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dlyagoogleplay.notes.databinding.ActivityAddNodeBinding
import com.dlyagoogleplay.notes.databinding.ActivityMainBinding

class AddNodeActivity : AppCompatActivity() {
    //привязать макет заметки
    private lateinit var binding: ActivityAddNodeBinding
    //инициализируем базу данных
    private lateinit var db: NotesDatabaseHelper

    //отображение на экране
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAddNodeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //создаем базу данных
        db = NotesDatabaseHelper(this)

        //при нажатии на кнопку выполнено введенные значения сохраняются в базу данных, закрывается активити и сообщение
        binding.saveButton.setOnClickListener {
            val title = binding.titleEditText.text.toString() //введенное имя заметки присваиваем к title
            val content = binding.contentEditText.text.toString() //введенное описание заметки присваиваем к content
            val note = Note(0, title, content) //создание экземпляра класс Note

            //вставляем в базу данных данные с экземпляра класса
            db.insertNote(note)

            //закрыли экран с заметкой
            finish()

            //вывели сообщение о загрузке в базу
            Toast.makeText(this, "ДАННЫЕ СОХРАНЕНЫ", Toast.LENGTH_SHORT).show()
        }
    }
}