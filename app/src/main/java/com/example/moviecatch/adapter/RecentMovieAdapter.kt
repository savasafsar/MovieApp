package com.example.moviecatch.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler
import androidx.room.Room
import com.bumptech.glide.Glide
import com.example.moviecatch.databinding.PopularMovieItemBinding
import com.example.moviecatch.databinding.RecentMovieItemBinding
import com.example.moviecatch.di.dao.FavoriteDao
import com.example.moviecatch.di.dao.FavoriteDataClass
import com.example.moviecatch.di.dao.FavoriteDatabase
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.model.Movie
import com.example.moviecatch.model.Result
import java.util.Locale

class RecentMovieAdapter(private val isFirstScreen : Boolean = true) : RecyclerView.Adapter<RecentMovieAdapter.MyViewHolder>() {
    private lateinit var db : FavoriteDatabase
    private lateinit var dao : FavoriteDao
    var liveData : List<Result>? = null
    var genreList : List<GenreData>? = null
    fun setLists(liveData: List<Result>,genreList: List<GenreData>) {
        this.liveData = liveData
        this.genreList = genreList
        notifyDataSetChanged()
    }

    class MyViewHolder(val binding: RecentMovieItemBinding) : RecyclerView.ViewHolder(binding.root) {
        fun bindItems(result: Result,genreList: List<GenreData>) {
            binding.txtTitle.text = result.title

            var genres = ""
            val lang = Locale.getDefault().language
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

            binding.txtReleaseDate.text = result.release_date
            binding.txtPuanlama.text = result.vote_average.toString() + " / 10 "
            Glide.with(binding.posterView)
                .load("https://image.tmdb.org/t/p/w342/"+result.poster_path)
                .into(binding.posterView)
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        return MyViewHolder(RecentMovieItemBinding.inflate(LayoutInflater.from(parent.context),parent,false))

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


        db = Room.databaseBuilder(holder.itemView.context.applicationContext,
            FavoriteDatabase::class.java,"FavoriteMovie")
            .allowMainThreadQueries()
            .build()
        dao = db.favoriteDao()
        val dataExists = dao.checkIfDataExists(liveData!![position].id.toInt()) > 0

        if (dataExists == true) {
            // veritabanında veri varsa, image görüntülenecek
            holder.binding.imgFavoriYes.visibility = ViewGroup.VISIBLE
            holder.binding.imgFavorite.visibility = ViewGroup.GONE
        } else {
            // veritabanında veri yoksa, image gizlenecek
            holder.binding.imgFavoriYes.visibility= ViewGroup.GONE
            holder.binding.imgFavorite.visibility = ViewGroup.VISIBLE
        }


        holder.binding.imgFavorite.setOnClickListener {

            val data = FavoriteDataClass(
                liveData!!.get(position).title,
                liveData!!.get(position).vote_average,
                liveData!!.get(position).poster_path,
                liveData!!.get(position).release_date,
                liveData!!.get(position).id,
            )
            dao.insert(data)
            notifyDataSetChanged()
            Toast.makeText(holder.itemView.context, "Favorilere Eklendi",
                Toast.LENGTH_SHORT).show()

        }

    }
}