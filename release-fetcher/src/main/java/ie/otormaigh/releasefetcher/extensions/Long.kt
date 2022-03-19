package ie.otormaigh.releasefetcher.extensions

internal fun Long?.toBoolean(): Boolean = when (this) {
  0L -> false
  1L -> true
  else -> throw java.lang.Exception("")
}