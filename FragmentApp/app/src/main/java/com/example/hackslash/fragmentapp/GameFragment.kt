package com.example.hackslash.fragmentapp

import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import kotlinx.android.synthetic.main.fragment_game.*

class GameFragment : Fragment() {

    private var clicked: Boolean = false
    private var winner: Boolean = false

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_game, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        gameEndPopUp.visibility = View.GONE

        gamePlayAgainButton.setOnClickListener {
            playAgain()
        }
        gameExitButton.setOnClickListener {
            signInFragment()
        }

        buttonClick()
    }

    private fun buttonClick() {
        gameFragmentBtn1.setOnClickListener { clickButtons(gameFragmentBtn1);game() }
        gameFragmentBtn2.setOnClickListener { clickButtons(gameFragmentBtn2);game() }
        gameFragmentBtn3.setOnClickListener { clickButtons(gameFragmentBtn3);game() }
        gameFragmentBtn4.setOnClickListener { clickButtons(gameFragmentBtn4);game() }
        gameFragmentBtn5.setOnClickListener { clickButtons(gameFragmentBtn5);game() }
        gameFragmentBtn6.setOnClickListener { clickButtons(gameFragmentBtn6);game() }
        gameFragmentBtn7.setOnClickListener { clickButtons(gameFragmentBtn7);game() }
        gameFragmentBtn8.setOnClickListener { clickButtons(gameFragmentBtn8);game() }
        gameFragmentBtn9.setOnClickListener { clickButtons(gameFragmentBtn9);game() }
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

    private fun checkEmpty(btn1: Button, btn2: Button, btn3: Button): Boolean {
        return !btn1.text.toString().isEmpty() && !btn2.text.toString().isEmpty() && !btn3.text.toString().isEmpty()
    }

    private fun checkWinner(btn1: Button, btn2: Button, btn3: Button) {
        if (btn1.text.toString().equals(btn2.text.toString()) && btn1.text.toString().equals(btn3.text.toString()) &&
            checkEmpty(btn1, btn2, btn3)
        ) {
            when {
                btn1.text.toString().equals("X") -> {
                    gameWinnerTextView.text = getString(R.string.first_player_winner)
                    winner = true
                }
                btn1.text.toString().equals("O") -> {
                    gameWinnerTextView.text = getString(R.string.second_player_winner)
                    winner = true
                }
            }
        }
    }

    private fun checkDraw(
        btn1: Button,
        btn2: Button,
        btn3: Button,
        btn4: Button,
        btn5: Button,
        btn6: Button,
        btn7: Button,
        btn8: Button,
        btn9: Button
    ) {
        if (checkEmpty(btn1, btn2, btn3) && checkEmpty(btn4, btn5, btn6) && checkEmpty(btn7, btn8, btn9)) {
            when {
                !winner -> {
                    gameWinnerTextView.text = getString(R.string.draw)
                    winner = true
                }
            }
        }
    }

    private fun endGame() {
        if (winner) {
            gameFragmentBtn1.isEnabled = false
            gameFragmentBtn2.isEnabled = false
            gameFragmentBtn3.isEnabled = false
            gameFragmentBtn4.isEnabled = false
            gameFragmentBtn5.isEnabled = false
            gameFragmentBtn6.isEnabled = false
            gameFragmentBtn7.isEnabled = false
            gameFragmentBtn8.isEnabled = false
            gameFragmentBtn9.isEnabled = false
            gameEndPopUp.visibility = View.VISIBLE
        }
    }

    private fun game() {
        checkWinner(gameFragmentBtn1, gameFragmentBtn2, gameFragmentBtn3)
        checkWinner(gameFragmentBtn4, gameFragmentBtn5, gameFragmentBtn6)
        checkWinner(gameFragmentBtn7, gameFragmentBtn8, gameFragmentBtn9)
        checkWinner(gameFragmentBtn1, gameFragmentBtn4, gameFragmentBtn7)
        checkWinner(gameFragmentBtn2, gameFragmentBtn5, gameFragmentBtn8)
        checkWinner(gameFragmentBtn3, gameFragmentBtn6, gameFragmentBtn9)
        checkWinner(gameFragmentBtn1, gameFragmentBtn5, gameFragmentBtn9)
        checkWinner(gameFragmentBtn3, gameFragmentBtn5, gameFragmentBtn7)
        checkDraw(
            gameFragmentBtn1,
            gameFragmentBtn2,
            gameFragmentBtn3,
            gameFragmentBtn4,
            gameFragmentBtn5,
            gameFragmentBtn6,
            gameFragmentBtn7,
            gameFragmentBtn8,
            gameFragmentBtn9
        )
        endGame()
    }

    private fun playAgain() {
        gameFragmentBtn1.text = ""
        gameFragmentBtn2.text = ""
        gameFragmentBtn3.text = ""
        gameFragmentBtn4.text = ""
        gameFragmentBtn5.text = ""
        gameFragmentBtn6.text = ""
        gameFragmentBtn7.text = ""
        gameFragmentBtn8.text = ""
        gameFragmentBtn9.text = ""

        gameFragmentBtn1.isEnabled = true
        gameFragmentBtn2.isEnabled = true
        gameFragmentBtn3.isEnabled = true
        gameFragmentBtn4.isEnabled = true
        gameFragmentBtn5.isEnabled = true
        gameFragmentBtn6.isEnabled = true
        gameFragmentBtn7.isEnabled = true
        gameFragmentBtn8.isEnabled = true
        gameFragmentBtn9.isEnabled = true

        gameEndPopUp.visibility = View.GONE

        winner = false
        clicked = false
    }

    private fun signInFragment() {
        val fragment = SignInFragment()
        val manager = activity?.supportFragmentManager
        val transaction = manager?.beginTransaction()
        transaction?.replace(R.id.contentConstraintLayout, fragment)
        transaction?.addToBackStack(null)
        transaction?.commit()
    }
}