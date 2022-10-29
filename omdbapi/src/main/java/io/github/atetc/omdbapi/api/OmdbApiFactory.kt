package io.github.atetc.omdbapi.api

import com.google.gson.GsonBuilder
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object OmdbApiFactory {
    private const val BASE_URL = "https://www.omdbapi.com/"

    fun build(token: String): OmdbApi {
        val gson = GsonBuilder()
            .setLenient()
            .create()

        val loggerInterceptor = HttpLoggingInterceptor().apply {
            setLevel(HttpLoggingInterceptor.Level.BODY)
        }

        val httpClient = OkHttpClient.Builder().apply {
            addInterceptor { chain ->
                val original: Request = chain.request()
                val originalHttpUrl = original.url
                val url = originalHttpUrl.newBuilder()
                    .addQueryParameter("apikey", token)
                    .build()

                val requestBuilder = original.newBuilder()
                    .url(url)
                val request: Request = requestBuilder.build()
                chain.proceed(request)
            }
            addInterceptor(loggerInterceptor)
        }

        val retrofit = Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()

        return retrofit.create(OmdbApi::class.java)
    }
}
