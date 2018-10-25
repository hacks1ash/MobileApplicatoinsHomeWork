package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.Toast
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    private var clicked: Boolean = true

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkClick()
    }

    private fun checkClick() {
        gameFragamentBtn1.setOnClickListener { clickButtons(gameFragamentBtn1);checkWinner() }
        gameFragamentBtn2.setOnClickListener { clickButtons(gameFragamentBtn2);checkWinner() }
        gameFragamentBtn3.setOnClickListener { clickButtons(gameFragamentBtn3);checkWinner() }
        gameFragamentBtn4.setOnClickListener { clickButtons(gameFragamentBtn4);checkWinner() }
        gameFragamentBtn5.setOnClickListener { clickButtons(gameFragamentBtn5);checkWinner() }
        gameFragamentBtn6.setOnClickListener { clickButtons(gameFragamentBtn6);checkWinner() }
        gameFragamentBtn7.setOnClickListener { clickButtons(gameFragamentBtn7);checkWinner() }
        gameFragamentBtn8.setOnClickListener { clickButtons(gameFragamentBtn8);checkWinner() }
        gameFragamentBtn9.setOnClickListener { clickButtons(gameFragamentBtn9);checkWinner() }
    }

    private fun clickButtons(button: Button) {
        if (button.text.toString().isEmpty()) {
            if (clicked) {
                button.text = "X"
                clicked = false
            } else {
                button.text = "O"
                clicked = true
            }
        }
    }

    private fun checkWinner() {
        if (gameFragamentBtn1.text.toString().equals(gameFragamentBtn2.text.toString()) && gameFragamentBtn1.text.toString().equals(
                gameFragamentBtn3.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn1, gameFragamentBtn2, gameFragamentBtn3)
        } else if (gameFragamentBtn4.text.toString().equals(gameFragamentBtn5.text.toString()) && gameFragamentBtn4.text.toString().equals(
                gameFragamentBtn6.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn4, gameFragamentBtn5, gameFragamentBtn6)
        } else if (gameFragamentBtn7.text.toString().equals(gameFragamentBtn8.text.toString()) && gameFragamentBtn7.text.toString().equals(
                gameFragamentBtn9.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn7, gameFragamentBtn8, gameFragamentBtn9)
        } else if (gameFragamentBtn1.text.toString().equals(gameFragamentBtn4.text.toString()) && gameFragamentBtn1.text.toString().equals(
                gameFragamentBtn7.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn1, gameFragamentBtn4, gameFragamentBtn7)
        } else if (gameFragamentBtn2.text.toString().equals(gameFragamentBtn5.text.toString()) && gameFragamentBtn2.text.toString().equals(
                gameFragamentBtn8.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn2, gameFragamentBtn5, gameFragamentBtn8)
        } else if (gameFragamentBtn3.text.toString().equals(gameFragamentBtn6.text.toString()) && gameFragamentBtn3.text.toString().equals(
                gameFragamentBtn9.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn3, gameFragamentBtn6, gameFragamentBtn9)
        } else if (gameFragamentBtn1.text.toString().equals(gameFragamentBtn5.text.toString()) && gameFragamentBtn1.text.toString().equals(
                gameFragamentBtn9.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn1, gameFragamentBtn5, gameFragamentBtn9)
        } else if (gameFragamentBtn3.text.toString().equals(gameFragamentBtn5.text.toString()) && gameFragamentBtn3.text.toString().equals(
                gameFragamentBtn7.text.toString()
            )
        ) {
            checkEmpty(gameFragamentBtn3, gameFragamentBtn5, gameFragamentBtn7)
        }
    }

    private fun checkEmpty(btn1: Button, btn2: Button, btn3: Button) {
        if (!btn1.text.toString().isEmpty() && !btn2.text.toString().isEmpty() && !btn3.text.toString().isEmpty()) {
            if (btn1.text.toString().equals("X")) {
                Toast.makeText(activity, "Player 1 is the Winner", Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, "Player 2 is the Winner", Toast.LENGTH_LONG).show()
            }
        }
    }
}