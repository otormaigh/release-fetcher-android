package ie.otormaigh.releasefetcher.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import ie.otormaigh.releasefetcher.Asset
import ie.otormaigh.releasefetcher.ui.databinding.ListItemAssetBinding
import ie.otormaigh.releasefetcher.ui.util.byteToString

internal class AssetRecyclerAdapter : ListAdapter<Asset, AssetRecyclerAdapter.ViewHolder>(diffUtil) {
  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder =
    ViewHolder(ListItemAssetBinding.inflate(LayoutInflater.from(parent.context)))

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    holder.bind(getItem(position))
  }

  inner class ViewHolder(private val binding: ListItemAssetBinding) : RecyclerView.ViewHolder(binding.root) {
    fun bind(item: Asset): Unit = with(binding) {
      tvName.text = item.name
      tvSize.text = item.size.byteToString()
    }
  }

  companion object {
    val diffUtil = object : DiffUtil.ItemCallback<Asset>() {
      override fun areItemsTheSame(oldItem: Asset, newItem: Asset): Boolean =
        oldItem.id == newItem.id

      override fun areContentsTheSame(oldItem: Asset, newItem: Asset): Boolean =
        oldItem == newItem
    }
  }
}