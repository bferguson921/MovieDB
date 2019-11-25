package com.example.moviedb.view

import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.net.NetworkInfo
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.ImageButton
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
import okhttp3.Cache
import okhttp3.OkHttpClient

class MainActivity : AppCompatActivity(), MainContract.View, MovieAdapter.MovieAdapterDelegate {

    private lateinit var presenter : MainPresenter
    private var page = 1

    private lateinit var images: Images
    private lateinit var genres: List<Genre>
    private lateinit var search : ImageButton

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        presenter = MainPresenter(this, makeCache())

        search = findViewById(R.id.searchButton)

        presenter.setImageConfig()
        presenter.setGenres()

        search.setOnClickListener{
            presenter.searchForMovie(searchBar.text.toString())
            Logger.debug("Search button clicked!")
        }

        presenter.getPopularMovies(page)
    }

    override fun displayMovies(movies: List<Result>) {
        movieListView.adapter = MovieAdapter(movies, genres, this, images, this)
        movieListView.layoutManager = LinearLayoutManager(this)
    }

    override fun getImages(images: Images) {
        this.images = images
    }

    override fun getGenres(genres: List<Genre>) {
        this.genres = genres
    }

    override fun displaySearch(movies: List<Result>) {
        Logger.debug("Displaying search!")
        movieListView.adapter = MovieAdapter(movies, genres, this, images, this)
        movieListView.layoutManager = LinearLayoutManager(this)
    }


    fun onClick(view: View){
        when(view){
            backButton -> {
                if(page > 1){
                    page--
                    presenter.getPopularMovies(page)
                }
            }
            forwardButton -> {
                page++
                presenter.getPopularMovies(page)
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

    private fun hasNetwork(context: Context): Boolean? {
        var isConnected: Boolean? = false // Initial Value
        val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val activeNetwork: NetworkInfo? = connectivityManager.activeNetworkInfo
        if (activeNetwork != null && activeNetwork.isConnected)
            isConnected = true
        return isConnected
    }

    private fun makeCache() : OkHttpClient {
        val cacheSize =(5 * 1024 * 1024).toLong()
        val cache = Cache(this.cacheDir, cacheSize)

        val okHttpClient = OkHttpClient.Builder()
            .cache(cache)
            .addInterceptor{
                var request = it.request()

                request = if(hasNetwork(this)!!){
                    request.newBuilder().header("Cache-Control", "public, max-age=" + 5).build()
                } else{
                    request.newBuilder().header("Cache-Control", "public, only-if-cached, max-stale=" + 60 * 60 * 24 * 7).build()
                }

                it.proceed(request)
            }
            .build()

        return okHttpClient

    }

}
