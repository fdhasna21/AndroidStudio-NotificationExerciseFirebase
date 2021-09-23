package com.tugas.notificationexercise.utils

import com.google.gson.GsonBuilder
import com.tugas.notificationexercise.BuildConfig
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.Request
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class ServerAPI {
    private var retrofit : Retrofit? = null
    private var httpClient = OkHttpClient.Builder()

    companion object{
        const val BASE_URL = "https://fcm.googleapis.com"
        const val SERVER_KEY = "key=${BuildConfig.SERVER_KEY}"
        const val CONTENT_TYPE = "application/json"
    }

    fun getServerAPI() : Retrofit {
        if(retrofit == null){
            httpClient.addInterceptor(Interceptor { chain ->
                val original: Request = chain.request()

                val request = original.newBuilder()
                    .header("Authorization", SERVER_KEY)
                    .header("Content-Type", CONTENT_TYPE)
                    .method(original.method, original.body)
                    .build()
                chain.proceed(request)
            })

            val gson = GsonBuilder()
                .setLenient()
                .create()

            val client = httpClient.build()
            retrofit = Retrofit.Builder()
                .client(client)
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build()
        }
        return retrofit!!
    }

}