package com.dlyagoogleplay.insragramclone.Models

//класс для создания экземпляров юзер
class User {
    //модель с контрукторами
    var image:String? = null
    var name:String? = null
    var email:String? = null
    var password:String? = null
    constructor()
    //Первичный конструктор инициализирует экземпляр класса и его свойства в заголовке класса.
    constructor(image: String?, name: String?, email: String?, password: String?) {
        this.image = image
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(name: String?, email: String?, password: String?) {
        this.name = name
        this.email = email
        this.password = password
    }

    constructor(email: String?, password: String?) {
        this.email = email
        this.password = password
    }


}