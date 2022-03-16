package ie.otormaigh.releasefetcher.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.squareup.sqldelight.android.AndroidSqliteDriver
import com.squareup.sqldelight.db.SqlDriver
import ie.otormaigh.releasefetcher.Database
import ie.otormaigh.releasefetcher.ReleaseQueries
import ie.otormaigh.releasefetcher.databinding.ActivityReleasesBinding

class ReleasesActivity : AppCompatActivity() {
  private lateinit var binding: ActivityReleasesBinding
  private val recyclerAdapter by lazy { ReleasesRecyclerAdapter() }
  private val driver: SqlDriver = AndroidSqliteDriver(Database.Schema, this, "release_fetcher.db")
  private val database = Database(driver)
  private val releaseQueries: ReleaseQueries = database.releaseQueries

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityReleasesBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupRecyclerView()

    recyclerAdapter.submitList(releaseQueries.fetchAll().executeAsList())
  }

  private fun setupRecyclerView() {
    binding.recyclerView.adapter = recyclerAdapter
  }

  companion object {
    fun start(activity: AppCompatActivity) {
      activity.startActivity(Intent(activity, ReleasesActivity::class.java))
    }
  }
}