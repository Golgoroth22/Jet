package com.vfalin.jet.ui.adapters

import android.content.Intent
import android.graphics.Color
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.google.android.material.button.MaterialButton
import com.google.android.material.card.MaterialCardView
import com.vfalin.jet.R
import com.vfalin.jet.model.MemberUi
import com.vfalin.jet.network.RetrofitBase
import com.vfalin.jet.ui.MemberDetailsActivity
import com.vfalin.jet.utils.Constants

class MembersAdapter(private val loadMoreMembersListener: () -> Unit) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var adapterList = mutableListOf<MemberUi>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        return when (viewType) {
            MembersAdapterItem.MEMBER.ordinal -> MemberViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_member_layout, parent, false)
            )
            else -> IncreaseMembersViewHolder(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_increase_members_layout, parent, false)
            )
        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (holder.itemViewType) {
            MembersAdapterItem.MEMBER.ordinal -> (holder as MemberViewHolder).bind(adapterList[position])
            MembersAdapterItem.INCREASE_BUTTON.ordinal -> (holder as IncreaseMembersViewHolder).bind()
        }
    }

    override fun getItemCount() = adapterList.size

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            adapterList.size - 1 -> MembersAdapterItem.INCREASE_BUTTON.ordinal
            else -> MembersAdapterItem.MEMBER.ordinal
        }
    }

    fun update(newList: List<MemberUi>) {
        adapterList = newList.toMutableList()
        adapterList.add(MemberUi())
        notifyDataSetChanged()
    }

    class MemberViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val avatar: ImageView = itemView.findViewById(R.id.item_member_avatar_image)
        private val name: TextView = itemView.findViewById(R.id.item_member_name_text)
        private val statusCard: MaterialCardView =
            itemView.findViewById(R.id.item_member_status_card)
        private val button: MaterialButton =
            itemView.findViewById(R.id.item_member_root_button)

        fun bind(member: MemberUi) {
            Glide.with(avatar.context)
                .load("${RetrofitBase.BASE_URL}/avatar/${member.username}?format=jpeg")
                .thumbnail(0.3f)
                .error(R.drawable.avatar_error)
                .into(avatar)
            name.text = member.name ?: "-"
            when (member.status) {
                Constants.MEMBER_STATUS_ONLINE -> {
                    statusCard.setCardBackgroundColor(Color.GREEN)
                    statusCard.visibility = View.VISIBLE
                }
                Constants.MEMBER_STATUS_OFFLINE -> {
                    statusCard.visibility = View.GONE
                }
            }
            button.setOnClickListener {
                val intent = Intent(it.context, MemberDetailsActivity::class.java).apply {
                    putExtra(MemberDetailsActivity.MEMBER_ID_TAG, member.id)
                }
                it.context.startActivity(intent)
            }
        }
    }

    inner class IncreaseMembersViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val button: MaterialButton =
            itemView.findViewById(R.id.item_increase_members_button)

        fun bind() {
            if (adapterList.size > 1) {
                button.visibility = View.VISIBLE
                button.setOnClickListener {
                    loadMoreMembersListener.invoke()
                    it.visibility = View.GONE
                }
            } else {
                button.visibility = View.GONE
            }
        }
    }

    enum class MembersAdapterItem {
        MEMBER, INCREASE_BUTTON
    }
}