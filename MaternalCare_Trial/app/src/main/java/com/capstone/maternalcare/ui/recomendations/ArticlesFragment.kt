package com.capstone.maternalcare.ui.recomendations


import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.capstone.maternalcare.adapter.ListArticleAdapter
import com.capstone.maternalcare.adapter.OnItemClickCallback
import com.capstone.maternalcare.data.model.Article
import com.capstone.maternalcare.data.model.UserPreference
import com.capstone.maternalcare.databinding.FragmentArticlesBinding
import com.capstone.maternalcare.util.DEFAULT_QUERY_ARTICLES
import com.capstone.maternalcare.util.ViewModelFactory


private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(
    name = "settings"
)


class ArticlesFragment : Fragment() {
    private var _binding: FragmentArticlesBinding? = null
    private val binding get() = _binding!!
    private lateinit var viewModel: ArticlesViewModel
    private var adapter = ListArticleAdapter()


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentArticlesBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(
            this,
            ViewModelFactory(
                UserPreference.getInstance(requireContext().dataStore)
            )
        )[ArticlesViewModel::class.java]
        showLoading()


        with(binding) {
            rvArticles.layoutManager = LinearLayoutManager(context)
            rvArticles.setHasFixedSize(true)
            rvArticles.adapter = adapter
        }


        viewModel.setNews(DEFAULT_QUERY_ARTICLES)
        viewModel.getNews().observe(viewLifecycleOwner) {
            it?.articles?.let { articles ->
                adapter.setArticles(articles)
                adapter.notifyDataSetChanged()
            }
        }


        adapter.setOnItemClickCallback(object : OnItemClickCallback {
            override fun onItemClicked(data: Article) {
                val intent = Intent(context, DetailArticleActivity::class.java)
                intent.putExtra("urlArticle", data.url)
                startActivity(intent)
            }
        })


        searchView()
    }


    private fun searchView() {
        binding.svArticle.isSubmitButtonEnabled = true
        binding.svArticle.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                query?.let {
                    viewModel.setNews(it)
                }
                return false
            }


            override fun onQueryTextChange(query: String?): Boolean {
                return false
            }
        })
    }


    private fun showLoading() {
        viewModel.isLoading.observe(viewLifecycleOwner) {
            binding.progressBar.visibility = if (it) View.VISIBLE else View.INVISIBLE
        }
    }
}