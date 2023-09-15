package com.example.moviecatch.ui.fragment.appintro.pages

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.RelativeLayout
import androidx.viewpager2.widget.ViewPager2
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentFirstBinding
import com.example.moviecatch.databinding.FragmentSecondBinding
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
class SecondFragment : Fragment() {
    private var _binding : FragmentSecondBinding?=null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSecondBinding.inflate(inflater, container, false)
        val view = binding.root
        // Inflate the layout for this fragment
        return view
    }
    override fun onResume() {
        val viewPager = activity?.findViewById<ViewPager2>(R.id.viewPager)
        val prevButton = activity?.findViewById<RelativeLayout>(R.id.prevButton)
        val nextButton = activity?.findViewById<RelativeLayout>(R.id.nxtButton)

        prevButton?.alpha = 1f
        prevButton?.isClickable = true

        nextButton?.alpha = 1f
        nextButton?.isClickable = true

        prevButton?.setOnClickListener {
            viewPager?.currentItem = 0
        }

        nextButton?.setOnClickListener {
            viewPager?.currentItem = 2
        }

        super.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}