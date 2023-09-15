package com.example.moviecatch.ui.fragment.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentHomeBinding
import com.example.moviecatch.databinding.FragmentMainBinding
import com.example.moviecatch.databinding.FragmentSettingsBinding
import com.example.moviecatch.model.prefs.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class MainFragment : Fragment() {
    private var _binding : FragmentMainBinding?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        val view = binding.root

        if (sessionManager.getIsFirstRun())
            sessionManager.setIsFirstRun(false)

        setupTabBar()

        return view
    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }
  private fun setupTabBar() {
        binding.bottomNavBar.setItemSelected(R.id.nav_home,true)
      binding.bottomNavBar.setOnItemSelectedListener {
          when(it){
              R.id.nav_home -> childFragmentManager.primaryNavigationFragment?.findNavController()?.navigate(R.id.homeFragment)
              R.id.nav_favorites -> childFragmentManager.primaryNavigationFragment?.findNavController()?.navigate(R.id.favoriFragment)
              R.id.nav_settings -> childFragmentManager.primaryNavigationFragment?.findNavController()?.navigate(R.id.settingsFragment)
          }
      }
    }

}