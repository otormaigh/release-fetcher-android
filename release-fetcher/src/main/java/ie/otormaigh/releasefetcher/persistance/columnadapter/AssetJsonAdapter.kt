@file:Suppress(
  "DEPRECATION", "unused", "ClassName", "REDUNDANT_PROJECTION",
  "RedundantExplicitType", "LocalVariableName", "RedundantVisibilityModifier",
  "PLATFORM_CLASS_MAPPED_TO_KOTLIN", "IMPLICIT_NOTHING_TYPE_ARGUMENT_IN_RETURN_POSITION"
)

package ie.otormaigh.releasefetcher.persistance.columnadapter

import com.squareup.moshi.*
import com.squareup.moshi.internal.Util
import ie.otormaigh.releasefetcher.Asset
import kotlin.Long
import kotlin.String

internal class AssetJsonAdapter : JsonAdapter<Asset>() {
  private val moshi: Moshi = Moshi.Builder().build()
  private val options: JsonReader.Options = JsonReader.Options.of(
    "id", "url", "name",
    "content_type", "state", "size", "createdAt", "updated_at", "browser_download_url"
  )

  private val longAdapter: JsonAdapter<Long> = moshi.adapter(Long::class.java, emptySet(), "id")

  private val stringAdapter: JsonAdapter<String> = moshi.adapter(
    String::class.java, emptySet(),
    "url"
  )

  private val nullableStringAdapter: JsonAdapter<String?> = moshi.adapter(
    String::class.java,
    emptySet(), "createdAt"
  )

  public override fun toString(): String = buildString(35) {
    append("GeneratedJsonAdapter(").append("AssetResponse").append(')')
  }

  @FromJson
  public override fun fromJson(reader: JsonReader): Asset {
    var id: Long? = null
    var url: String? = null
    var name: String? = null
    var contentType: String? = null
    var state: String? = null
    var size: Long? = null
    var createdAt: String? = null
    var updatedAt: String? = null
    var browserDownloadUrl: String? = null
    reader.beginObject()
    while (reader.hasNext()) {
      when (reader.selectName(options)) {
        0 -> id = longAdapter.fromJson(reader) ?: throw Util.unexpectedNull("id", "id", reader)
        1 -> url = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("url", "url", reader)
        2 -> name = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
          "name", "name",
          reader
        )
        3 -> contentType = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("contentType", "content_type", reader)
        4 -> state = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
          "state", "state",
          reader
        )
        5 -> size = longAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
          "size", "size",
          reader
        )
        6 -> createdAt = nullableStringAdapter.fromJson(reader)
        7 -> updatedAt = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull(
          "updatedAt",
          "updated_at", reader
        )
        8 -> browserDownloadUrl = stringAdapter.fromJson(reader) ?: throw Util.unexpectedNull("browserDownloadUrl", "browser_download_url", reader)
        -1 -> {
          // Unknown name, skip it.
          reader.skipName()
          reader.skipValue()
        }
      }
    }
    reader.endObject()
    return Asset(
      id = id ?: throw Util.missingProperty("id", "id", reader),
      url = url ?: throw Util.missingProperty("url", "url", reader),
      name = name ?: throw Util.missingProperty("name", "name", reader),
      contentType = contentType ?: throw Util.missingProperty(
        "contentType", "content_type",
        reader
      ),
      state = state ?: throw Util.missingProperty("state", "state", reader),
      size = size ?: throw Util.missingProperty("size", "size", reader),
      createdAt = createdAt,
      updatedAt = updatedAt ?: throw Util.missingProperty("updatedAt", "updated_at", reader),
      browserDownloadUrl = browserDownloadUrl ?: throw Util.missingProperty(
        "browserDownloadUrl",
        "browser_download_url", reader
      )
    )
  }

  @ToJson
  public override fun toJson(writer: JsonWriter, value_: Asset?): Unit {
    if (value_ == null) {
      throw NullPointerException("value_ was null! Wrap in .nullSafe() to write nullable values.")
    }
    writer.beginObject()
    writer.name("id")
    longAdapter.toJson(writer, value_.id)
    writer.name("url")
    stringAdapter.toJson(writer, value_.url)
    writer.name("name")
    stringAdapter.toJson(writer, value_.name)
    writer.name("content_type")
    stringAdapter.toJson(writer, value_.contentType)
    writer.name("state")
    stringAdapter.toJson(writer, value_.state)
    writer.name("size")
    longAdapter.toJson(writer, value_.size)
    writer.name("createdAt")
    nullableStringAdapter.toJson(writer, value_.createdAt)
    writer.name("updated_at")
    stringAdapter.toJson(writer, value_.updatedAt)
    writer.name("browser_download_url")
    stringAdapter.toJson(writer, value_.browserDownloadUrl)
    writer.endObject()
  }
}
