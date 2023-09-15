package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.lifecycle.MutableLiveData
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import com.bumptech.glide.Glide
import com.example.moviecatch.databinding.PopularMovieItemBinding
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.model.Movie
import com.example.moviecatch.model.Result
import com.example.moviecatch.ui.fragment.home.pages.HomeFragmentDirections
import java.util.Locale

class MovieAdapter(private val isFirstScreen : Boolean = true) : RecyclerView.Adapter<MovieAdapter.MyViewHolder>() {

    var liveData : List<Result>? = null
    var genreList : List<GenreData>? = null
    fun setLists(liveData: List<Result>,genreList:List<GenreData>) {
        this.liveData = liveData
        this.genreList = genreList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: PopularMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(result: Result,genreList: List<GenreData>) {
            binding.txtTitle.text = result.title
            val lang = Locale.getDefault().language
            var genres = ""
            for (id in result.genre_ids){

                var result = genreList.find { x -> x.genre_id ==id }
                if (result!=null) {
                    if (lang=="tr") {
                        genres += result!!.tr_name + ", "
                    } else {
                        genres += result!!.en_name + ", "
                    }
                }
            }
            genres = genres.substring(0,genres.length-2)
            binding.txtGenre.text = genres
            Glide.with(binding.posterView)
                .load("https://image.tmdb.org/t/p/w342/"+result.poster_path)
                .into(binding.posterView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
       return MyViewHolder(PopularMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

    }

    override fun getItemCount(): Int {
        if (liveData==null) {
            return 0
        } else if (isFirstScreen)
            return 4
        else {
            return liveData!!.size
        }
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
       holder.bindItems(liveData!!.get(position),genreList!!)

        holder.binding.posterView?.setOnClickListener {
            val action = HomeFragmentDirections.actionHomeFragmentToDetailsFragment(position)
            holder.binding.posterView.findNavController().navigate(action)
        }
    }
}