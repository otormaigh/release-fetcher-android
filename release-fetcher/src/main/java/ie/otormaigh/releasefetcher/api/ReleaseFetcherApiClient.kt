package ie.otormaigh.releasefetcher.api

import com.squareup.moshi.Moshi
import ie.otormaigh.releasefetcher.BuildConfig
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

internal class ReleaseFetcherApiClient {
  private val moshi = Moshi.Builder().build()
  private val okhttp = OkHttpClient.Builder()
    .addNetworkInterceptor(HttpLoggingInterceptor().apply {
      level = /*if (BuildConfig.DEBUG) */HttpLoggingInterceptor.Level.BODY
//      else HttpLoggingInterceptor.Level.NONE
    })
    .build()

  val instance: ReleaseFetcherApi
    get() = Retrofit.Builder()
      .client(okhttp)
      .addConverterFactory(MoshiConverterFactory.create(moshi))
      .baseUrl("https://api.github.com/")
      .build()
      .create(ReleaseFetcherApi::class.java)
}