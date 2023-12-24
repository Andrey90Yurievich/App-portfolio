package com.dlyagoogleplay.notes

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.dlyagoogleplay.notes.databinding.ActivityAddNodeBinding
import com.dlyagoogleplay.notes.databinding.ActivityUpdateBinding

class UpdateActivity : AppCompatActivity() {

    //привязываем макет
    private lateinit var binding: ActivityUpdateBinding

    //инициализировать вспомогательную базу данных заметок
    private lateinit var db: NotesDatabaseHelper

    //создание переменной
    private var noteId: Int = -1

    //отображение макета на экране
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityUpdateBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //создание БД
        db = NotesDatabaseHelper(this)

        //передаем данные
        //и если значение -1 тоже самое nuull толи пусто, то вернется тоже самое значение
        noteId = intent.getIntExtra("note_id", -1)
        if (noteId == -1) {
            finish() //закрыть активити окно
            return //вернуться
        }

        //найти заметку по ид и вставить туда изменения
        val note = db.getNoteByID(noteId) //getNoteByID - получить заметку по идентификатору -1
        binding.updateTitleEditText.setText(note.title)
        binding.updateContentEditText.setText(note.content)

        //кнопка сохранить галочка
        binding.updateSaveButton.setOnClickListener {
            val newTitle = binding.updateTitleEditText.text.toString() //запись заголовка присваиваем к новому звголовку
            val newContent = binding.updateContentEditText.text.toString()
            val updateNote = Note(noteId, newTitle, newContent) //переменная с новыми значениями
            db.updateNote(updateNote) //к БД присобачить обновленные
            finish() //закрыть окно
            Toast.makeText(this,"Изменения сохранены", Toast.LENGTH_SHORT).show()
        }

    }
}