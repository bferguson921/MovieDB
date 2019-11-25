package com.example.moviedb.presenter

import com.example.moviedb.model.Genre
import com.example.moviedb.model.Result

interface Contract {

    interface Presenter{
        fun getMovies(page: Int)
    }

    interface View{
        fun displayMovies(movies: List<Result>, genres: List<Genre>)
    }
}