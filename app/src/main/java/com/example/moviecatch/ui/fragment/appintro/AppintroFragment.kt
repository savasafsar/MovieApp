package com.example.moviecatch.ui.fragment.appintro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.moviecatch.adapter.ViewPagerAdapter
import com.example.moviecatch.databinding.FragmentAppintroBinding
import com.example.moviecatch.ui.fragment.appintro.pages.FirstFragment
import com.example.moviecatch.ui.fragment.appintro.pages.SecondFragment
import com.example.moviecatch.ui.fragment.appintro.pages.ThirdFragment
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class AppintroFragment : Fragment() {
    private var _binding : FragmentAppintroBinding?=null
    private val binding get() = _binding!!
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAppintroBinding.inflate(inflater,container,false)
        val view = binding.root

        val fragmentList = arrayListOf<Fragment>(
            FirstFragment(),
            SecondFragment(),
            ThirdFragment()
        )
        val adapter = ViewPagerAdapter(fragmentList,requireActivity()
            .supportFragmentManager,lifecycle)
        binding.viewPager.adapter = adapter




        return view
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
}