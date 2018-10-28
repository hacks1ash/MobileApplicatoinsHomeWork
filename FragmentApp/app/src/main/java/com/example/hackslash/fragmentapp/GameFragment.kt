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

    private var clicked: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        checkClick()
    }

    private fun checkClick() {
        gameFragmentBtn1.setOnClickListener { clickButtons(gameFragmentBtn1);checkWinner() }
        gameFragmentBtn2.setOnClickListener { clickButtons(gameFragmentBtn2);checkWinner() }
        gameFragmentBtn3.setOnClickListener { clickButtons(gameFragmentBtn3);checkWinner() }
        gameFragmentBtn4.setOnClickListener { clickButtons(gameFragmentBtn4);checkWinner() }
        gameFragmentBtn5.setOnClickListener { clickButtons(gameFragmentBtn5);checkWinner() }
        gameFragmentBtn6.setOnClickListener { clickButtons(gameFragmentBtn6);checkWinner() }
        gameFragmentBtn7.setOnClickListener { clickButtons(gameFragmentBtn7);checkWinner() }
        gameFragmentBtn8.setOnClickListener { clickButtons(gameFragmentBtn8);checkWinner() }
        gameFragmentBtn9.setOnClickListener { clickButtons(gameFragmentBtn9);checkWinner() }
    }

    private fun clickButtons(button: Button) {
        if (button.text.toString().isEmpty()) {
            if (!clicked) {
                button.text = "X"
                clicked = true
            } else {
                button.text = "O"
                clicked = false
            }
        }
    }

    private fun checkWinner() {
        if (gameFragmentBtn1.text.toString().equals(gameFragmentBtn2.text.toString()) && gameFragmentBtn1.text.toString().equals(
                gameFragmentBtn3.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn1, gameFragmentBtn2, gameFragmentBtn3)
        } else if (gameFragmentBtn4.text.toString().equals(gameFragmentBtn5.text.toString()) && gameFragmentBtn4.text.toString().equals(
                gameFragmentBtn6.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn4, gameFragmentBtn5, gameFragmentBtn6)
        } else if (gameFragmentBtn7.text.toString().equals(gameFragmentBtn8.text.toString()) && gameFragmentBtn7.text.toString().equals(
                gameFragmentBtn9.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn7, gameFragmentBtn8, gameFragmentBtn9)
        } else if (gameFragmentBtn1.text.toString().equals(gameFragmentBtn4.text.toString()) && gameFragmentBtn1.text.toString().equals(
                gameFragmentBtn7.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn1, gameFragmentBtn4, gameFragmentBtn7)
        } else if (gameFragmentBtn2.text.toString().equals(gameFragmentBtn5.text.toString()) && gameFragmentBtn2.text.toString().equals(
                gameFragmentBtn8.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn2, gameFragmentBtn5, gameFragmentBtn8)
        } else if (gameFragmentBtn3.text.toString().equals(gameFragmentBtn6.text.toString()) && gameFragmentBtn3.text.toString().equals(
                gameFragmentBtn9.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn3, gameFragmentBtn6, gameFragmentBtn9)
        } else if (gameFragmentBtn1.text.toString().equals(gameFragmentBtn5.text.toString()) && gameFragmentBtn1.text.toString().equals(
                gameFragmentBtn9.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn1, gameFragmentBtn5, gameFragmentBtn9)
        } else if (gameFragmentBtn3.text.toString().equals(gameFragmentBtn5.text.toString()) && gameFragmentBtn3.text.toString().equals(
                gameFragmentBtn7.text.toString()
            )
        ) {
            checkEmpty(gameFragmentBtn3, gameFragmentBtn5, gameFragmentBtn7)
        }
    }

    private fun checkEmpty(btn1: Button, btn2: Button, btn3: Button) {
        if (!btn1.text.toString().isEmpty() && !btn2.text.toString().isEmpty() && !btn3.text.toString().isEmpty()) {
            if (btn1.text.toString().equals("X")) {
                Toast.makeText(activity, R.string.first_player_winner, Toast.LENGTH_LONG).show()
            } else {
                Toast.makeText(activity, R.string.second_player_winner, Toast.LENGTH_LONG).show()
            }
        }
    }
}