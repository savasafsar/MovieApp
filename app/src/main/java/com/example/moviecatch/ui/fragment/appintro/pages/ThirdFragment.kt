package com.example.moviecatch.ui.fragment.appintro.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentSecondBinding
import com.example.moviecatch.databinding.FragmentThirdBinding
import com.example.moviecatch.di.dao.GenreData
import com.example.moviecatch.util.StringHelper
import com.example.moviecatch.viewmodel.GenreViewModel
import com.example.moviecatch.viewmodel.HomePageViewModel
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class ThirdFragment : Fragment() {
    private var _binding : FragmentThirdBinding?=null
    private val binding get() = _binding!!
   private lateinit var genreViewModel: GenreViewModel
   private lateinit var homePageViewModel: HomePageViewModel

   private var stringHelper : StringHelper?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentThirdBinding.inflate(inflater, container, false)
        val view = binding.root
        stringHelper= StringHelper()
        val genreList : MutableList<GenreData> = mutableListOf()
        genreViewModel = ViewModelProvider(this).get(GenreViewModel::class.java)
        homePageViewModel = ViewModelProvider(this).get(HomePageViewModel::class.java)

        homePageViewModel.getObserverGenre().observe(viewLifecycleOwner,{
            if (it!=null){
                for (item in it.genres) {
                    val tr_name = stringHelper!!.getTrName(item.name)
                    val genre = GenreData(0,item.id,item.name,tr_name)
                    genreList.add(genre)
                }
             genreViewModel.addAllGenres(genreList)
                findNavController().navigate(R.id.action_appintroFragment_to_mainFragment)
            }
        })

        // Inflate the layout for this fragment
        binding.imageButton.setOnClickListener {
        homePageViewModel.loadGenreData()
        }
        return view
    }
    override fun onResume() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val prevButton = activity?.findViewById<RelativeLayout>(R.id.prevButton)
        val nextButton = activity?.findViewById<RelativeLayout>(R.id.nxtButton)

        nextButton?.alpha = 0f
        nextButton?.isClickable =false

        prevButton?.setOnClickListener {
            viewPager?.currentItem = 1
        }

        nextButton?.setOnClickListener {
            viewPager?.currentItem = 3
        }

        super.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}