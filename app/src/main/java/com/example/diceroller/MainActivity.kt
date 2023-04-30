package com.example.diceroller

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.core.content.ContextCompat
import com.example.diceroller.databinding.ActivityMainBinding
import kotlin.random.Random
import kotlin.system.exitProcess

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private var recentTrials = ArrayList<Int>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        supportActionBar?.hide()

        paintTheBackground()
        var hidden = true
        binding.apply {
            changeColorButton.setOnClickListener { paintTheBackground() }

            diceImage.setOnClickListener { rollTheDice() }

            recordButton.setOnClickListener {
                hidden = !hidden
                if(hidden) {
                    recentHeader.gone()
                    attemptCounter.gone()
                    trials.gone()
                } else {
                    recentHeader.visible()
                    attemptCounter.visible()
                    trials.visible()
                }
            }
        }
    }

    private fun paintTheBackground() {
        val colorCode: Int = getRandomColor()
        binding.parentLayout.setBackgroundResource(colorCode)
        window.statusBarColor = ContextCompat.getColor(this, colorCode)
        window.navigationBarColor = ContextCompat.getColor(this, colorCode)
    }

    private fun rollTheDice() {
        val diceNumber = Random.nextInt(6)+1
        recentTrials.add(diceNumber)

        val diceFace: Int = when(diceNumber) {
            1 -> R.drawable.dice_1
            2 -> R.drawable.dice_2
            3 -> R.drawable.dice_3
            4 -> R.drawable.dice_4
            5 -> R.drawable.dice_5
            else -> {R.drawable.dice_6}
        }
        binding.diceImage.setImageResource(diceFace)
        binding.trials.text = recentTrials.toString()
        binding.attemptCounter.text = recentTrials.size.toString()
    }

    private fun getRandomColor(): Int {
        val colorCode = ArrayList<Int>()

        for (i in 1..15) {
            val color = resources.getIdentifier("color$i", "color", packageName)
            colorCode.add(color)
        }

        return colorCode[Random.nextInt(colorCode.size)]
    }

    private fun View.gone() {
        this.visibility = View.GONE
    }

    private fun View.visible() {
        this.visibility = View.VISIBLE
    }

    // Ask again for exit
    private var backPressedTime: Long = 0
    override fun onBackPressed() {

        if(backPressedTime+2000 > System.currentTimeMillis()) {
            super.onBackPressed()
            exitProcess(0)
        }
        Toast.makeText(this,"Press again to exit",Toast.LENGTH_SHORT).show()
        backPressedTime = System.currentTimeMillis()
    }
}
