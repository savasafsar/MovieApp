package com.example.moviecatch.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.di.dao.GenreRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class GenreViewModel @Inject constructor(private val genreRepository: GenreRepository) :ViewModel(){

    lateinit var allData : MutableLiveData<List<GenreData>>

    init {
        allData = MutableLiveData()
        loadRecords()
    }
    fun getRecordObserver() : MutableLiveData<List<GenreData>>{
        return allData
    }
    fun addGenre(genreData: GenreData) {
        genreRepository.addGenre(genreData)
        loadRecords()
    }
    fun addAllGenres(genreList: List<GenreData>){
        genreRepository.addAllGenres(genreList)
        loadRecords()
    }
   fun loadRecords() {
        val list = genreRepository.readAllData
        allData.postValue(list)
    }
}