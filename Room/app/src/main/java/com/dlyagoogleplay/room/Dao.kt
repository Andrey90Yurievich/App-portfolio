package com.dlyagoogleplay.room

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

//интерфейс для функций которые могут записывать или считывать
@Dao
interface Dao {
    @Insert //аннотация и функция для вставки в базу данных
    fun insertItem(item: Item) //функция для записи данных
    @Query("SELECT * FROM items") //аннотация для получения данных
    //SELECT - все
    //FROM - из
    fun getAllItems() : Flow<List<Item>> //выдаст список со всеми элементами из базы данных
    //выдается список из найденных результатов
    //Flow - для обновления данных при изменении
}