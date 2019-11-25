package com.example.moviedb.presenter

import com.example.moviedb.model.MovieResponse

interface DetailContract {
    interface Presenter {
        fun getDetails(movieId: Int)
    }

    interface View {
        fun displayDetails(movie: MovieResponse)
    }
}