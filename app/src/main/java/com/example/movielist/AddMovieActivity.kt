// Tyler Radke
// Project 3
// AddMovieActivity.kt

package com.example.movielist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class AddMovieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_add_movie)

    }

    //Retrieves the four text Strings that the user entered out of the four EditText elements

    fun backToFirst(v: View) {
        Log.d("MOVIE", "AddMovieAct backToFirst ENTERED")

        val textTitle = findViewById<EditText>(R.id.title)
        val textYear = findViewById<EditText>(R.id.year)
        val textGenre = findViewById<EditText>(R.id.genre)
        val textRating = findViewById<EditText>(R.id.rating)

        val title = textTitle.text.toString()
        val year = textYear.text.toString()
        val genre = textGenre.text.toString()
        val rating = textRating.text.toString()

        Log.d("MOVIE", "TITLE: $title")

        setMovieInfo(title, year, genre, rating)
    }

    //This function creates a Intent object and packages in it – as key-value pairs

    private fun setMovieInfo(title: String, year: String, genre: String, rating: String) {
        val movieIntent = Intent()
        Log.d("MOVIE", "AddMovieAct setMovieInfo TITLE: $title")

        movieIntent.putExtra("title", title)
        movieIntent.putExtra("year", year)
        movieIntent.putExtra("genre", genre)
        movieIntent.putExtra("rating", rating)

        setResult(Activity.RESULT_OK, movieIntent)
        finish()
    }
}
