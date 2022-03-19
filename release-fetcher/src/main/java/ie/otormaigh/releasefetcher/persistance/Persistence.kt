package ie.otormaigh.releasefetcher.persistance

import android.content.Context
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import ie.otormaigh.releasefetcher.AssetQueries
import ie.otormaigh.releasefetcher.Database
import ie.otormaigh.releasefetcher.Release
import ie.otormaigh.releasefetcher.ReleaseQueries
import ie.otormaigh.releasefetcher.persistance.columnadapter.BooleanColumnAdapter
import ie.otormaigh.releasefetcher.persistance.columnadapter.ListAssetColumnAdapter

internal class Persistence(context: Context) {
  private val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, context, "release_fetcher.db")
  private val database = Database(
    driver = driver,
    releaseAdapter = Release.Adapter(
      draftAdapter = BooleanColumnAdapter(),
      preReleaseAdapter = BooleanColumnAdapter(),
      assetsAdapter = ListAssetColumnAdapter()
    )
  )
  val assetQueries: AssetQueries = database.assetQueries
  val releaseQueries: ReleaseQueries = database.releaseQueries
}