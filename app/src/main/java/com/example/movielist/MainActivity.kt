// Tyler Radke
// Project 3
// MainActivity.kt

package com.example.movielist

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.view.Menu
import android.view.MenuItem
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.result.ActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.io.File
import java.io.FileWriter
import java.io.IOException
import java.util.Scanner

class MainActivity : AppCompatActivity() {

    private val movieList: MutableList<Movie> = ArrayList()
    private val movieAdapter = MovieAdapter(movieList)

    //When adding new movie
    private val startForResult = registerForActivityResult(
        ActivityResultContracts.StartActivityForResult()
    ) { result: ActivityResult ->
        if (result.resultCode == Activity.RESULT_OK) {
            val data = result.data
            if (data != null) {
                val title = data.getStringExtra("title") ?: ""
                val year = data.getStringExtra("year") ?: ""
                val genre = data.getStringExtra("genre") ?: ""
                val rating = data.getStringExtra("rating") ?: ""

                movieList.add(Movie(title, year, genre, rating))
                movieAdapter.notifyDataSetChanged()
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)

        //Read from file to populate movieList
        readFile(movieList)

        //RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.recyclerView)
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = movieAdapter

        //Swipe to delete
        val itemTouchHelper = ItemTouchHelper(movieAdapter.swipeToDeleteCallback)
        itemTouchHelper.attachToRecyclerView(recyclerView)

        //Add movie button
        val add_button = findViewById<Button>(R.id.add_button)
        add_button.setOnClickListener {
            startSecond(it)
        }

        //Save button
        val save_button = findViewById<Button>(R.id.save_button)
        save_button.setOnClickListener {
            saveList(it)
        }
    }

    //Options/Overflow
    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.d("MOVIELIST", "options menu")
        when (item.itemId) {
            R.id.ratingSort -> {
                Log.d("MOVIELIST", "onOptions: rating sort")
                movieList?.sortBy{ it?.rating }
                movieAdapter.notifyDataSetChanged()
            }
            R.id.yearSort -> {
                Log.d("MOVIELIST", "onOptions: year sort")
                movieList?.sortBy{ it?.year }
                movieAdapter.notifyDataSetChanged()
            }
            R.id.genreSort -> {
                Log.d("MOVIELIST", "onOptions: genre sort")
                movieList?.sortBy{ it?.genre }
                movieAdapter.notifyDataSetChanged()
            }
        }
        return super.onOptionsItemSelected(item)
    }

    //Function to save movie list
    fun saveList(v: View) {
        writeFile()
    }

    //Start the second activity
    fun startSecond(v: View) {
        startForResult.launch(Intent(this, AddMovieActivity::class.java))
    }

    //Read in the movie list
    private fun readFile(movieList: MutableList<Movie>) {
        Log.d("MOVIELIST", "readFile() entered")
        try {
            val f = File(filesDir, "MOVIELIST.csv")
            f.createNewFile()
            val myReader = Scanner(f)

            while (myReader.hasNextLine()) {
                val data = myReader.nextLine()
                val parts = data.split(",")
                movieList.add(Movie(parts[0], parts[1], parts[2], parts[3]))
            }

            myReader.close()
        } catch (e: IOException) {
            Log.d("MOVIELIST", "READ EXCEPTION: $e")
        }
    }

    //Write/Save the movie list
    private fun writeFile() {
        Log.d("MOVIELIST", "writeFile() entered")
        try {
            val f = File(filesDir, "MOVIELIST.csv")
            val fw = FileWriter(f, false)
            for (movie in movieList) {
                fw.write(movie.convertOut() + "\n")
            }
            fw.flush()
            fw.close()
        } catch (e: IOException) {
            Log.d("MOVIELIST", "WRITE EXCEPTION: $e")
        }
    }
}
