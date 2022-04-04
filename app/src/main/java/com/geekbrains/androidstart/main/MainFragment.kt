package com.geekbrains.androidstart.main

import android.os.Bundle
import android.os.Parcelable
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.geekbrains.androidstart.CalcText
import com.geekbrains.androidstart.R
import com.geekbrains.androidstart.databinding.FragmentMainBinding

class MainFragment : Fragment() {

    lateinit var mainViewModel: MainViewModel
    lateinit var binding: FragmentMainBinding

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_main, container, false)
        mainViewModel = ViewModelProvider(this).get(MainViewModel::class.java)
        binding.mainVM = mainViewModel

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        mainViewModel.calcText.observe(viewLifecycleOwner, Observer { binding.calcText.text = it })
    }

    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        val calcText = CalcText(binding.calcText.text.toString())
        instanceState.putParcelable("text", calcText)
    }

    override fun onViewStateRestored(savedInstanceState: Bundle?) {
        super.onViewStateRestored(savedInstanceState)
        if (savedInstanceState != null) {
            val textCalcGet = savedInstanceState.getParcelable<Parcelable>("text") as CalcText
            binding.calcText.text = textCalcGet.text.toString()
        }
    }
}