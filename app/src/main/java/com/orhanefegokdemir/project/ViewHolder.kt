package com.orhanefegokdemir.project

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.orhanefegokdemir.project.syste.Supplement

class MyViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
    val titleTextView: TextView = itemView.findViewById(R.id.textViewTitle)
    val imageViewSupplement: ImageView = itemView.findViewById(R.id.imageViewSupplement)

    fun bind(item: Supplement) {
        titleTextView.text = item.title
        val imageIdentifier = item.imageResId
        val imageResId = getImageResource(imageIdentifier)
        imageViewSupplement.setImageResource(imageResId)
    }

    private fun getImageResource(imageIdentifier: Int): Int {
        // Implement logic to map imageIdentifier to drawable resource
        // This is just an example, you would replace it with your actual logic
        return when (imageIdentifier) {
            1 -> R.drawable.pre_workout
            2 -> R.drawable.l_carnitine
            // ... add cases for other image identifiers
            else -> R.drawable.pre_workout // Replace with your default image
        }
    }
}
