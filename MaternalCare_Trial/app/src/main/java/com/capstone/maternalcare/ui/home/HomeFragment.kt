package com.capstone.maternalcare.ui.home

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.capstone.maternalcare.R
import com.capstone.maternalcare.data.model.UserHome
import com.capstone.maternalcare.databinding.FragmentHomeBinding
import com.capstone.maternalcare.ui.recomendations.DetailArticleActivity

class HomeFragment : Fragment(R.layout.fragment_home) {

    private lateinit var binding: FragmentHomeBinding
    private lateinit var homeViewModel: HomeViewModel
    private lateinit var user: UserHome  // Changed to UserHome

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)
        homeViewModel = ViewModelProvider(this).get(HomeViewModel::class.java)

        user = homeViewModel.getUserData() // Fetch the UserHome data from ViewModel
        updateUI()

        // Set up Recommendations
        binding.cvRecommendations.setOnClickListener {
            // Navigate to the Articles Activity to show the list of articles
            val intent = Intent(requireContext(), DetailArticleActivity::class.java)
            startActivity(intent)
        }

        homeViewModel.progress.observe(viewLifecycleOwner) { progress ->
            binding.progressBar1.progress = progress
            binding.tvProgressStatus.text = "$progress%"
        }

        binding.btnCheck.setOnClickListener {
            // Hbelum ak buat ke history yaww
        }

        return binding.root
    }

    private fun updateUI() {
        binding.tvUsername.text = user.name
        binding.tvBabyAge.text = user.babyAge // Changed to babyAge
    }
}
