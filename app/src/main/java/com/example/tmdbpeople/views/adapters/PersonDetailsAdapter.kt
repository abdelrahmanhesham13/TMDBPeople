package com.example.tmdbpeople.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.PersonDetailsItemBinding
import com.example.tmdbpeople.databinding.PersonImageItemBinding
import com.example.tmdbpeople.models.PersonImage
import com.example.tmdbpeople.models.PersonModel
import com.squareup.picasso.Picasso
import javax.inject.Inject


class PersonDetailsAdapter() : RecyclerView.Adapter<ViewHolder>() {

    private lateinit var context: Context
    private lateinit var personImages : ArrayList<PersonImage>
    private var personModel: PersonModel? = null
    private lateinit var onImageClicked: OnImageClicked

    @Inject
    constructor(context: Context, personImages : ArrayList<PersonImage>, personModel: PersonModel?, onImageClicked: OnImageClicked) : this() {
        this.context = context
        this.personImages = personImages
        this.personModel = personModel
        this.onImageClicked = onImageClicked
    }

    //Add person details cell
    fun setPersonDetailsResponse(personModel: PersonModel?) {
        this.personModel = personModel
        notifyItemChanged(0)
    }

    fun addImages(images : ArrayList<PersonImage>) {
        personImages.addAll(images)
        notifyItemRangeInserted(1,images.size)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            DETAILS_VIEW_TYPE
        } else {
            IMAGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return if (viewType == DETAILS_VIEW_TYPE) {
            val personDetailsItemBinding = DataBindingUtil.inflate<PersonDetailsItemBinding>(LayoutInflater.from(parent.context), R.layout.person_details_item, parent, false)
            PersonDetailsViewHolder(personDetailsItemBinding)
        } else {
            val personImageItemBinding = DataBindingUtil.inflate<PersonImageItemBinding>(LayoutInflater.from(parent.context), R.layout.person_image_item, parent, false)
            PersonImageViewHolder(personImageItemBinding)
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        if (getItemViewType(position) == DETAILS_VIEW_TYPE) {
            val detailsViewHolder = holder as PersonDetailsViewHolder
            detailsViewHolder.binding.person = personModel
            Picasso.get().load(personModel?.getImageFullPath())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(detailsViewHolder.binding.personImage)
        } else {
            val imageViewHolder = holder as PersonImageViewHolder
            Picasso.get().load(personImages[position].getImageFullPath())
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(imageViewHolder.binding.personImage)
        }
    }

    override fun getItemCount(): Int {
        return personImages.size
    }

    inner class PersonDetailsViewHolder(var binding: PersonDetailsItemBinding) : ViewHolder(binding.root)

    inner class PersonImageViewHolder(var binding: PersonImageItemBinding) : ViewHolder(binding.root) ,View.OnClickListener{
        init {
            //Width of every image in grid to be half of screen width
            val metrics = context.resources.displayMetrics
            val width = metrics.widthPixels
            binding.root.layoutParams.width = (width / 2) - 10
            binding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onImageClicked.onImageClicked(personImages.get(adapterPosition).filePath)
        }
    }

    companion object {
        const val DETAILS_VIEW_TYPE = 1
        const val IMAGE_VIEW_TYPE = 2
    }

    interface OnImageClicked {
        fun onImageClicked(image : String?)
    }

}