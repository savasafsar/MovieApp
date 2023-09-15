package com.example.moviecatch.ui.fragment.appintro.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentFavoriteBinding
import com.example.moviecatch.databinding.FragmentFirstBinding
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class FirstFragment : Fragment() {
    private var _binding : FragmentFirstBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentFirstBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }

    override fun onResume() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val prevButton = activity?.findViewById<RelativeLayout>(R.id.prevButton)
        val nextButton = activity?.findViewById<RelativeLayout>(R.id.nxtButton)



        prevButton?.alpha = 0f
        prevButton?.isClickable = false

        nextButton?.setOnClickListener {
            viewPager?.currentItem = 1

        }
        super.onResume()
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }


}