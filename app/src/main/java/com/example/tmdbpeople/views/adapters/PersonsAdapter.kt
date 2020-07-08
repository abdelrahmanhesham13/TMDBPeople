package com.example.tmdbpeople.views.adapters

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.ViewHolder
import com.example.tmdbpeople.R
import com.example.tmdbpeople.models.PersonModel
import com.example.tmdbpeople.networkutils.Constants
import com.example.tmdbpeople.views.adapters.PersonsAdapter.PersonViewHolder
import com.squareup.picasso.Picasso
import java.util.*
import kotlin.collections.ArrayList

class PersonsAdapter(
    var mPersonModels: ArrayList<PersonModel>,
    var mContext: Context,
    var onItemClicked: OnItemClicked
) : RecyclerView.Adapter<PersonViewHolder>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PersonViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.person_item, parent, false)
        return PersonViewHolder(view)
    }

    override fun onBindViewHolder(holder: PersonViewHolder, position: Int) {
        if (position == mPersonModels.size - 1) {
            onItemClicked.onLoadMore()
        }
        val personModel = mPersonModels[position]
        holder.personName.text = personModel.name
        Picasso.get()
            .load(Constants.IMAGE_BASE_URL_500W + personModel.profilePath)
            .into(holder.personImage)
    }

    override fun getItemCount(): Int {
        return mPersonModels.size
    }

    inner class PersonViewHolder(itemView: View) :
        ViewHolder(itemView), View.OnClickListener {
        var personImage: ImageView = itemView.findViewById(R.id.person_image)
        var personName: TextView = itemView.findViewById(R.id.person_name)
        override fun onClick(v: View) {
            onItemClicked.onItemClicked(adapterPosition)
        }

    }

    fun addItems(personList : ArrayList<PersonModel>) {
        mPersonModels.addAll(personList)
        notifyDataSetChanged()
    }

    interface OnItemClicked {
        fun onItemClicked(position: Int)
        fun onLoadMore()
    }

}