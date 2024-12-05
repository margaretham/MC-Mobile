package com.capstone.maternalcare.adapter

import com.capstone.maternalcare.data.model.Article

interface OnItemClickCallback {
    fun onItemClicked(data: Article)
}