package com.example.moviecatch.ui.fragment.home.pages

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.moviecatch.adapter.MovieAdapter
import com.example.moviecatch.adapter.RecentMovieAdapter
import com.example.moviecatch.databinding.FragmentHomeBinding
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.model.Movie
import com.example.moviecatch.viewmodel.GenreViewModel
import com.example.moviecatch.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Deferred
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {
    private var _binding : FragmentHomeBinding?=null
    private val binding get() = _binding!!

    private var genreList : List<GenreData>? = null

    val viewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory)
            .get(HomePageViewModel::class.java)
    }
    val genreViewModel by lazy {
        ViewModelProvider(this,defaultViewModelProviderFactory).get(GenreViewModel::class.java)
    }

    private lateinit var movieAdapter: MovieAdapter
    private lateinit var recentMovieAdapter: RecentMovieAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val view = binding.root





        val lmHorizontal = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL,false)
        val lmVertical= LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        val recyclerView = binding.recyclerView
        recyclerView.layoutManager = lmHorizontal
        movieAdapter = MovieAdapter()
        recyclerView.adapter = movieAdapter
        val recentRecyclerView = binding.recentrecyclerView
        recentMovieAdapter  = RecentMovieAdapter()
        recentRecyclerView.adapter = recentMovieAdapter
        recentRecyclerView.layoutManager = lmVertical

        viewModel.getObserverLiveData(true).
        observe(viewLifecycleOwner,object : Observer<Movie> {
            override fun onChanged(value: Movie) {
                if (value!=null){
                    movieAdapter.setLists(value.results,genreList!!)
                }
            }

        })
        viewModel.getObserverLiveData(false).observe(viewLifecycleOwner,
            object : Observer<Movie> {
            override fun onChanged(value: Movie) {
                if (value!=null){
                    recentMovieAdapter.setLists(value.results,genreList!!)
                }
            }

        })
        fetchMovies()

        genreViewModel.getRecordObserver().observe(viewLifecycleOwner,
            object :Observer<List<GenreData>>{
            override fun onChanged(t: List<GenreData>) {
               if (t!=null){
                   genreList = t
                   fetchMovies()
               }
            }

        })


        return view
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

    fun fetchMovies() {
        CoroutineScope(Dispatchers.IO).launch {
            val job1 : Deferred<Unit> = async {
                viewModel.loadData("1",true)
            }
            val job2 : Deferred<Unit> = async {
                viewModel.loadData("1",false)
            }
            job1.await()
            job2.await()
        }
    }


}