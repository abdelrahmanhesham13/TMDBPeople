package com.example.tmdbpeople.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.PersonDetailsItemBinding
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.views.adapters.PersonAdapter.OnItemClicked
import com.example.tmdbpeople.views.adapters.PersonDetailsAdapter.PersonDetailsViewHolder
import com.squareup.picasso.Picasso

class PersonDetailsAdapter(private val context: Context,private var images : ArrayList<String>,
                           private var personDetailsResponse: PersonDetailsResponse?,
    private val onItemClicked: OnItemClicked) : RecyclerView.Adapter<PersonDetailsViewHolder>() {
    fun setPersonDetailsResponse(personDetailsResponse: PersonDetailsResponse?) {
        this.personDetailsResponse = personDetailsResponse
        if (images.size > 0) {
            images[0] = DUMMY_STRING
        } else {
            images.add(DUMMY_STRING)
        }
        notifyItemChanged(0)
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) {
            DETAILS_VIEW_TYPE
        } else {
            IMAGE_VIEW_TYPE
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonDetailsViewHolder {
        val personDetailsItemBinding =
            DataBindingUtil.inflate<PersonDetailsItemBinding>(
                LayoutInflater.from(parent.context),
                R.layout.person_details_item,
                parent,
                false
            )
        return PersonDetailsViewHolder(personDetailsItemBinding)
    }

    override fun onBindViewHolder(holder: PersonDetailsViewHolder, position: Int) {
        if (getItemViewType(position) == DETAILS_VIEW_TYPE) {
            holder.binding.person = personDetailsResponse
            if (personDetailsResponse?.gender == 1) {
                holder.binding.personGender.setText(R.string.female)
            } else {
                holder.binding.personGender.setText(R.string.male)
            }
            Picasso.get()
                .load(Constants.IMAGE_BASE_URL_500W + personDetailsResponse?.profilePath)
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.binding.personImage)
        }
    }

    override fun getItemCount(): Int {
        return images.size
    }

    inner class PersonDetailsViewHolder(var binding: PersonDetailsItemBinding) :
        ViewHolder(binding.root)

    companion object {
        const val DETAILS_VIEW_TYPE = 1
        const val IMAGE_VIEW_TYPE = 2
        const val DUMMY_STRING = "0"
    }

}