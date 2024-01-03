package com.dlyagoogleplay.rxjava


import android.content.ContentValues.TAG
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.BackpressureStrategy
import io.reactivex.rxjava3.core.Completable
import io.reactivex.rxjava3.core.Flowable
import io.reactivex.rxjava3.core.Maybe
import io.reactivex.rxjava3.core.Observable
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.schedulers.Schedulers

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val observable = Observable.just(1, 2, 3)

        val single = Single.just(1)

        val flowable = Flowable.just(1, 2, 3)

        val btnTest: TextView = findViewById(R.id.btnTest)
        btnTest.setOnClickListener() {
           Log.e(TAG, "Клык, Клык")
       }

        val dispose = dataSource()
            .subscribeOn(Schedulers.newThread()) //наблюдение в основном потоке
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe ({ //Время подписки Это временное состояние, когда subscribe()вызывается поток, который устанавливает внутреннюю цепочку шагов обработки:
                btnTest.text = "Следующее число $it"
                Log.e(TAG, "Новые данные $it")
            }, {
                Log.e(TAG, "it ${it.localizedMessage}")
            }, {
                Log.e(TAG, "результат выполнен")
            })
    }




//    fun dataSource() : Observable<Int> { //Время выполнения Это состояние, когда потоки активно выдают элементы, ошибки или сигналы завершения:
//        return Observable.create { subscriber ->
//            for (i in 0..100) {
//                Thread.sleep(3000)
//                subscriber.onNext(i)
//            }
//        }
//    }

//    fun dataSource() : Flowable<Int> { //Время выполнения Это состояние, когда потоки активно выдают элементы, ошибки или сигналы завершения:
//        return Flowable.create ({ subscriber ->
//            for (i in 0..100000) {
//                //Thread.sleep(3000)
//                subscriber.onNext(i)
//            }
//            subscriber.onComplete()
//        }, BackpressureStrategy.LATEST) //страиегия
//    }

//    fun dataSource() : Single<List<Int>> {
//
//        return Single.create {subscriber ->//Время выполнения Это состояние, когда потоки активно выдают элементы, ошибки или сигналы завершения:
//        val list = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
//                //Thread.sleep(3000)
//        subscriber.onSuccess(list) //или onError
//        }
//    }

//    fun dataSource() : Completable {
//
//        return Completable.create {subscriber ->
//            val list = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
//            //Thread.sleep(3000)
//            subscriber.onComplete()
//        }
//    }

    fun dataSource() : Maybe<List<Int>> {
        return Maybe.create {subscriber ->
            val list = listOf<Int>(1,2,3,4,5,6,7,8,9,10)
            //Thread.sleep(3000)
            subscriber.onSuccess(list)
            subscriber.onComplete()
        }
    }

}