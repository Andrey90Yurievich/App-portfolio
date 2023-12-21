package com.dlyagoogleplay.weather

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.SearchView
import com.dlyagoogleplay.weather.databinding.ActivityMainBinding
import com.dlyagoogleplay.weatherapp.ApiInterface
import com.dlyagoogleplay.weatherapp.WeatherApp
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by lazy {
        ActivityMainBinding.inflate(layoutInflater)
    }
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(binding.root)
        fetchWeatherData("moscow")
        SearchCity()
    }
    private fun SearchCity() {
        val searchView = binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }
            override fun onQueryTextChange(p0: String?): Boolean {
                return true
            }
        })
    }



    //получение данных о погоде по запросам
    private fun fetchWeatherData(cityName: String) { //Извлекать данные о погоде
        //подтягиваем объект ретрофит
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())//конвертирует ответ
            .baseUrl("https://api.openweathermap.org/data/2.5/") //то что не меняется
            .build().create(ApiInterface::class.java) //подготовился запрос используя


        //ответ объект
        val response = retrofit.getWeatherData(cityName, "e00951731a91c7b6da22f397302c71ce", "metric")



        response.enqueue(object : Callback<WeatherApp> { //раскладывается по полочкам
            //запрос будет в отдельном потоке
            //Callback<WeatherApp> - обратный ответ с сервера
            //вернется список объектов с типом WeatherApp
            //если успешный ответ
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody = //По умолчанию Retrofit позволяет нам работать только с объектами RequestBody и ResponseBody OkHttp .
                    response.body() //сам ответ в виде строки, без сериализации
                if (response.isSuccessful && responseBody != null) { //метод isSuccessful() для успешной обработки запроса (коды 200хх)
                    //если успешный ответ и ответ не равен null
                    val temperature = responseBody.main.temp.toString() //подтянем из ответа через обобщенного класса температуру в строке
                    val humidity = responseBody.main.humidity
                    val windSpeed = responseBody.wind.speed
                    val sunRise = responseBody.sys.sunrise.toLong()
                    val sunSet = responseBody.sys.sunset.toLong()
                    val seaLevel = responseBody.main.pressure
                    val condition = responseBody.weather.firstOrNull()?.main ?: "unkown"
                    val maxTemp = responseBody.main.temp_max
                    val minTemp = responseBody.main.temp_min
                    binding.temp.text = "$temperature °C" //в поле температуры вставим текст полученных значений
                    binding.weather.text = condition
                    binding.maxTem.text = "Max Temp: $maxTemp °C"
                    binding.minTem.text = "Min Temp: $minTemp °C"
                    binding.humidity.text = "$humidity %"
                    binding.windSpeed.text = "{$windSpeed m/s"
                    binding.sunRise.text = "${time(sunRise)}"
                    binding.sunSet1.text = "${time(sunSet)}"
                    binding.seaLevel.text = "$seaLevel hPa"
                    binding.condition.text = condition
                    binding.day.text = dayName(System.currentTimeMillis())
                    binding.date.text = date()
                    binding.cityName.text = "$cityName"
                        //Log.d("TAG", "onResponse: $temperature")
                    changeImagsAccordingToWeatherCondition(condition)
                }
            }
            //если неуспешный ответ или есть какя то ошибка
            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                TODO("Not yet implemented")
            }
        })
    }




    private fun changeImagsAccordingToWeatherCondition(conditions: String) {
        when (conditions) {
            "Clear Cky", "Sunny", "Clear" -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
            "Party Clouds", "Clouds", "Overcast", "Mist", "Foggy" -> {
                binding.root.setBackgroundResource(R.drawable.colud_background)
                binding.lottieAnimationView.setAnimation(R.raw.cloud) //cloud
            }
            "Light Rain", "Drizzle", "Moderate Rain", "Showers", "Heavy Rain" -> {
                binding.root.setBackgroundResource(R.drawable.rain_background)
                binding.lottieAnimationView.setAnimation(R.raw.rain) //rain
            }
            "Light Snow", "Moderate Snow", "Heavy Snow", "Blizzart" -> {
                binding.root.setBackgroundResource(R.drawable.snow_background)
                binding.lottieAnimationView.setAnimation(R.raw.snow) //snow
            }
            else -> {
                binding.root.setBackgroundResource(R.drawable.sunny_background)
                binding.lottieAnimationView.setAnimation(R.raw.sun)
            }
        }
        binding.lottieAnimationView.playAnimation() //запуск анимации
    }

    private fun time(timestamp: Long): String {
        val sdf = SimpleDateFormat("HH:mm", Locale.getDefault())
        return sdf.format((Date(timestamp * 1000)))
    }


    private fun date(): String {
        val sdf = SimpleDateFormat("MMMMM YYYYY", Locale.getDefault())
        return sdf.format((Date()))
    }


    fun dayName (timestamp: Long) : String {
        val sdf = SimpleDateFormat("EEEE", Locale.getDefault())
        return sdf.format((Date()))
    }
}