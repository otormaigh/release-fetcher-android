package ie.otormaigh.releasefetcher

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import ie.otormaigh.releasefetcher.persistance.columnadapter.BooleanColumnAdapter
import ie.otormaigh.releasefetcher.persistance.columnadapter.ListAssetColumnAdapter

class ReleaseFetcher(private val context: Context) {
  private val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "release_fetcher.db")
  private val database = Database(
    driver = driver,
    releaseAdapter = Release.Adapter(
      draftAdapter = BooleanColumnAdapter(),
      preReleaseAdapter = BooleanColumnAdapter(),
      assetsAdapter = ListAssetColumnAdapter()
    )
  )
  internal val releaseQueries: ReleaseQueries = database.releaseQueries

  fun fetchOnline() =
    WorkScheduler.oneTimeRequest<ReleaseFetcherWorker>(context)

  fun fetchLocal(): List<Release> =
    releaseQueries.fetchAll().executeAsList()
}