package ie.otormaigh.releasefetcher.extensions

internal fun Boolean?.toLong(): Long = when (this) {
  true -> 1
  false -> 0
  else -> throw java.lang.Exception("")
}