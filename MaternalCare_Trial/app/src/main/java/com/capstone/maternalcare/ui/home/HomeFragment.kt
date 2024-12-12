package com.capstone.maternalcare.ui.home


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.maternalcare.adapter.ListArticleAdapter
import com.capstone.maternalcare.adapter.OnItemClickCallback
import com.capstone.maternalcare.data.model.Article
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.FragmentHomeBinding
import com.capstone.maternalcare.ui.login.LoginViewModel
import com.capstone.maternalcare.ui.recomendations.ArticlesViewModel
import com.capstone.maternalcare.ui.recomendations.DetailArticleActivity
import com.capstone.maternalcare.util.ViewModelFactory


class HomeFragment : Fragment() {


    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val Context.dataStore by preferencesDataStore(name = "user_preferences")


    private lateinit var homeViewModel: HomeViewModel
    private lateinit var articlesViewModel: ArticlesViewModel
    private lateinit var articlesAdapter: ListArticleAdapter


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val pref = UserPreference.getInstance(requireContext().dataStore)
        homeViewModel = ViewModelProvider(this, ViewModelFactory(pref))[HomeViewModel::class.java]


        articlesViewModel = ViewModelProvider(this)[ArticlesViewModel::class.java]
        articlesAdapter = ListArticleAdapter()


        binding.rvRecommendation.apply {
            layoutManager = LinearLayoutManager(context)
            adapter = articlesAdapter
        }


        articlesViewModel.getNews().observe(viewLifecycleOwner) { articlesResponse ->
            articlesResponse?.articles?.let {
                articlesAdapter.setArticles(it)
            }
        }


        articlesAdapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val intent = Intent(context, DetailArticleActivity::class.java)
                intent.putExtra("urlArticle", data.url)
                startActivity(intent)
            }
        })


        articlesViewModel.setNews("Health")


        binding.tvProgressStatus.text = "${homeViewModel.progress.value}%"
        homeViewModel.progress.observe(viewLifecycleOwner) { progress ->
            binding.progressBar1.progress = progress
            binding.tvProgressStatus.text = "$progress%"
        }

        setupViewModel()
    }

    private fun setupViewModel() {
        val viewModel = ViewModelProvider(
            this,
            ViewModelFactory(UserPreference.getInstance(requireContext().dataStore))
        )[LoginViewModel::class.java]

        viewModel.getUser().observe(viewLifecycleOwner) { user ->
            binding.tvUsername.text = user.username
            binding.tvConditions.text = user.predictedRisk
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}