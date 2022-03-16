package ie.otormaigh.releasefetcher.api

import ie.otormaigh.releasefetcher.data.ReleaseResponse
import retrofit2.http.GET
import retrofit2.http.Path

internal interface ReleaseFetcherApi {
  @GET("repos/{user}/{repo}/releases")
  suspend fun getReleases(
    @Path("user") user: String,
    @Path("repo") repo: String,
  ): List<ReleaseResponse>
}