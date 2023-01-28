package com.example.galaxy.dashboard

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.galaxy.data.entity.MemberInfo
import com.example.galaxy.databinding.LayoutMembersListItemBinding

class MembersListAdapter :
    RecyclerView.Adapter<MembersListAdapter.ViewHolder>() {

    private var mList = ArrayList<MemberInfo>()

    class ViewHolder(private val binding: LayoutMembersListItemBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun setData(data: MemberInfo) {
            binding.itemData = data
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val view = LayoutMembersListItemBinding.inflate(inflater, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.setData(mList[position])
    }

    override fun getItemCount(): Int {
        return mList.size
    }

    // TODO: replace with ListAdapter to avoid this
    fun setData(data: List<MemberInfo>) {
        mList.clear()
        mList.addAll(data)
        notifyDataSetChanged()
    }
}