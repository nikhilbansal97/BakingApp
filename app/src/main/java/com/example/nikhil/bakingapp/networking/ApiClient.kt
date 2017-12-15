package com.example.nikhil.bakingapp.networking

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by NIKHIL on 14-12-2017.
 */

class ApiClient {

    companion object {

    var BASE_URL = "https://d17h27t6h515a5.cloudfront.net/topher/2017/May/59121517_baking/"

    var retrofit: Retrofit? = null

        fun getClient(): Retrofit {
            if(retrofit == null)
                retrofit = Retrofit.Builder()
                        .baseUrl(BASE_URL)
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
            return retrofit!!
        }
    }
}
