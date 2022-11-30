package xyz.gravitychain.gravitysdk.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    private const val baseUrl = "https://devnet.node.gravitychain.xyz"

    private val logging = HttpLoggingInterceptor()

    fun getInstance(): Retrofit {
        logging.level = HttpLoggingInterceptor.Level.BODY;
        return Retrofit.Builder().baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .addCallAdapterFactory(ResultCallAdapterFactory())
            .client(
                OkHttpClient.Builder()
                    .addInterceptor(logging)
                    .build()
            )
            .build()
    }
}