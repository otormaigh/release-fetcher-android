package ie.otormaigh.releasefetcher

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import ie.otormaigh.releasefetcher.api.ReleaseFetcherApiClient
import ie.otormaigh.releasefetcher.data.ReleaseResponse.Companion.toEntity
import ie.otormaigh.releasefetcher.persistance.Persistence

internal class FetchReleasesWorker(context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {
  private val persistence by lazy { Persistence(context) }

  override suspend fun doWork(): Result {
    try {
      val releases = ReleaseFetcherApiClient()
        .instance
        .getReleases("otormaigh", "lazyotp-android")
      releases.forEach { releaseResponse ->
        val releaseEntity = releaseResponse.toEntity()
        persistence.releaseQueries.insert(releaseEntity)
        releaseEntity.assets.forEach { assetEntity -> persistence.assetQueries.insert(assetEntity) }
      }

      Log.e("ReleaseFetcher", "Releases -> ${releases.count()}")
    } catch (e: Exception) {
      Log.e("ReleaseFetcher", e.stackTraceToString())
      return Result.failure()
    }

    return Result.success()
  }
}