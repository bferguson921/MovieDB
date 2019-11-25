package com.example.moviedb.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.moviedb.R
import com.example.moviedb.factory.MovieFactory
import com.example.moviedb.model.Genre
import com.example.moviedb.model.Images
import com.example.moviedb.model.MovieResponse
import com.example.moviedb.model.Result
import com.example.moviedb.util.Logger
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MovieAdapter(private val movies: List<Result>,
                   private val genres: List<Genre>,
                   private val delegator: MovieAdapterDelegate,
                   private val image: Images,
                   private  val context: Context) : RecyclerView.Adapter<MovieAdapter.CustomViewHolder>(){

    interface MovieAdapterDelegate {
        fun getMovieDetails(movieId: Int, genre: String)
    }
    class CustomViewHolder(view: View) : RecyclerView.ViewHolder(view){
        val thumbnail: ImageView = view.findViewById(R.id.movieThumbnail)
        val movieTitle: TextView = view.findViewById(R.id.movieTitleView)
        val genreTitle: TextView = view.findViewById(R.id.genreView)
        val popularityScore: TextView = view.findViewById(R.id.popularityScoreView)
        val releaseYear: TextView = view.findViewById(R.id.releaseYearView)
        val movieItem: ConstraintLayout = view.findViewById(R.id.movieItem)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CustomViewHolder {
        return CustomViewHolder(LayoutInflater.from(parent.context)
            .inflate(R.layout.movie_item, parent, false))
    }

    override fun getItemCount(): Int {
        return movies.size
    }

    override fun onBindViewHolder(holder: CustomViewHolder, position: Int) {
        holder.apply {

            val url = image.secureBaseUrl + image.posterSizes.get(0) + movies[position].posterPath
            val stringBuilder = StringBuilder()


            for(genre in genres) {
                for(genreId in movies[position].genreIds) {
                    if(genre.id == genreId){
                        stringBuilder.append("${genre.name}\n")
                    }
                }
            }

            stringBuilder.deleteCharAt(stringBuilder.lastIndex)

            val movieGenres = "Genres:\n$stringBuilder\n"
            val popularity =  "Popularity: ${movies[position].popularity}"
            val release = "Release Date: ${movies[position].releaseDate}"

            Glide.with(context).load(url).into(thumbnail)

            movieTitle.text = movies[position].originalTitle
            genreTitle.text = movieGenres
            popularityScore.text = popularity
            releaseYear.text = release

            movieItem.setOnClickListener{
                Logger.debug("Movie clicked!")
                delegator.getMovieDetails(movies[position].id, stringBuilder.toString())
            }
        }
    }
}