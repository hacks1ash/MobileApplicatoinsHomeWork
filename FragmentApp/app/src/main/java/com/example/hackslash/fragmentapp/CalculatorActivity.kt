package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_calculator.*

class CalculatorActivity : AppCompatActivity() {

    private var operable = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calculator)

        calculatorButtonDelete.setOnClickListener {
            clearNumbers()
        }

        calculatorButtonEquals.setOnClickListener {
            answer()
        }
    }

    override fun onBackPressed() {
        clearNumbers()
    }

    fun numPad(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        calculatorTextView.append(buttonText)
    }

    private fun clearNumbers() {
        if (!calculatorTextView.text.toString().isEmpty()) {
            calculatorTextView.text = ""
        }
        if (!calculatorFuncTextView.text.toString().isEmpty()) {
            calculatorFuncTextView.text = ""
        }
        if (!calculatorEnteredTextView.text.toString().isEmpty()) {
            calculatorEnteredTextView.text = ""
        }
        operable = true
    }

    fun funcPad(view: View) {
        if (operable) {
            if (!calculatorTextView.text.toString().isEmpty()) {
                val button = view as Button
                val buttonText = button.text.toString()
                calculatorFuncTextView.append(buttonText)
                calculatorEnteredTextView.text = calculatorTextView.text.toString()
                calculatorTextView.text = ""
                operable = false
            }
        }
    }

    private fun answer() {
        if (!calculatorFuncTextView.text.toString().isEmpty() && !calculatorTextView.text.toString().isEmpty() && !calculatorEnteredTextView.text.toString().isEmpty()) {
            val firstEnteredNumber = calculatorEnteredTextView.text.toString().toDouble()
            val secondEnteredNumber = calculatorTextView.text.toString().toDouble()
            val function = calculatorFuncTextView.text.toString()
            when {
                function.equals("/") -> {
                    if ((firstEnteredNumber / secondEnteredNumber).toInt().compareTo(firstEnteredNumber / secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber / secondEnteredNumber).toInt()
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber / secondEnteredNumber
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    }
                }
                function.equals("*") -> {
                    if ((firstEnteredNumber * secondEnteredNumber).toInt().compareTo(firstEnteredNumber * secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber * secondEnteredNumber).toInt()
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber * secondEnteredNumber
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    }
                }
                function.equals("+") -> {
                    if ((firstEnteredNumber + secondEnteredNumber).toInt().compareTo(firstEnteredNumber + secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber + secondEnteredNumber).toInt()
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber + secondEnteredNumber
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    }
                }
                function.equals("-") -> {
                    if ((firstEnteredNumber - secondEnteredNumber).toInt().compareTo(firstEnteredNumber - secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber - secondEnteredNumber).toInt()
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber - secondEnteredNumber
                        calculatorEnteredTextView.text = ""
                        calculatorFuncTextView.text = ""
                        calculatorTextView.text = answer.toString()
                    }
                }
            }
            operable = true
        }

    }
}
