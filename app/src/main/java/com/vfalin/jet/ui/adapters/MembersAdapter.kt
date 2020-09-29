package com.vfalin.jet.ui.adapters

import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.card.MaterialCardView
import com.vfalin.jet.R
import com.vfalin.jet.model.MemberUi
import com.vfalin.jet.network.RetrofitBase
import com.vfalin.jet.utils.Constants

class MembersAdapter : RecyclerView.Adapter<MembersAdapter.MemberViewHolder>() {
    private var adapterList = emptyList<MemberUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MemberViewHolder {
        return MemberViewHolder(
            LayoutInflater.from(parent.context)
                .inflate(R.layout.item_member_layout, parent, false)
        )
    }

    override fun onBindViewHolder(holder: MemberViewHolder, position: Int) {
        holder.bind(adapterList[position])
    }

    override fun getItemCount() = adapterList.size

    fun update(newList: List<MemberUi>) {
        adapterList = newList
        notifyDataSetChanged()
    }

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.item_member_avatar_image)
        private val name: TextView = itemView.findViewById(R.id.item_member_name_text)
        private val statusCard: MaterialCardView =
            itemView.findViewById(R.id.item_member_status_card)
        private val rootLayout: ConstraintLayout =
            itemView.findViewById(R.id.item_member_root_layout)

        fun bind(member: MemberUi) {
            Glide.with(avatar.context)
                .load("${RetrofitBase.BASE_URL}/avatar/${member.username}?format=jpeg")
                .thumbnail(0.5f)
                .error(R.drawable.avatar_error)
                .into(avatar)
            name.text = member.name.trim()
            when (member.status) {
                Constants.MEMBER_STATUS_ONLINE -> {
                    statusCard.setCardBackgroundColor(Color.GREEN)
                    statusCard.visibility = View.VISIBLE
                }
                Constants.MEMBER_STATUS_OFFLINE -> {
                    statusCard.visibility = View.GONE
                }
            }
            rootLayout.setOnClickListener {
                TODO("Переход на экран с подробной информацией")
            }
        }
    }
}