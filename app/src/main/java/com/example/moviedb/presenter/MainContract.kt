package com.example.moviedb.presenter

import com.example.moviedb.model.Genre
import com.example.moviedb.model.GenreResponse
import com.example.moviedb.model.Images
import com.example.moviedb.model.Result

interface MainContract {

    interface Presenter{
        fun getMovies(page: Int)
        fun getConfig()
        fun getGenres() : List<Genre>
    }

    interface View{
        fun displayMovies(movies: List<Result>, genres: List<Genre>)
        fun getImages(images: Images)
    }
}