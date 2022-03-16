package ie.otormaigh.releasefetcher.demo

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ie.otormaigh.releasefetcher.ReleaseFetcher
import ie.otormaigh.releasefetcher.demo.databinding.ActivityMainBinding
import ie.otormaigh.releasefetcher.ui.ReleasesActivity

class MainActivity : AppCompatActivity() {
  private lateinit var binding: ActivityMainBinding

  override fun onCreate(savedInstanceState: Bundle?) {
    super.onCreate(savedInstanceState)
    binding = ActivityMainBinding.inflate(layoutInflater)
    setContentView(binding.root)

    binding.btnShowReleases.setOnClickListener { ReleasesActivity.start(this) }
    binding.btnFetchRelease.setOnClickListener { ReleaseFetcher.schedule(this) }
  }
}