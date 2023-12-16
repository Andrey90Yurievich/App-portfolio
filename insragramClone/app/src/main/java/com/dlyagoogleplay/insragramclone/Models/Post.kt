package com.dlyagoogleplay.insragramclone.Models


//КЛАСС пост для макета или класса постов для добавления
class Post {
    var postUrl:String="" //создаем память пустые
    var caption:String=""
    var uid:String=""
    var time:String=""
    constructor()
    constructor(postUrl: String, caption:String) {
        this.postUrl = postUrl  //заголовок поста или ссылка на пост
        this.caption = caption //содержание поста
    }

    constructor(postUrl: String, caption: String, uid: String, time: String) {
        this.postUrl = postUrl
        this.caption = caption
        this.uid = uid
        this.time = time
    }



}