package chain.gravity.gravitysdk.retrofit

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseUrl = "https://devnet.node.gravitychain.xyz"

    val logging = HttpLoggingInterceptor()

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