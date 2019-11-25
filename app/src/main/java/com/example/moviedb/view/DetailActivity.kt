package com.example.moviedb.view

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
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
import okhttp3.Cache
import okhttp3.OkHttpClient

class DetailActivity : AppCompatActivity(), DetailContract.View {

    private lateinit var presenter : DetailPresenter
    private var image : Images? = null
    private var genre : String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_detail)

        presenter = DetailPresenter(this, makeCache())

        val id = intent.getIntExtra("id", 0)
        image = intent.getParcelableExtra("images")
        genre = intent.getStringExtra("genre")
        presenter.getDetails(id)
    }

    override fun displayDetails(movie: MovieResponse) {
        val url = image?.secureBaseUrl + image?.posterSizes?.get(0)+ movie.posterPath
        Logger.debug(url)

        Glide.with(this).load(url).into(iconDisplay)
        val genresToShow = "Genres:\n${genre}\n"
        val popularity = "Popularity: ${movie.popularity}"
        val release = "Release Date: ${movie.releaseDate}"
        val runtime = "Runtime: ${movie.runtime} mins"
        val link = "Homepage:\n${movie.homepage}"

        titleDisplay.text = movie.originalTitle
        genreDisplay.text = genresToShow
        popularityDisplay.text = popularity
        releaseYearDisplay.text = release
        runtimeDisplay.text = runtime
        linkDisplay.text = link
        overviewDisplay.text = movie.overview

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
