package ie.otormaigh.releasefetcher.persistance.columnadapter

import com.squareup.sqldelight.ColumnAdapter
import ie.otormaigh.releasefetcher.extensions.toBoolean
import ie.otormaigh.releasefetcher.extensions.toLong

internal class BooleanColumnAdapter : ColumnAdapter<Boolean, Long> {
  override fun decode(databaseValue: Long): Boolean =
    databaseValue.toBoolean()

  override fun encode(value: Boolean): Long =
    value.toLong()
}