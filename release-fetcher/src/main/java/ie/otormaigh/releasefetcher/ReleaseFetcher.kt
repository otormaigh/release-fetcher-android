package ie.otormaigh.releasefetcher

import android.content.Context
import androidx.work.Data
import ie.otormaigh.releasefetcher.persistance.Persistence

class ReleaseFetcher(private val context: Context) {
  private val persistence by lazy { Persistence(context) }

  fun fetchOnline() =
    WorkScheduler.oneTimeRequest<FetchReleasesWorker>(context)

  fun fetchLocal(): List<Release> =
    persistence.releaseQueries.fetchAll().executeAsList()

  fun downloadAsset(assetId: Long?) {
    if (assetId == null) return

    WorkScheduler.oneTimeRequest<FetchAssetWorker>(
      context,
      Data.Builder()
        .putLong(FetchAssetWorker.ASSET_ID, assetId)
        .build()
    )
  }
}