package com.dlyagoogleplay.room

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.lifecycle.asLiveData
import com.dlyagoogleplay.room.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //создается БД при запуске активити
        val db = MainDb.getDb(this)
        db.getDao().getAllItems().asLiveData().observe(this) {list ->
            //
            binding.tvList.text = ""
            list.forEach {//Пройтись по всем элементам коллекции.

                val text = "Id: ${it.id} Name: ${it.name} Price: ${it.price}\n"
                //
                binding.tvList.append(text)
                //append = добавить
            }
        }
        //observe()- отслеживание изменений
        binding.button.setOnClickListener {
            val item = Item(null,
                binding.edName.text.toString(),
                binding.edPrice.text.toString()
            )

            Thread{
                db.getDao().insertItem(item)
            }.start()

        }
    }
}