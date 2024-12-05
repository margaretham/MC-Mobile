package com.capstone.maternalcare.ui.history

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.maternalcare.adapter.HistoryAdapter
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.ActivityHistoryBinding
import com.capstone.maternalcare.ui.login.LoginActivity
import com.capstone.maternalcare.util.ViewModelFactory

private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)

class HistoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityHistoryBinding
    private lateinit var viewModel: HistoryViewModel
    private var adapter = HistoryAdapter()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHistoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        setupViewModel()
        setupView()
        setupAction()

    }

    private fun setupView() {
        supportActionBar?.hide()
        showLoading()
    }

    private fun setupViewModel() {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(dataStore))
        )[HistoryViewModel::class.java]

        viewModel.getUser().observe(this){
            if (!it.isLogin){
                val i = Intent(this, LoginActivity::class.java)
                i.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                startActivity(i)
            } else {
                viewModel.setHistory(it.id)
            }
        }

        with(binding) {
            rvHistory.layoutManager = LinearLayoutManager(this@HistoryActivity)
            rvHistory.setHasFixedSize(true)
            rvHistory.adapter = adapter
        }

        viewModel.getHistory().observe(this){
            adapter.setHistory(it)
            adapter.notifyDataSetChanged()
        }
    }

    private fun setupAction() {
        binding.apply {
            ivAvatar.setOnClickListener{
                onBackPressed()
            }
        }

    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }

    override fun onResume() {
        viewModel.getHistory().observe(this){
            adapter.setHistory(it)
            adapter.notifyDataSetChanged()
        }
        super.onResume()
    }

    private fun showLoading() {
        viewModel.isLoading.observe(this) {
            binding.apply {
                when {
                    it -> progressBar.visibility = View.VISIBLE
                    else -> progressBar.visibility = View.INVISIBLE
                }
            }
        }
    }
}