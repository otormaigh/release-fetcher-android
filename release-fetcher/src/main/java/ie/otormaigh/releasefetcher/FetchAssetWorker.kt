package ie.otormaigh.releasefetcher

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ie.otormaigh.releasefetcher.persistance.Persistence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okio.BufferedSink
import okio.buffer
import okio.sink
import java.io.File

internal class FetchAssetWorker(context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {
  private val persistence by lazy { Persistence(context) }

  override suspend fun doWork(): Result {
    try {
      val assetId = inputData.getLong(ASSET_ID, -1)
      val asset = persistence.assetQueries.fetch(assetId).executeAsOneOrNull()
      if (asset == null) {
        Log.e("FetchAssetWorker", "asset is null")
        return Result.failure()
      }

      val client = OkHttpClient.Builder()
        .addNetworkInterceptor(HttpLoggingInterceptor().apply {
          level = HttpLoggingInterceptor.Level.BODY
        })
        .build()

      val request: Request = Request.Builder()
        .url(asset.browserDownloadUrl)
        .build()
      val response = client.newCall(request).execute()

      withContext(Dispatchers.IO) {
        val downloadedFile = File(applicationContext.cacheDir, asset.name)
        val sink: BufferedSink = downloadedFile.sink().buffer()
        sink.writeAll(response.body!!.source())
        sink.close()

        Log.e("FetchAssetWorker", "file -> $downloadedFile")
      }

      return Result.success()
    } catch (e: Exception) {
      Log.e("FetchAssetWorker", e.stackTraceToString())
      return Result.failure()
    }
  }

  companion object {
    const val ASSET_ID = "asset_id"
  }
}