package com.vfalin.jet.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.google.android.material.snackbar.Snackbar
import com.vfalin.jet.R
import com.vfalin.jet.di.Scopes
import com.vfalin.jet.network.RetrofitBase
import com.vfalin.jet.utils.injectViewModel
import com.vfalin.jet.viewmodel.MemberDetailsActivityViewModel
import com.vfalin.jet.viewmodel.factories.MemberDetailsActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_member_details.*
import toothpick.Toothpick
import javax.inject.Inject

class MemberDetailsActivity : AppCompatActivity() {
    @Inject
    lateinit var factory: MemberDetailsActivityViewModelFactory
    private lateinit var viewModel: MemberDetailsActivityViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP))
        setContentView(R.layout.activity_member_details)
        viewModel = injectViewModel(factory)
        initToolbar()
        initLiveData()
        intent.extras?.getString(MEMBER_ID_TAG)?.let { viewModel.getMemberDetails(it) }
    }

    private fun initToolbar() {
        setSupportActionBar(activity_member_details_toolbar)
        activity_member_details_toolbar.setTitleTextColor(Color.WHITE)
        activity_member_details_toolbar.setNavigationOnClickListener {
            onBackPressed()
        }
    }

    private fun initLiveData() {
        viewModel.memberLiveData.observe(this, { memberDetails ->
            if (memberDetails.data != null) {
                Glide.with(this)
                    .load("${RetrofitBase.BASE_URL}/avatar/${memberDetails.data.username}?format=jpeg")
                    .thumbnail(0.5f)
                    .error(R.drawable.avatar_error)
                    .into(activity_member_details_avatar_image)
                activity_member_details_name_text.text = memberDetails.data.name
                activity_member_details_id_text.text = memberDetails.data.id
                activity_member_details_nickname_text.text = memberDetails.data.username ?: "-"
                activity_member_details_status_text.text = memberDetails.data.status
                activity_member_details_offset_text.text =
                    if (memberDetails.data.utcOffset != null) memberDetails.data.utcOffset.toString() else "-"
            }
            if (memberDetails.error != null) {
                Snackbar.make(
                    activity_member_details_root_layout,
                    memberDetails.error.localizedMessage,
                    Snackbar.LENGTH_LONG
                ).show()
            }
        })
    }

    companion object {
        const val MEMBER_ID_TAG = "com.vfalin.jet.ui.MemberDetailsActivity.MEMBER_ID_TAG"
    }
}