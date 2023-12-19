package com.dictionary.me_dictionary

import android.content.Context
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import com.dictionary.me_dictionary.models.Dictionary
import android.widget.Toast
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import java.lang.Exception

class RequestManager(var context: Context) {
    object RequestManager {
        val retrofitInstance: Retrofit?
            get() {
                if (retrofit == null) {
                    retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                }
                return retrofit
            }
    }

    fun getWordMeaning(listener: OnFetchDataListener, word: String?) {
        val retrofit = RequestManager.retrofitInstance!!
        val dictionaryApi = retrofit.create(DictionaryApi::class.java)
        val call = dictionaryApi.getMeanings(word)
        try {
            call.enqueue(object : Callback<List<Dictionary>> {
                override fun onResponse(call: Call<List<Dictionary>>, response: Response<List<Dictionary>>) {
                    if (!response.isSuccessful) {
                        Toast.makeText(context, "An Error Occurred! ${response.code()}", Toast.LENGTH_SHORT).show()
                        return
                    }
                    listener.onFetchData(response.body()!![0], response.message())
                }

                override fun onFailure(call: Call<List<Dictionary>>, t: Throwable) {
                    listener.onError("Request Failed!")
                }
            })
        } catch (e: Exception) {
            e.printStackTrace()
            Toast.makeText(context, "An Error Occurred!", Toast.LENGTH_SHORT).show()
        }
    }

    interface DictionaryApi {
        @GET("entries/en/{word}")
        fun getMeanings(@Path("word") word: String?): Call<List<Dictionary>>
    }

    private companion object {
        const val BASE_URL = "https://api.dictionaryapi.dev/api/v2/"
        var retrofit: Retrofit? = null
    }
}