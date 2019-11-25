package com.example.moviedb.view

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.Parcel
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviedb.R
import com.example.moviedb.adapter.MovieAdapter
import com.example.moviedb.model.Genre
import com.example.moviedb.model.Images
import com.example.moviedb.model.Result
import com.example.moviedb.presenter.MainContract
import com.example.moviedb.presenter.MainPresenter
import com.example.moviedb.util.Logger
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), MainContract.View, MovieAdapter.MovieAdapterDelegate {

    private val presenter = MainPresenter(this)
    private var page = 1
    private lateinit var images: Images

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter.getConfig()
        presenter.getMovies(page)
    }

    override fun displayMovies(movies: List<Result>, genres: List<Genre>) {
        movieListView.adapter = MovieAdapter(movies, genres, this, images, this)
        movieListView.layoutManager = LinearLayoutManager(this)
        Logger.debug(movies.toString() + genres.toString())
    }

    override fun getImages(i: Images) {
        images = i
        Logger.debug(images.toString())
    }

    fun onClick(view: View){
        when(view){
            backButton -> {
                if(page > 1){
                    page--
                    presenter.getMovies(page)
                }
            }
            forwardButton -> {
                page++
                presenter.getMovies(page)
            }
        }
    }

    override fun getMovieDetails(movieId: Int, genre: String) {

        val intent = Intent(this, DetailActivity::class.java)

        intent.putExtra("id", movieId)
        intent.putExtra("genre", genre)
        intent.putExtra("images", images)

        startActivity(intent)
    }

}
