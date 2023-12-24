package com.dlyagoogleplay.notes

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView

class NotesAdapter(private var notes: List<Note>, context: Context) :
    RecyclerView.Adapter<NotesAdapter.NoteViewHolder>() {

    //контекст как аргумент передаем в помощник баз данных
    private val db: NotesDatabaseHelper = NotesDatabaseHelper(context)

    //считываются данные с макета карточки
    class NoteViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val titleTextView: TextView = itemView.findViewById(R.id.titleTextCard) //значение с карточки заголовка
        val contentTextView: TextView = itemView.findViewById(R.id.titleContentCard) //значение с карточки описания
        val updateButton: ImageView = itemView.findViewById(R.id.updateBTN) //ссылка на кнопку редактировать
        val deleteButton: ImageView = itemView.findViewById(R.id.deleteBTN) //ссылка на кнопку удалить
    }

    //раздуваем с макета вьюшки в NoteViewHolder
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.note_item, parent, false)
        return NoteViewHolder(view) //
    }
    override fun getItemCount(): Int = notes.size //размер списка

    //перебирая список прикрепим к держателю данные
    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.titleTextView.text = note.title //с экземпляра заголовик прикрепляется к держателю
        holder.contentTextView.text = note.content //описание к держателю

        //кнопка редактировать
        holder.updateButton.setOnClickListener {
            val intent = Intent(holder.itemView.context, UpdateActivity::class.java).apply {
                putExtra("note_id", note.id) //передается значение через putExtra
            }
            holder.itemView.context.startActivity(intent)
        }

        //кнопка удалить в держателе
        holder.deleteButton.setOnClickListener {
            db.deleteNote(note.id) //наша функция удалить применяется к базе данных
            //db(база данных).deleteNote(наша функция удалить заметку)(note(список экз).id(ид в этом списке))
            refreshData(db.getAllNoties()) //обновленный список в БД, и ниже фун обновить и перерисовать список
            //затем сообщение что заметка удалена
            Toast.makeText(holder.itemView.context, "Заметка удалена", Toast.LENGTH_SHORT).show()
        }
    }

    //функция обновления данных
    fun refreshData(newNotes: List<Note>) {
        notes = newNotes //notes - новый список с экземплярами
        notifyDataSetChanged() //Метод notifyDataSetChanged() указывает адаптеру, что полученные ранее данные изменились и следует перерисовать список на экране.
    }
}