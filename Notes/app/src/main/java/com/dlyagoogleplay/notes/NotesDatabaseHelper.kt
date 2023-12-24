package com.dlyagoogleplay.notes

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


//этот класс с фунциями для управления данными
class NotesDatabaseHelper (context : Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION)  {

    //создали объекты в классе константы которые можно достать из этого класса
    companion object {
        //константы для того чтобы не ошибиться в названии
        private const val DATABASE_NAME = "notesapp.db" //название базы данных
        private const val DATABASE_VERSION = 1
        private const val TABLE_NAME = "allnotes" //название таблицы
        private const val COLUMN_ID = "id" //названия столбца
        private const val COLUMN_TITLE = "title"
        private const val COLUMN_CONTENT = "content"
    }

    //создается база
    override fun onCreate(db: SQLiteDatabase?) {
        val createTableQuery = "CREATE TABLE $TABLE_NAME ($COLUMN_ID INTEGER PRIMARY KEY, $COLUMN_TITLE TEXT, $COLUMN_CONTENT TEXT)"
        db?.execSQL(createTableQuery)
    }

    //обновляется база
    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        val dropTableQuery = "DROP TABLE IF EXISTS $TABLE_NAME"
        db?.execSQL(dropTableQuery)
        onCreate(db)
    }

    //фукция вставки заметки
    fun insertNote(note: Note) { //параметром передается в функцию экземпяр класса с 3
        val db = writableDatabase //база данных открывается для записи , переводит хранилище в режим для записи
        //теперь db может записывать и стирать
        val values = ContentValues().apply { //создание ячейки
            put(COLUMN_TITLE, note.title) //COLUMN_TITLE - ключь, note.title - значение с экзкмпляра класса с макета
            put(COLUMN_CONTENT, note.content) //описание с экземпляра класса которые с макета
        }
        //вставляем в базу данных ячеку
        db.insert(TABLE_NAME, null, values)
        //закрываем таблицу
        db.close()
        //в базе данных 1 строка с данными
    }

    //перебираем базу данных и получаем список с экзкмплярами заметок
    fun getAllNoties(): List<Note> {
        val notesList = mutableListOf<Note>() //создаем новый изменяемы список через класс Note с эеземплярами
        val db = readableDatabase //читаемая база данных
        val query = "SELECT * FROM $TABLE_NAME" //запрос SELECT - выбрать FROM - от
        val cursor = db.rawQuery(query, null) // принимающий сырой SQL-запрос.

        while (cursor.moveToNext()) { //пербирается сырой ответ,  moveToNext() — перемещает курсор на следующую строку;
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) //получение id из столбца getColumnIndexOrThrow() — возвращает индекс для столбца с указанным именем (выбрасывает исключение, если столбец с таким именем не существует);
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)) //присвоение к title заголовка из столбца базы данных с заголовками
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)) //получение описания из столбца
            val note = Note(id, title, content) //все  сохраняются в note
            notesList.add(note) //добавляем все в список notesList экземпляры через note
        }
        //очищяем сырые данные
        cursor.close()
        //очищяем базу данных
        db.close()
        //вернем список экземпляров с данными
        return notesList
    }

    //отредактированная заметка
    fun updateNote(note: Note) { //параметром передаем список
        val db = writableDatabase //изменяемая БД
        val values = ContentValues().apply { //apply - применить
            put(COLUMN_TITLE, note.title) //put - класть из класса заголовок в БД колонка заголовок
            put(COLUMN_CONTENT, note.content) //в БД контекст добавляем контекст
        }
        val whereClause = "$COLUMN_ID = ?"
        val whereArgs = arrayOf(note.id.toString()) //массив в строках
        db.update(TABLE_NAME, values, whereClause, whereArgs)
        db.close() //очистить
    }


    //найти заметку по ид
    fun getNoteByID(noteId: Int): Note {
        val db = readableDatabase //читаемая база данных
        val query = "SELECT * FROM $TABLE_NAME WHERE $COLUMN_ID = $noteId" //запрос SELECT - выбрать FROM - от
        val cursor = db.rawQuery(query, null) //метод rawQuery(), принимающий сырой SQL-запрос.
        cursor.moveToFirst()
            val id = cursor.getInt(cursor.getColumnIndexOrThrow(COLUMN_ID)) //получение id из столбца getColumnIndexOrThrow() — возвращает индекс для столбца с указанным именем (выбрасывает исключение, если столбец с таким именем не существует);
            val title = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_TITLE)) //присвоение к title заголовка из столбца базы данных с заголовками
            val content = cursor.getString(cursor.getColumnIndexOrThrow(COLUMN_CONTENT)) //получение описания из столбца
        cursor.close()
        db.close()
        //вернем список экземпляров с данными
        return Note(id, title, content)
    }

    //удалить заметку
    fun deleteNote(noteId: Int) {
        val db = writableDatabase //изменяемая база данных создаем
        val whereClause = "$COLUMN_ID = ?" //
        val whereArgs = arrayOf(noteId.toString())
        //удалить из нашей ДБ, по ид, и контекст
        db.delete(TABLE_NAME, whereClause, whereArgs)
        //очистить БД
        db.close()
    }

}