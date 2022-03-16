package ie.otormaigh.releasefetcher.data

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class ReleaseResponse(
  val id: Long,
  val url: String,
  @Json(name = "assets_url")
  val assetUrl: String,
  @Json(name = "tag_name")
  val tagName: String,
  val name: String,
  val draft: Boolean,
  @Json(name = "prerelease")
  val preRelease: Boolean,
  @Json(name = "created_at")
  val createdAt: String, // "2021-08-03T21:06:18Z"
  @Json(name = "published_at")
  val publishedAt: String, // "2021-08-03T21:06:18Z"
  @Json(name = "tarball_url")
  val tarballUrl: String,
  @Json(name = "zipball_url")
  val zipballUrl: String,
  val body: String
)



/*  {
    "url": "https://api.github.com/repos/otormaigh/lazyotp-android/releases/47254765",
    "assets_url": "https://api.github.com/repos/otormaigh/lazyotp-android/releases/47254765/assets",
    "id": 47254765,
    "author": {},
    "tag_name": "0.3.3",
    "target_commitish": "master",
    "name": "0.3.3",
    "draft": false,
    "prerelease": false,
    "created_at": "2021-08-03T21:06:18Z",
    "published_at": "2021-08-03T21:14:25Z",
    "assets": [
      {
        "url": "https://api.github.com/repos/otormaigh/lazyotp-android/releases/assets/41674430",
        "id": 41674430,
        "name": "lazyotp-0.3.3-556b846-release.apk",
        "label": null,
        "uploader": {},
        "content_type": "application/vnd.android.package-archive",
        "state": "uploaded",
        "size": 2239217,
        "download_count": 7,
        "created_at": "2021-08-03T21:14:07Z",
        "updated_at": "2021-08-03T21:14:13Z",
        "browser_download_url": "https://github.com/otormaigh/lazyotp-android/releases/download/0.3.3/lazyotp-0.3.3-556b846-release.apk"
      }
    ],
    "tarball_url": "https://api.github.com/repos/otormaigh/lazyotp-android/tarball/0.3.3",
    "zipball_url": "https://api.github.com/repos/otormaigh/lazyotp-android/zipball/0.3.3",
    "body": "* update slack OTP code message to include text field for notification preview"
  },*/