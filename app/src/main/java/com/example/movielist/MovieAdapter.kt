// Tyler Radke
// Project 3
// MainAdapter.kt

package com.example.movielist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.RecyclerView

class MovieAdapter(private val movieList: MutableList<Movie>) : RecyclerView.Adapter<MovieAdapter.MovieViewHolder>() {

    class MovieViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        var titleTextView: TextView
        var yearTextView: TextView
        var genreTextView: TextView
        var ratingTextView: TextView

        init {
            titleTextView = itemView.findViewById(R.id.titleTextView)
            yearTextView = itemView.findViewById(R.id.yearTextView)
            genreTextView = itemView.findViewById(R.id.genreTextView)
            ratingTextView = itemView.findViewById(R.id.ratingTextView)
        }
    }

    //Creates a new ViewHolder object when there’s no existing view to reuse

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MovieViewHolder {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false)
        return MovieViewHolder(view)
    }

    //Binds the data from a Movie object to the views inside the ViewHolder

    override fun onBindViewHolder(holder: MovieViewHolder, position: Int) {
        val movie = movieList[position]
        holder.titleTextView.text = movie.title
        holder.yearTextView.text = movie.year
        holder.genreTextView.text = movie.genre
        holder.ratingTextView.text = movie.rating
    }

    //Count how many items are in data
    override fun getItemCount(): Int {
        return movieList.size
    }

    //Item Deletion Code Logic

    fun removeItem(position: Int) {
        movieList.removeAt(position)
        notifyItemRemoved(position)
    }
    inner class SwipeToDeleteCallback :
        ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {

        override fun onMove(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder,
            target: RecyclerView.ViewHolder
        ): Boolean = false

        override fun getMovementFlags(
            recyclerView: RecyclerView,
            viewHolder: RecyclerView.ViewHolder
        ): Int {
            return if (viewHolder is MovieViewHolder) {
                makeMovementFlags(
                    ItemTouchHelper.ACTION_STATE_IDLE,
                    ItemTouchHelper.RIGHT
                ) or makeMovementFlags(
                    ItemTouchHelper.ACTION_STATE_SWIPE,
                    ItemTouchHelper.RIGHT
                )
            } else {
                0
            }
        }

        override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
            val position = viewHolder.adapterPosition
            removeItem(position)
        }
    }

    val swipeToDeleteCallback = SwipeToDeleteCallback()
}
