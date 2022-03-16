package ie.otormaigh.releasefetcher.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.otormaigh.releasefetcher.Release
import ie.otormaigh.releasefetcher.databinding.ListItemReleaseBinding

class ReleasesRecyclerAdapter : ListAdapter<Release, ReleasesRecyclerAdapter.ViewHolder>(diffUtil) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) =
    ViewHolder(ListItemReleaseBinding.inflate(LayoutInflater.from(parent.context)))


  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(val binding: ListItemReleaseBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Release) {
      binding.tvTag.text = item.tagName
      binding.tvBody.text = item.body.takeIf { it.isNotEmpty() } ?: "<Empty>"
    }
  }

  companion object {
    val diffUtil = object : DiffUtil.ItemCallback<Release>() {
      override fun areItemsTheSame(oldItem: Release, newItem: Release): Boolean =
        oldItem.id == newItem.id

      override fun areContentsTheSame(oldItem: Release, newItem: Release): Boolean =
        oldItem == newItem
    }
  }
}