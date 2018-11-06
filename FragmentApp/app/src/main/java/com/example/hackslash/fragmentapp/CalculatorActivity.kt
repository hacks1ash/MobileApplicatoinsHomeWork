package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.widget.Button
import kotlinx.android.synthetic.main.activity_calculator.*

//import net.objecthunter.exp4j.ExpressionBuilder

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

    fun numberOnClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        calculatorTextView.append(buttonText)
        operable = true
    }

    private fun clearNumbers() {
        calculatorTextView.text = ""
        calculatorFuncTextView.text = ""
        calculatorEnteredTextView.text = ""
        operable = true
    }

    fun operationsOnClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        if (operable && calculatorFuncTextView.text.toString().isNotEmpty() && calculatorTextView.text.toString().isNotEmpty()) {
            answer()
            calculatorEnteredTextView.text = calculatorTextView.text.toString()
            calculatorTextView.text = ""
            calculatorFuncTextView.text = buttonText
        } else if (operable && calculatorTextView.text.toString().isNotEmpty()) {
            calculatorFuncTextView.text = buttonText
            calculatorEnteredTextView.text = calculatorTextView.text.toString()
            calculatorTextView.text = ""
        }
        operable = false
    }

    private fun answer() {
        if (calculatorFuncTextView.text.toString().isNotEmpty() && calculatorTextView.text.toString().isNotEmpty() && calculatorEnteredTextView.text.toString().isNotEmpty()) {
            val firstEnteredNumber = calculatorEnteredTextView.text.toString().toDouble()
            val secondEnteredNumber = calculatorTextView.text.toString().toDouble()
            val function = calculatorFuncTextView.text.toString()
            when (function) {
                ("/") -> {
                    if ((firstEnteredNumber / secondEnteredNumber).toLong().compareTo(firstEnteredNumber / secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber / secondEnteredNumber).toLong()
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber / secondEnteredNumber
                        calculatorTextView.text = answer.toString()
                    }
                }
                ("*") -> {
                    if ((firstEnteredNumber * secondEnteredNumber).toLong().compareTo(firstEnteredNumber * secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber * secondEnteredNumber).toLong()
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber * secondEnteredNumber
                        calculatorTextView.text = answer.toString()
                    }
                }
                ("+") -> {
                    if ((firstEnteredNumber + secondEnteredNumber).toLong().compareTo(firstEnteredNumber + secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber + secondEnteredNumber).toLong()
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber + secondEnteredNumber
                        calculatorTextView.text = answer.toString()
                    }
                }
                ("-") -> {
                    if ((firstEnteredNumber - secondEnteredNumber).toLong().compareTo(firstEnteredNumber - secondEnteredNumber) == 0) {
                        val answer = (firstEnteredNumber - secondEnteredNumber).toLong()
                        calculatorTextView.text = answer.toString()
                    } else {
                        val answer = firstEnteredNumber - secondEnteredNumber
                        calculatorTextView.text = answer.toString()
                    }
                }
            }
            calculatorEnteredTextView.text = ""
            calculatorFuncTextView.text = ""
            operable = true
        }

    }

    /*fun calculatorOnClick(view: View) {
        val button = view as Button
        val buttonText = button.text.toString()
        calculatorTextView.append(buttonText)
    }

    fun clearTextView(view: View) {
        calculatorTextView.text = ""
    }

    fun equalsOnClick(view: View) {
        val expression = ExpressionBuilder(calculatorTextView.text.toString()).build()
        val result = expression.evaluate()
        val longResult = result.toLong()
        if (result == longResult.toDouble()) {
            calculatorTextView.text = ""
            calculatorTextView.text = longResult.toString()
        } else {
            calculatorTextView.text = ""
            calculatorTextView.text = result.toString()
        }
    }*/

}