package ie.otormaigh.releasefetcher.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass
import ie.otormaigh.releasefetcher.Asset

@JsonClass(generateAdapter = true)
internal data class AssetResponse(
  val id: Long,
  val url: String,
  val name: String,
  @Json(name = "content_type")
  val contentType: String,
  val state: String,
  val size: Long,
  val createdAt: String?, // "2021-08-03T21:06:18Z"
  @Json(name = "updated_at")
  val updatedAt: String, // "2021-08-03T21:06:18Z"
  @Json(name = "browser_download_url")
  val browserDownloadUrl: String
) {

  companion object {
    fun AssetResponse.toEntity() = Asset(
      id = id,
      url = url,
      name = name,
      contentType = contentType,
      state = state,
      size = size,
      createdAt = createdAt,
      updatedAt = updatedAt,
      browserDownloadUrl = browserDownloadUrl
    )
  }
}