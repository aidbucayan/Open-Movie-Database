package com.adrian.bucayan.movie.presentation.ui.list

import android.annotation.SuppressLint
import android.graphics.Rect
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.adrian.bucayan.movie.databinding.MovieItemsBinding
import com.adrian.bucayan.movie.domain.model.Search
import com.adrian.bucayan.movie.presentation.ui.util.adapter.BaseRecyclerViewAdapter
import com.adrian.bucayan.movie.presentation.ui.util.adapter.BaseViewHolder
import com.adrian.bucayan.movie.presentation.ui.util.adapter.ViewHolderInitializer
import kotlinx.coroutines.ExperimentalCoroutinesApi

class MovieListAdapter : BaseRecyclerViewAdapter<Search, MovieItemsBinding>() ,
    ViewHolderInitializer<Search, MovieItemsBinding> {

    var toSelectSearch: ((Search, Int) -> Unit)? = null

    init { viewBindingInitializer = this }

    class TopSpacingDecoration(private val padding: Int): RecyclerView.ItemDecoration() {

        override fun getItemOffsets(outRect: Rect, view: View, parent: RecyclerView, state: RecyclerView.State) {
            super.getItemOffsets(outRect, view, parent, state)

            if (parent.getChildAdapterPosition(view) > 0) {
                outRect.top = padding
            }
        }
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    override fun generateViewHolder(parent: ViewGroup): BaseViewHolder<Search, MovieItemsBinding> {

        val itemBinding: MovieItemsBinding = MovieItemsBinding.inflate(
            LayoutInflater.from(parent.context), parent, false)

        return SubDiscoveryItemsAdapterViewHolder(itemBinding, toSelectSearch)

    }

}

@ExperimentalCoroutinesApi
class SubDiscoveryItemsAdapterViewHolder(
    viewBinding: MovieItemsBinding,
    private val toSelectSearch: ((Search, Int) -> Unit)? ) : BaseViewHolder<Search, MovieItemsBinding>(viewBinding) {

    private val title : TextView = viewBinding.tvMovieItemsTitle
    private val year : TextView = viewBinding.tvMovieItemsYear
    private val holder : LinearLayout = viewBinding.llMovieItemsHolder

    @SuppressLint("SetTextI18n", "ResourceType")
    override fun setViews(item: Search) {
        super.setViews(item)

        title.text = item.title
        year.text = item.year

        holder.setOnClickListener{
            toSelectSearch?.invoke(item, adapterPosition)
        }

    }



}

