package com.example.tmdbpeople.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tmdbpeople.R
import com.example.tmdbpeople.databinding.PersonItemBinding
import com.example.tmdbpeople.models.responsemodels.PersonDetailsResponse
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.views.adapters.PersonAdapter.PersonViewHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject


class PersonAdapter() :
    PagedListAdapter<PersonDetailsResponse, PersonViewHolder>(DIFF_CALLBACK) {

    private lateinit var mCtx : Context
    lateinit var onItemClicked : OnItemClicked

    @Inject
    constructor (mCtx: Context, onItemClicked : OnItemClicked) : this() {
        this.mCtx = mCtx
        this.onItemClicked = onItemClicked
    }

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
        holder.personItemBinding.person = person
        if (person != null) {
            Picasso.get()
                .load(Constants.IMAGE_BASE_URL_500W + person.profilePath)
                .placeholder(R.drawable.im_placeholder)
                .error(R.drawable.im_placeholder)
                .into(holder.personItemBinding.personImage)
        }
    }

    inner class PersonViewHolder(var personItemBinding: PersonItemBinding) :
        ViewHolder(personItemBinding.root) , View.OnClickListener {
        init {
           personItemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onItemClicked.onItemClicked(getItem(adapterPosition)?.id)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PersonDetailsResponse> =
            object : DiffUtil.ItemCallback<PersonDetailsResponse>() {
                override fun areItemsTheSame(
                    oldItem: PersonDetailsResponse,
                    newItem: PersonDetailsResponse
                ): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(
                    oldItem: PersonDetailsResponse,
                    newItem: PersonDetailsResponse
                ): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface OnItemClicked {
        fun onItemClicked(id : Int?)
    }

}