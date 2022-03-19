package ie.otormaigh.releasefetcher.persistance.columnadapter

import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import com.squareup.sqldelight.ColumnAdapter
import ie.otormaigh.releasefetcher.Asset

internal class ListAssetColumnAdapter : ColumnAdapter<List<Asset>, String> {
  private val adapter: JsonAdapter<List<Asset>> by lazy {
    Moshi.Builder()
      .add((AssetJsonAdapter()))
      .build()
      .adapter(Types.newParameterizedType(List::class.java, Asset::class.java))
  }

  override fun decode(databaseValue: String): List<Asset> =
    adapter.fromJson(databaseValue) ?: emptyList()

  override fun encode(value: List<Asset>): String =
    adapter.toJson(value) ?: ""
}