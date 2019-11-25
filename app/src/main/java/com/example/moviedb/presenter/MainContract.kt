package com.example.moviedb.presenter

import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreResponse
import com.example.moviedb.model.Images
import com.example.moviedb.model.Result

interface MainContract {

    interface Presenter{
        fun getPopularMovies(page: Int)
        fun setImageConfig()
        fun setGenres()
    }

    interface View{
        fun displayMovies(movies: List<Result>)
        fun getImages(images: Images)
        fun getGenres(genres: List<Genre>)
    }
}