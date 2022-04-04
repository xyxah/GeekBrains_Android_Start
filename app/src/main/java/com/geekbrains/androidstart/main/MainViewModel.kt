package com.geekbrains.androidstart.main

import android.view.View
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.material.button.MaterialButton

class MainViewModel: ViewModel() {

    val calcText = MutableLiveData("")

    fun addTextBtn(view: View){
        val text: String? = calcText.value
        val btnText: String = (view as MaterialButton).text.toString()
        calcText.value = text + btnText
    }
}