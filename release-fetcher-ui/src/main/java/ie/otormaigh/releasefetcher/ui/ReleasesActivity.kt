package ie.otormaigh.releasefetcher.ui

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.otormaigh.releasefetcher.ReleaseFetcher
import ie.otormaigh.releasefetcher.ui.databinding.ActivityReleasesBinding

class ReleasesActivity : AppCompatActivity() {
  private lateinit var binding: ActivityReleasesBinding
  private val recyclerAdapter by lazy { ReleasesRecyclerAdapter() }
  private val releaseFetcher by lazy { ReleaseFetcher(this) }

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityReleasesBinding.inflate(layoutInflater)
    setContentView(binding.root)

    setupRecyclerView()

    recyclerAdapter.submitList(releaseFetcher.fetchLocal())
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