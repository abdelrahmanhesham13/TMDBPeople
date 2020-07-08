package com.example.tmdbpeople.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.PersonItemBinding
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.views.adapters.PersonAdapter.PersonViewHolder
import com.squareup.picasso.Picasso

class PersonAdapter(private val mCtx: Context) :
    PagedListAdapter<PersonModel, PersonViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view = DataBindingUtil.inflate<PersonItemBinding>(
            LayoutInflater.from(parent.context),
            R.layout.person_item,
            parent,
            false
        )
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        val person = getItem(position)
        if (person != null) {
            holder.personItemBinding.personName.text = person.name
            Picasso.get()
                .load(Constants.IMAGE_BASE_URL_500W + person.profilePath)
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.personItemBinding.personImage)
        }
    }

    inner class PersonViewHolder(var personItemBinding: PersonItemBinding) :
        ViewHolder(personItemBinding.root)

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PersonModel> =
            object : DiffUtil.ItemCallback<PersonModel>() {
                override fun areItemsTheSame(
                    oldItem: PersonModel,
                    newItem: PersonModel
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: PersonModel,
                    newItem: PersonModel
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

}