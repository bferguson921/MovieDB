package com.example.moviedb.view

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.model.Images
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.presenter.DetailContract
import com.example.moviedb.presenter.DetailPresenter
import com.example.moviedb.util.Logger
import kotlinx.android.synthetic.main.activity_detail.*

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private val presenter = DetailPresenter(this)
    private var image : Images? = null
    private var genre : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        val id = intent.getIntExtra("id", 0)
        image = intent.getParcelableExtra("images")
        genre = intent.getStringExtra("genre")
        presenter.getDetails(id)
    }

    override fun displayDetails(movie: MovieResponse) {
        val url = image?.secureBaseUrl + image?.posterSizes?.get(0)+ movie.posterPath
        Logger.debug(url)

        Glide.with(this).load(url).into(iconDisplay)
        
        titleDisplay.text = movie.title
        genreDisplay.text = genre
        popularityDisplay.text = movie.popularity.toString()
        releaseYearDisplay.text = movie.releaseDate
        overviewDisplay.text = movie.overview
        runtimeDisplay.text = movie.runtime.toString()
        linkDisplay.text = movie.homepage.toString()
    }
}
