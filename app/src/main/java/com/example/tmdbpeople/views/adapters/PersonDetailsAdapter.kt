package com.example.tmdbpeople.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.PersonDetailsItemBinding
import com.example.tmdbpeople.databinding.PersonImageItemBinding
import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.views.adapters.PersonAdapter.OnItemClicked
import com.squareup.picasso.Picasso


class PersonDetailsAdapter(private val context: Context,private var personImages : ArrayList<PersonImage>,
                           private var personDetailsResponse: PersonDetailsResponse?,
    private val onItemClicked: OnItemClicked) : RecyclerView.Adapter<ViewHolder>() {
    fun setPersonDetailsResponse(personDetailsResponse: PersonDetailsResponse?) {
        this.personDetailsResponse = personDetailsResponse
        if (personImages.size > 0) {
            personImages[0] = PersonImage()
        } else {
            personImages.add(PersonImage())
        }
        notifyItemChanged(0)
    }

    fun addImages(images : ArrayList<PersonImage>) {
        personImages.addAll(images)
        notifyDataSetChanged()
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            DETAILS_VIEW_TYPE
        } else {
            IMAGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        if (viewType == DETAILS_VIEW_TYPE) {
            val personDetailsItemBinding =
                DataBindingUtil.inflate<PersonDetailsItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.person_details_item,
                    parent,
                    false
                )
            return PersonDetailsViewHolder(personDetailsItemBinding)
        } else {
            val personImageItemBinding =
                DataBindingUtil.inflate<PersonImageItemBinding>(
                    LayoutInflater.from(parent.context),
                    R.layout.person_image_item,
                    parent,
                    false
                )
            return PersonImageViewHolder(personImageItemBinding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == DETAILS_VIEW_TYPE) {
            val detailsViewHolder = holder as PersonDetailsViewHolder
            detailsViewHolder.binding.person = personDetailsResponse
            if (personDetailsResponse?.gender == 1) {
                detailsViewHolder.binding.personGender.setText(R.string.female)
            } else {
                detailsViewHolder.binding.personGender.setText(R.string.male)
            }
            Picasso.get()
                .load(Constants.IMAGE_BASE_URL_500W + personDetailsResponse?.profilePath)
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.binding.personImage)
        } else {
            val imageViewHolder = holder as PersonImageViewHolder
            Picasso.get()
                .load(Constants.IMAGE_BASE_URL_500W + personImages.get(position).filePath)
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(imageViewHolder.binding.personImage)
        }
    }

    override fun getItemCount(): Int {
        return personImages.size
    }

    inner class PersonDetailsViewHolder(var binding: PersonDetailsItemBinding) : ViewHolder(binding.root)

    inner class PersonImageViewHolder(var binding: PersonImageItemBinding) : ViewHolder(binding.root) {
        init {
            val metrics = context.resources.displayMetrics
            val width = metrics.widthPixels
            val height = metrics.heightPixels
            binding.root.layoutParams.width = (width / 2) - 10
        }
    }

    companion object {
        const val DETAILS_VIEW_TYPE = 1
        const val IMAGE_VIEW_TYPE = 2
    }

}