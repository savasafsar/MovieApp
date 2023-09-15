package com.example.moviecatch.ui.fragment

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.findNavController
import com.example.moviecatch.R
import com.example.moviecatch.databinding.FragmentSettingsBinding
import com.example.moviecatch.databinding.FragmentSplashBinding
import com.example.moviecatch.model.prefs.SessionManager
import dagger.hilt.android.AndroidEntryPoint
import javax.inject.Inject

@AndroidEntryPoint
class SplashFragment : Fragment() {
    private var _binding :FragmentSplashBinding?=null
    private val binding get() = _binding!!

    @Inject
    lateinit var sessionManager: SessionManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentSplashBinding.inflate(inflater,container,false)
        val view = binding.root

        Handler(Looper.getMainLooper()).postDelayed({
            if (sessionManager.getIsFirstRun()){
                findNavController().navigate(R.id.
                action_splashFragment_to_appintroFragment)
            }
            else{
                findNavController().navigate(R.id.action_splashFragment_to_mainFragment)

            }
        },3000)

        return view


    }
    override fun onDestroy() {
        super.onDestroy()
        _binding=null
    }

}