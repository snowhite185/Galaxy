package com.example.galaxy.ui.dashboard

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxy.R
import com.example.galaxy.data.entity.Members
import com.example.galaxy.databinding.FragmentDashboardBinding

class MembersListAdapter :
    RecyclerView.Adapter<MembersListAdapter.ViewHolder>() {

    private lateinit var mList: List<Members>

    class ViewHolder(ItemView: View) : RecyclerView.ViewHolder(ItemView) {
        val textView: TextView = itemView.findViewById(R.id.tvMemberName)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        // inflates the card_view_design view
        // that is used to hold list item
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.layout_members_list_item, parent, false)

        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val itemsViewModel = mList[position]
        holder.textView.text = itemsViewModel.name

    }

    override fun getItemCount(): Int {
        return 0
    }

    fun setData(data: List<Members>) {
        mList = data
    }
}