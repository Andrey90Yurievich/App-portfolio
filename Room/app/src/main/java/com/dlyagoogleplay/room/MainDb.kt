package com.dlyagoogleplay.room

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Item::class], version = 1) //для библиотеки рум что за класс и как с ним работать
abstract class MainDb : RoomDatabase() { //что если класс содержит хотя бы один абстрактный метод, то он обязан быть сам абстрактным.
    //Создавать объект на основе абстрактного класса нельзя.
    //Используют для объединения свойств и методов. Чтобы не повторять код несколько раз, его выносят в отдельный класс, который дублируют.

    abstract fun getDao(): Dao
    companion object {

        fun getDb(context: Context) : MainDb { //функция должна вернуть MainDb
            return Room.databaseBuilder( //databaseBuilder() - функция для создания БД
                context.applicationContext,
                MainDb::class.java,
                "test.db" //название ДБ там на устройстве где создастся БД
            ).build() //после этого эта фен либо создает лмбо выдает существующую БД
        }
    }
}