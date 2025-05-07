// Tyler Radke
// Project 3
// Movie.kt

package com.example.movielist

class Movie(val title: String?, val year: String?, val genre: String?, val rating: String?) {

    //Models any given Movie

    override fun toString(): String {
        return "TITLE = $title  YEAR = $year  GENRE = $genre  RATING = $rating"
    }
    fun convertOut(): String {
        return "$title,$year,$genre,$rating"
    }

}