package com.example.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatch.di.dao.GenreRepository
import com.example.moviecatch.di.retrofit.RetrofitRepository
import com.example.moviecatch.model.Genre
import com.example.moviecatch.model.Movie
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class HomePageViewModel @Inject constructor(private val retrofitRepository: RetrofitRepository,private val genreRepository: GenreRepository)
    :ViewModel(){
        var popularMovieList : MutableLiveData<Movie>
         var recentMovieList : MutableLiveData<Movie>
    var genreList : MutableLiveData<Genre>
    init {
        popularMovieList = MutableLiveData()
        recentMovieList = MutableLiveData()
        genreList = MutableLiveData()
    }
    fun getObserverGenre() : MutableLiveData<Genre> {
        return genreList
    }
    fun getObserverLiveData(isPopular : Boolean) : MutableLiveData<Movie> {
        if (isPopular) {
            return popularMovieList
        } else {
            return recentMovieList
        }

    }
    fun loadGenreData(){
        retrofitRepository.getAllGenres(genreList)
    }
    fun loadData(page:String,isPopular: Boolean){
        if (isPopular)
            retrofitRepository.getPopularMovies(page,popularMovieList)
        else
            retrofitRepository.getRecentMovies(page,recentMovieList)
    }
}