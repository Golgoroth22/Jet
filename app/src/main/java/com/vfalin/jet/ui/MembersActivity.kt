package com.vfalin.jet.ui

import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.vfalin.jet.R
import com.vfalin.jet.di.Scopes
import com.vfalin.jet.ui.adapters.MembersAdapter
import com.vfalin.jet.utils.injectViewModel
import com.vfalin.jet.viewmodel.MembersActivityViewModel
import com.vfalin.jet.viewmodel.factories.MembersActivityViewModelFactory
import kotlinx.android.synthetic.main.activity_main.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import toothpick.Toothpick
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

class MembersActivity : AppCompatActivity(), CoroutineScope {
    override val coroutineContext: CoroutineContext
        get() = Dispatchers.Main

    @Inject
    lateinit var factory: MembersActivityViewModelFactory
    private lateinit var viewModel: MembersActivityViewModel

    private lateinit var membersRecycler: RecyclerView
    private lateinit var membersAdapter: MembersAdapter
    private lateinit var membersManager: LinearLayoutManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Toothpick.inject(this, Toothpick.openScope(Scopes.APP))
        setContentView(R.layout.activity_main)
        viewModel = injectViewModel(factory)
        initViews()
        initListeners()
        initLiveData()
        viewModel.getMembers()
    }

    private fun initViews() {
        activity_members_toolbar.setTitleTextColor(Color.WHITE)
        membersManager = LinearLayoutManager(this)
        membersAdapter = MembersAdapter()
        membersRecycler = findViewById<RecyclerView>(R.id.activity_members_recycler).apply {
            layoutManager = membersManager
            adapter = membersAdapter
        }
    }

    private fun initListeners() {
        activity_members_recycler_swiperefresh.setOnRefreshListener {
            viewModel.getMembers()
        }
    }

    private fun initLiveData() {
        viewModel.internetLiveData.observe(this, { hasInternetConnection ->
            viewModel.switchInternetConnectionStatus(hasInternetConnection)
            if (hasInternetConnection) {
                viewModel.getMembers()
            }
        })
        viewModel.membersLiveData.observe(this, { uiResponse ->
            if (uiResponse.data != null) {
                membersAdapter.update(uiResponse.data)
                if (uiResponse.data.isEmpty()) {
                    activity_members_info_text.visibility = View.VISIBLE
                } else {
                    activity_members_info_text.visibility = View.GONE
                }
            }
            if (uiResponse.error != null) {
                Snackbar.make(
                    activity_members_root_layout,
                    uiResponse.error.localizedMessage,
                    Snackbar.LENGTH_LONG
                ).show()
            }
            if (uiResponse.isLoading) {
                activity_members_recycler_swiperefresh.isRefreshing = true
                activity_members_info_text.visibility = View.GONE
            } else {
                activity_members_recycler_swiperefresh.isRefreshing = false
            }
        })
    }
}