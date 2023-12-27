package com.dlyagoogleplay.room

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "items") //для заполнения БД
//будет создана таблица items
data class Item ( //заполним класс и он пойдет в таблицу
    @PrimaryKey(autoGenerate = true) //автоматическая генерация идентификаторов
    var id: Int? = null, //первое значенин id = null
    // Int? - может быть null
    @ColumnInfo(name = "name") val name: String?, //переменная для колонки name
    //@ColumnInfo(name = "name" (название коллонки)) val firstName: String?,
    @ColumnInfo(name = "price") val price: String?

        )