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
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.utils.ImageUtils
import com.example.tmdbpeople.views.adapters.PersonAdapter.PersonViewHolder
import com.squareup.picasso.Picasso
import javax.inject.Inject


class PersonAdapter() : PagedListAdapter<PersonModel, PersonViewHolder>(DIFF_CALLBACK) {

    private lateinit var mCtx: Context
    lateinit var onPersonClicked: OnPersonClicked

    @Inject
    constructor (mCtx: Context, onPersonClicked: OnPersonClicked) : this() {
        this.mCtx = mCtx
        this.onPersonClicked = onPersonClicked
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
    }

    inner class PersonViewHolder(var personItemBinding: PersonItemBinding) :
        ViewHolder(personItemBinding.root), View.OnClickListener {
        init {
            personItemBinding.root.setOnClickListener(this)
        }

        override fun onClick(v: View?) {
            onPersonClicked.onPersonClicked(getItem(adapterPosition)?.id)
        }
    }

    companion object {
        private val DIFF_CALLBACK: DiffUtil.ItemCallback<PersonModel> =
            object : DiffUtil.ItemCallback<PersonModel>() {
                override fun areItemsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
                    return oldItem.id == newItem.id
                }

                override fun areContentsTheSame(oldItem: PersonModel, newItem: PersonModel): Boolean {
                    return oldItem == newItem
                }
            }
    }

    interface OnPersonClicked {
        fun onPersonClicked(id: Int?)
    }

}