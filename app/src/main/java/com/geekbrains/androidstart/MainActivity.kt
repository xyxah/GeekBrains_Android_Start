package com.geekbrains.androidstart

import android.content.Intent
import android.os.Bundle
import android.os.Parcelable
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
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

    private fun initView() {
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
        binding.calcBtnEquals.setOnClickListener { calculate() }

        //settings
        binding.btnSettings.setOnClickListener {
            val runSettings = Intent(this@MainActivity, SettingsActivity::class.java)
            startActivity(runSettings)
        }

        //theme
//        binding.btnSettings.setOnClickListener {
//            if (binding.btnSettings!!.isChecked) AppCompatDelegate.setDefaultNightMode(
//                AppCompatDelegate.MODE_NIGHT_YES
//            )
//            else AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
//        }
    }

    private fun calcBtnNumberClick(text: String) {
        binding.calcText.append(text)
    }

    private fun calculate() {

        var text: String = binding.calcText.text.toString()

        var finalText: String? = ""
        var countOperations = getCountOperations(text, operations)

        while (countOperations != 0) {
            finalText = getMainOperation(text, operations)
            if (finalText != null)
                text = finalText
            countOperations--
        }

        if (!finalText.isNullOrEmpty()) binding.calcText.text = finalText
        else Toast.makeText(this, "Ошибка", Toast.LENGTH_SHORT).show()
    }

    // Получение количества операций
    private fun getCountOperations(text: String, operations: Map<Int, Char>): Int {
        var count = 0
        for (i in 0 until operations.count())
            count += text.filter { it == operations[i] }.count()
        return count
    }

    // Получение главной операции
    private fun getMainOperation(text: String, operations: Map<Int, Char>): String? {
        var mainOperation: Char? = null

        for (i in 0 until operations.count())
            if (operations[i]?.let { text.contains(it) } == true) mainOperation = operations[i]
        return cropText(mainOperation, text)
    }

    // Обрезка текста
    private fun cropText(operation: Char?, text: String): String? {
        if (operation != null) {

            val indexOperation = text.indexOf(operation)

            var startPart = text.substring(0, indexOperation)
            val firstNumber = getNumber(startPart, true)

            var endPart = text.substring(indexOperation + 1, text.length)
            val secondNumber = getNumber(endPart, false)

            //todo доделать операции с отрицательными и дробными числами
            if (firstNumber.isEmpty() && secondNumber.isNotEmpty()) return text

            if (firstNumber.isNotEmpty() && secondNumber.isNotEmpty()) {
                val result = makeOperation(operation, firstNumber, secondNumber)

                if (result != null) {
                    startPart = startPart.substring(0, startPart.lastIndexOf(firstNumber))
                    endPart = endPart.replace(secondNumber, "")

                    return startPart + result + endPart
                } else Toast.makeText(this, "Ошибка при сложении частей", Toast.LENGTH_SHORT).show()
            } else Toast.makeText(this, "Ошибка при получение чисел", Toast.LENGTH_SHORT).show()
        } else Toast.makeText(this, "Операция отсутствует", Toast.LENGTH_SHORT).show()
        return null
    }

    // Получение числа
    private fun getNumber(text: String, firstNumber: Boolean): String {
        var number = ""

        if (firstNumber) {
            for (i in text.length - 1 downTo 0) {
                if (text[i].isDigit())
                    number = text[i] + number
                else break
            }
        } else {
            for (i in text.indices) {
                if (text[i].isDigit())
                    number += text[i]
                else break
            }
        }

        return number
    }

    // Подсчет операции
    private fun makeOperation(operation: Char, firstNumber: String, secondNumber: String): String? {
        when (operation) {
            '+' -> {
                return (firstNumber.toInt() + secondNumber.toInt()).toString()
            }
            '-' -> {
                return (firstNumber.toInt() - secondNumber.toInt()).toString()
            }
            '*' -> {
                return (firstNumber.toInt() * secondNumber.toInt()).toString()
            }
            '/' -> {
                return (firstNumber.toInt() / secondNumber.toInt()).toString()
            }
        }
        return null
    }
}