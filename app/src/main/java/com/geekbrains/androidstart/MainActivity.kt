package com.geekbrains.androidstart

import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import com.geekbrains.androidstart.databinding.ActivityMainBinding
import com.google.android.material.button.MaterialButton


class MainActivity : AppCompatActivity() {

    lateinit var binding: ActivityMainBinding
    private val operations = mapOf(0 to '-', 1 to '+', 2 to '/', 3 to '*')

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
        //numbers
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

        //operations
        binding.calcBtnDot.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtnMultiply.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtnSlash.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtnPlus.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }
        binding.calcBtnMinus.setOnClickListener { calcBtnNumberClick((it as MaterialButton).text.toString()) }

        //calculate
        binding.calcBtnEquals.setOnClickListener { calculate(binding.calcText.text.toString()) }

        //theme
        binding.btnTheme!!.setOnClickListener {
            if (binding.btnTheme!!.isChecked) AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
            else  AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        }
    }

    private fun calcBtnNumberClick(text: String){
        binding.calcText.append(text)
    }

    private fun calculate(text: String){

        var finalText : String? = ""
        var countOperations = getAllOperations(text, operations)

        while (countOperations != 0){
            finalText = getMainOperation(text, operations)
            countOperations--
        }

        if (!finalText.isNullOrEmpty()) binding.calcText.text = finalText
        else Toast.makeText(this, "????????????", Toast.LENGTH_SHORT).show()
    }

    // ?????????????????? ???????????????????? ????????????????
    private fun getAllOperations(text: String, operations: Map<Int,Char>): Int {
        var count = 0
        for (i in 0 until operations.count())
            count += text.filter { it == operations[i] }.count()
        return count
    }

    // ?????????????????? ?????????????? ????????????????
    private fun getMainOperation(text: String, operations: Map<Int,Char>): String? {
        var mainOperation: Char? = null

        for (i in 1..operations.count())
            if (operations[i]?.let { text.contains(it) } == true) mainOperation = operations[i]
         return cropText(mainOperation, text)
    }

    // ?????????????? ????????????
    private fun cropText(operation: Char?,text: String): String?{
        if (operation != null){

            val indexOperation = text.indexOf(operation)

            var startPart = text.substring(0, indexOperation)
            val firstNumber = getNumber(startPart, true)

            var endPart = text.substring(indexOperation + 1, text.length)
            val secondNumber = getNumber(endPart, false)

            if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                val result = makeOperation(operation, firstNumber, secondNumber)

                if (result != null) {

                    startPart = startPart.substring(0, startPart.lastIndexOf(firstNumber))
                    endPart = endPart.replace(secondNumber, "")

                    return startPart + result + endPart

                } else Toast.makeText(this, "???????????? ?????? ???????????????? ????????????", Toast.LENGTH_SHORT).show()
            }
            else Toast.makeText(this, "???????????? ?????? ?????????????????? ??????????", Toast.LENGTH_SHORT).show()
        }
        else Toast.makeText(this,"???????????????? ??????????????????????", Toast.LENGTH_SHORT).show()
        return null
    }

    // ?????????????????? ??????????
    private fun getNumber(text: String, firstNumber: Boolean): String{
        var number: String = ""

        if (firstNumber) {
            for (i in text.length - 1 downTo 0) {
                if (text[i].isDigit())
                    number = text[i] + number
                else break
            }
        }
        else {
            for (i in text.indices) {
                if (text[i].isDigit())
                    number += text[i]
                else break
            }
        }

        return number
    }

    // ?????????????? ????????????????
    private fun makeOperation(operation: Char, firstNumber: String, secondNumber: String): String? {
        when(operation){
            '+' -> {return (firstNumber.toInt() + secondNumber.toInt()).toString()}
            '-' -> {return (firstNumber.toInt() - secondNumber.toInt()).toString()}
            '*' -> {return (firstNumber.toInt() * secondNumber.toInt()).toString()}
            '/' -> {return (firstNumber.toInt() / secondNumber.toInt()).toString()}
        }
        return null
    }
}