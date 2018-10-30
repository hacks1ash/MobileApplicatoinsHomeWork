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

    fun clearNumbers() {
        calculatorTextView.text = ""
        calculatorFuncTextView.text = ""
        calculatorEnteredTextView.text = ""
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
        when {
            calculatorFuncTextView.text.toString().equals("/") -> {
                val answer =
                    calculatorEnteredTextView.text.toString().toDouble() / calculatorTextView.text.toString().toDouble()
                calculatorEnteredTextView.text = ""
                calculatorFuncTextView.text = ""
                calculatorTextView.text = answer.toString()
            }
            calculatorFuncTextView.text.toString().equals("*") -> {
                val answer =
                    calculatorEnteredTextView.text.toString().toDouble() * calculatorTextView.text.toString().toDouble()
                calculatorEnteredTextView.text = ""
                calculatorFuncTextView.text = ""
                calculatorTextView.text = answer.toString()
            }
            calculatorFuncTextView.text.toString().equals("+") -> {
                val answer =
                    calculatorEnteredTextView.text.toString().toDouble() + calculatorTextView.text.toString().toDouble()
                calculatorEnteredTextView.text = ""
                calculatorFuncTextView.text = ""
                calculatorTextView.text = answer.toString()
            }
            calculatorFuncTextView.text.toString().equals("-") -> {
                val answer =
                    calculatorEnteredTextView.text.toString().toDouble() - calculatorTextView.text.toString().toDouble()
                calculatorEnteredTextView.text = ""
                calculatorFuncTextView.text = ""
                calculatorTextView.text = answer.toString()
            }
        }

    }
}
