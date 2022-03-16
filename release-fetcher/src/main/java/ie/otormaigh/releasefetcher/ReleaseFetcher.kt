package ie.otormaigh.releasefetcher

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.WorkerParameters
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import ie.otormaigh.releasefetcher.api.ReleaseFetcherApiClient

class ReleaseFetcher(context: Context, workerParams: WorkerParameters) :
  CoroutineWorker(context, workerParams) {
  private val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "release_fetcher.db")
  private val database = Database(driver)
  private val releaseQueries: ReleaseQueries = database.releaseQueries

  override suspend fun doWork(): Result {
    try {
      val releases = ReleaseFetcherApiClient()
        .instance
        .getReleases("otormaigh", "lazyotp-android")
      releases.forEach { releaseQueries.insert(Release(id = it.id, tagName = it.tagName, body = it.body)) }

      Log.e("ReleaseFetcher", "Releases -> ${releases.count()}")
    } catch (e: Exception) {
      Log.e("ReleaseFetcher", e.stackTraceToString())
      return Result.failure()
    }

    return Result.success()
  }

  companion object {
    fun schedule(context: Context) {
      WorkScheduler.oneTimeRequest<ReleaseFetcher>(context)
    }
  }
}