package com.geekbrains.androidstart

import android.os.Bundle
import android.os.Parcelable
import androidx.appcompat.app.AppCompatActivity
import com.geekbrains.androidstart.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton

class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        initView()
    }

    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        val calcText = CalcText(binding.calcText.text.toString())
        instanceState.putParcelable("text", calcText)
    }

    override fun onRestoreInstanceState(instanceState: Bundle) {
        super.onRestoreInstanceState(instanceState)
        val textCalcGet = instanceState.getParcelable<Parcelable>("text") as CalcText
        binding.calcText.text = textCalcGet.text.toString()
    }

    private fun initView(){
        binding.calcBtn0.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn1.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn2.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn3.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn4.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn5.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn6.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn7.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn8.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtn9.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
    }

    private fun calcBtnNumberClick(text: String){
        binding.calcText.append(text)
    }
}