package ie.otormaigh.releasefetcher

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ie.otormaigh.releasefetcher.api.ReleaseFetcherApiClient
import ie.otormaigh.releasefetcher.data.ReleaseResponse.Companion.toEntity

internal class ReleaseFetcherWorker(context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {
  private val releaseFetcher by lazy { ReleaseFetcher(context) }

  override suspend fun doWork(): Result {
    try {
      val releases = ReleaseFetcherApiClient()
        .instance
        .getReleases("otormaigh", "lazyotp-android")
      releases.forEach { releaseFetcher.releaseQueries.insert(it.toEntity()) }

      Log.e("ReleaseFetcher", "Releases -> ${releases.count()}")
    } catch (e: Exception) {
      Log.e("ReleaseFetcher", e.stackTraceToString())
      return Result.failure()
    }

    return Result.success()
  }
}