package com.dlyagoogleplay.insragramclone.utils

import android.app.ProgressDialog
import android.content.Context
import android.net.Uri
import com.google.firebase.storage.FirebaseStorage
import java.util.UUID

//функция для загрузки изображения
fun uploadImage (uri: Uri, folderName:String, callback:(String?) -> Unit) {
    var imageUrl: String?= null
    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener {
                imageUrl = it.toString()
                callback(imageUrl)
            }
        }

    }





//функция для загрузки видео
fun uploadVideo (
    uri: Uri,
    folderName:String,
    progressDialog: ProgressDialog,
    callback:(String?) -> Unit) {
    var imageUrl: String?= null //переменная с типом строка

    progressDialog.setTitle("Да звгружаю яяя....") //диалоговое окно с индикатором прогресса.
    progressDialog.show() //s how - показать

    FirebaseStorage.getInstance().getReference(folderName).child(UUID.randomUUID().toString())
        .putFile(uri)
        .addOnSuccessListener {
            it.storage.downloadUrl.addOnSuccessListener { //слушатель успешного завершения
                imageUrl = it.toString()
                progressDialog.dismiss() //.dismiss() - отклонять
                callback(imageUrl)
            }
        }


        .addOnProgressListener { //Добавляет прослушиватель хода выполнения,
            val uploadedValue: Long = (it.bytesTransferred/it.totalByteCount) * 100 //uploadedValue - слушатель
            progressDialog.setMessage("Загрузка $uploadedValue %") //выводит на экран процент загрузки

        }

    }