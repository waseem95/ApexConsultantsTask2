package com.task.apexConsultants.ui.fragments

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import apexConsultants.databinding.GuestsItemBinding
import com.task.apexConsultants.Guest

class GuestsAdapter(
    private val guestsList: ArrayList<Guest>? = arrayListOf(),
    private val checkBoxChecked: () -> Unit
) :
    RecyclerView.Adapter<GuestsAdapter.PostsViewHolder>() {
    private var count: Int = 0

    inner class PostsViewHolder(val binding: GuestsItemBinding) :
        RecyclerView.ViewHolder(binding.root)


    override fun getItemCount() = guestsList?.size ?: 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PostsViewHolder {
        return PostsViewHolder(
            GuestsItemBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: PostsViewHolder, position: Int) {
        holder.binding.apply {
            val guest = guestsList?.get(position)
            checkbox.isChecked = guest?.checked == true
            if (guestsList?.get(position)?.type == "title") {
                titleTv.visibility = View.VISIBLE
                checkbox.visibility= View.GONE
                titleTv.text = guestsList[position].name ?: ""
            } else {
                titleTv.visibility = View.GONE
                checkbox.visibility= View.VISIBLE
                checkbox.text = guestsList?.get(position)?.name

            }

            checkbox.setOnCheckedChangeListener { compoundButton, check ->
                guest?.checked = check
                checkBoxChecked.invoke()
            }
        }
    }
}