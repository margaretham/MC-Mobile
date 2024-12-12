package com.capstone.maternalcare.adapter


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.capstone.maternalcare.data.model.Article
import com.capstone.maternalcare.databinding.ItemRowArticleBinding


class ListArticleAdapter : RecyclerView.Adapter<ListArticleAdapter.ListViewHolder>() {


    private var listArticle = ArrayList<Article>()
    private lateinit var onItemClickCallback: OnItemClickCallback


    fun setOnItemClickCallback(onItemClickCallback: OnItemClickCallback) {
        this.onItemClickCallback = onItemClickCallback
    }


    fun setArticles(data: List<Article>) {
        this.listArticle.clear()
        this.listArticle.addAll(data)
        notifyDataSetChanged()
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ListViewHolder {
        val binding = ItemRowArticleBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ListViewHolder(binding)
    }


    override fun onBindViewHolder(holder: ListViewHolder, position: Int) {
        val (description, source, title, url) = listArticle[position]


        holder.binding.newsTitle.text = title
        holder.binding.newsText.text = description
        holder.binding.newsSource.text = source


        Glide.with(holder.binding.root)
            .load(url)
            .into(holder.binding.newsImage)


        holder.itemView.setOnClickListener {
            onItemClickCallback.onItemClicked(listArticle[position])
        }
    }


    override fun getItemCount(): Int = listArticle.size


    class ListViewHolder(var binding: ItemRowArticleBinding) : RecyclerView.ViewHolder(binding.root)
}