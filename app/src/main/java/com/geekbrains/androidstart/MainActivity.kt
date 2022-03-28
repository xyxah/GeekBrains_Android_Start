package com.geekbrains.androidstart

import android.os.Bundle
import android.os.Parcelable
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    lateinit var textView: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.calc)

        initView()
    }

    override fun onSaveInstanceState(instanceState: Bundle) {
        super.onSaveInstanceState(instanceState)
        val calcText = CalcText(textView.text.toString())
        instanceState.putParcelable("text", calcText)
    }

    override fun onRestoreInstanceState(instanceState: Bundle) {
        super.onRestoreInstanceState(instanceState)
        val textCalcGet = instanceState.getParcelable<Parcelable>("text") as CalcText
        textView.text = textCalcGet.text.toString()
    }

    private fun initView() {
        textView = findViewById(R.id.calc_text)
        val buttons = arrayOfNulls<MaterialButton>(16)
        for (i in 0..15) {
            val id = resources.getIdentifier("calc_btn_$i", "id", packageName)
            buttons[i] = findViewById(id)
            buttons[i]?.setOnClickListener {
                textView.append(buttons[i]?.text)
            }
        }
    }
}