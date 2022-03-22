package ie.otormaigh.releasefetcher

import android.content.Context
import android.content.Intent
import android.util.Log
import androidx.core.content.FileProvider
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ie.otormaigh.releasefetcher.persistance.Persistence
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.logging.HttpLoggingInterceptor
import okio.buffer
import okio.sink
import java.io.File

internal class FetchAssetWorker(context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {
  private val persistence by lazy { Persistence(context) }

  override suspend fun doWork(): Result = withContext(Dispatchers.IO) {
    try {
      val assetId = inputData.getLong(ASSET_ID, -1)
      val asset = persistence.assetQueries.fetch(assetId).executeAsOneOrNull()
      if (asset == null) {
        Log.e("FetchAssetWorker", "asset is null")
        return@withContext Result.failure()
      }

      val file = File(applicationContext.filesDir, asset.name)
      if (!file.exists()) {
        val client = OkHttpClient.Builder()
          .addNetworkInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
          })
          .build()

        val request = Request.Builder()
          .url(asset.browserDownloadUrl)
          .build()
        val response = client.newCall(request).execute()

        if (response.body == null) {
          // TODO: Better error handling
          return@withContext Result.failure()
        } else {
          file.sink().buffer().use { sink ->
            sink.writeAll(response.body!!.source())
          }
        }
      }

      val uri = FileProvider.getUriForFile(applicationContext, BuildConfig.PROVIDER_AUTHORITY, file)
      val intent = Intent(Intent.ACTION_VIEW).apply {
        setDataAndType(uri, asset.contentType)
        flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_GRANT_READ_URI_PERMISSION
      }
      applicationContext.startActivity(intent, null)

      return@withContext Result.success()
    } catch (e: Exception) {
      Log.e("FetchAssetWorker", e.stackTraceToString())
      return@withContext Result.failure()
    }
  }

  companion object {
    const val ASSET_ID = "asset_id"
  }
}