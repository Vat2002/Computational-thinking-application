package com.example.mobilecoursework

import android.annotation.SuppressLint
import android.graphics.Color
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.CountDownTimer
import android.widget.Button
import android.widget.TextView
import kotlin.random.Random

class NewGame : AppCompatActivity() {

    private var operators = listOf("+","-","/","*") //the list of operators used to create the arithmetic equations
    private var firstValueStr: String? = null //the first integer value randomly generated in string
    private var secondValueStr: String? = null //the second integer value randomly generated in string
    private var equation1Len: Int? = 0 //the length of the equation which is generated randomly
    private var equation2Len: Int? = 0 //the length of the equation which is generated randomly
    private var sumValueInt: Int? = 0 //the sum of the equation 1 which is solved
    private var sumValueInt2: Int? = 0 //the sum of the equation 1 which is solved
    private var arithmeticEquation1Str: String? = null //the arithmetic equation 1 generated in string to view in text field
    private var arithmeticEquation2Str: String? = null //the arithmetic equation 2 generated in string to view in text field
    private lateinit var eq1 : TextView //variable used to store  the 1st arithmetic equation
    private lateinit var eq2 : TextView //variable used to store  the 2nd arithmetic equation
    private var correctAnswers:Int = 0 //the number of correct answers selected
    private var incorrectAnswers:Int = 0 //the number of incorrect answers selected
    var correctAnswersChange : Int = 0 //the number of correct answers which needs to be reset when it is 5
    var timerSeconds : Int = 50 //the no of seconds for the countdown

    @SuppressLint("SetTextI18n")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.newgame_activity)

        eq1 = findViewById(R.id.textView1)  //variable used to store the equation 1 in the textview1
        eq2 = findViewById(R.id.textView2) //variable used to store the equation 2 in the textview2
        val greaterBt = findViewById<Button>(R.id.greaterButton)
        val lesserBt = findViewById<Button>(R.id.lesserButton)
        val equalBt = findViewById<Button>(R.id.equalButton)
        val result = findViewById<TextView>(R.id.resultId)

        startATimer() //calling the timer function to start the timer
        startANewGame(eq1,1) //generating the 2 equations by calling the function
        startANewGame(eq2,2)

        greaterBt.setOnClickListener {
            if(sumValueInt!! > sumValueInt2!!){ //if the condition is met the result is set to correct
                result.text = "CORRECT!"
                result.setTextColor(Color.GREEN)
                correctAnswers += 1 //correct answers is incremented
                correctAnswersChange +=1
                startANewGame(eq1,1) //a new set of equations is set
                startANewGame(eq2,2)
            }
            else{
                result.text = "WRONG!"
                result.setTextColor(Color.RED)
                incorrectAnswers += 1 //incorrect answers is incremented
                startANewGame(eq1,1)//a new set of equations is set
                startANewGame(eq2,2)
            }
        }

        lesserBt.setOnClickListener {
            if(sumValueInt!! < sumValueInt2!!){
                result.text = "CORRECT!"
                result.setTextColor(Color.GREEN)
                correctAnswers += 1
                correctAnswersChange +=1

                startANewGame(eq1,1)
                startANewGame(eq2,2)
            }
            else{
                result.text = "WRONG!"
                result.setTextColor(Color.RED)
                incorrectAnswers += 1
                startANewGame(eq1,1)
                startANewGame(eq2,2)

            }
        }

        equalBt.setOnClickListener {
            if(sumValueInt == sumValueInt2){
                result.text = "CORRECT!"
                result.setTextColor(Color.GREEN)
                correctAnswers += 1
                correctAnswersChange +=1

                startANewGame(eq1,1)
                startANewGame(eq2,2)
            }
            else{
                result.text = "WRONG!"
                result.setTextColor(Color.RED)
                incorrectAnswers += 1
                startANewGame(eq1,1)
                startANewGame(eq2,2)
            }
        }
    }

    //https://developer.android.com/guide/topics/resources/runtime-changes
    override fun onSaveInstanceState(outState: Bundle) { //this function will save the values of the variables which is used before the configuration change
        super.onSaveInstanceState(outState)
        outState.putInt("Correct", correctAnswers) //inserting the value of the correctAnswers
        outState.putInt("Incorrect",incorrectAnswers)
        outState.putInt("Timer",timerSeconds)
        sumValueInt?.let { outState.putInt("Sum of 1", it) }
        sumValueInt2?.let { outState.putInt("Sum of 2", it) }
        outState.putString("Arithmetic expression 1",arithmeticEquation1Str)
        outState.putString("Arithmetic expression 2",arithmeticEquation2Str)
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) { //this function will restore the values of the variables which is used before the configuration change
        super.onRestoreInstanceState(savedInstanceState)

        correctAnswers = savedInstanceState.getInt("Correct",0) //getting the number of correctsAnswers
        incorrectAnswers = savedInstanceState.getInt("Incorrect",0)
        timerSeconds = savedInstanceState.getInt("Timer",0)
        sumValueInt = savedInstanceState.getInt("Sum of 1",0)
        sumValueInt2 = savedInstanceState.getInt("Sum of 2",0)
        arithmeticEquation1Str = savedInstanceState.getString("Arithmetic expression 1",null)
        arithmeticEquation2Str = savedInstanceState.getString("Arithmetic expression 2",null)

        eq1.text = arithmeticEquation1Str
        eq2.text = arithmeticEquation2Str

    }

    private fun randomOperator():Int{ //generating a random integer to decide the operator which is going to be used
        return Random.nextInt(3)
    }

    private fun randomLengthOfEquation():Int{ //generating a random integer to get the number of terms of the equation
        return Random.nextInt(1,4)
    }

    private fun randomValueBetweenRange():Int{ //generating a random integer for the terms
        return Random.nextInt(1,20)
    }

    private fun startANewGame(arithmeticEquation:TextView, equationNumber:Int ){ //loads the two generated equation
        when (equationNumber){
            1 ->{
                firstValueStr = randomValueBetweenRange().toString() //randomly generated value is turned to string to create the equation
                equation1Len = randomLengthOfEquation() //generating the number of terms for the equations
                createArithmeticEquation(firstValueStr!!, arithmeticEquation,equation1Len!!, firstValueStr!!.toInt(),equationNumber)//calling the function used create the arithmetic equation
            }
            2 ->{
                secondValueStr = randomValueBetweenRange(). toString()
                equation2Len = randomLengthOfEquation()
                createArithmeticEquation(secondValueStr!!, arithmeticEquation, equation2Len!!, secondValueStr!!.toInt(), equationNumber)
            }
        }
    }

    private fun createArithmeticEquation(
        firstTerm: String,
        arithmeticEquation: TextView,
        noOfTerms1: Int,
        final: Int,
        equationNumber: Int
    ) {
        val operator = randomOperator() //loads a random operator from the list initialized
        var finalArithmeticAns = final
        var termCount = noOfTerms1
        var equations: String? = null
        var value2: Int

        while (true) {
            value2 = randomValueBetweenRange() //generates a random value for the value2
            when (operator) { //use of case to do the correct function for the generated operator
                0 -> {
                    if ((finalArithmeticAns + value2) <= 100) { //validates the if the value is less than 100
                        finalArithmeticAns += value2
                        break
                    } else {
                        continue
                    }
                }
                1 -> {
                    if ((finalArithmeticAns - value2) <= 100) {
                        finalArithmeticAns -= value2
                        break
                    } else {
                        continue
                    }
                }
                2 -> {
                    if ((finalArithmeticAns * value2) <= 100) {
                        finalArithmeticAns *= value2
                        break
                    } else {
                        continue
                    }
                }
                3 -> {
                    if (((finalArithmeticAns / value2) <= 100) && (finalArithmeticAns % value2 == 0)) { //checks if the number is a whole number
                        finalArithmeticAns /= value2
                        break
                    } else {
                        continue
                    }
                }
            }
        }
        when (equationNumber) {
            1 -> { //creating the equation as a string
                arithmeticEquation1Str = firstTerm + operators[operator] + value2
                equations = arithmeticEquation1Str
            }
            2 -> {
                arithmeticEquation2Str = firstTerm + operators[operator] + value2
                equations = arithmeticEquation2Str
            }

        }

        termCount -= 1 //decreasing the term count

        if (termCount > 0) { //The createArithmeticEquation function is called recursively until the term count is 0
            createArithmeticEquation(
                "($equations)",
                arithmeticEquation,
                termCount,
                finalArithmeticAns,
                equationNumber
            )
        } else {
            when (equationNumber) {
                1 -> {
                    sumValueInt = finalArithmeticAns
                }
                2 -> {
                    sumValueInt2 = finalArithmeticAns
                }
            }
            arithmeticEquation.text = equations //setting the equation to the textview
        }
    }

    //https://developer.android.com/reference/kotlin/android/os/CountDownTimer
    private fun startATimer(){ //timer function
        val timerView = findViewById<TextView>(R.id.timerId)
        object : CountDownTimer(50000, 1000) { //setting the timer for 50secs
            @SuppressLint("SetTextI18n")
            override fun onTick(millisUntilFinished: Long) {
                if(correctAnswersChange == 5){ //when 5 corrects answers are met the timer increases it by 10secs
                    correctAnswersChange =0
                    timerSeconds +=10
                }
                timerView.text = "Timer:$timerSeconds" //setting the timer to the textview
                timerView.setTextColor(Color.GREEN)
                if(timerSeconds <= 0){ //when the timer stops onFinish() is called to load the scorecard
                    cancel()
                    onFinish()
                }
                timerSeconds -- //timer value is decremented every round
            }
            @SuppressLint("SetTextI18n")
            override fun onFinish() {
                scorecard() //calling the scorecard function
            }
        }.start() //starting the countdown
    }

    @SuppressLint("SetTextI18n")
    fun scorecard() {
        setContentView(R.layout.scoreboard_activity)

        val displayCorrectAnswers = findViewById<TextView>(R.id.correctValue) //variable used to store the correct answers in the textview
        val displayIncorrectAnswers = findViewById<TextView>(R.id.incorrectValue)

        displayCorrectAnswers.text = "Correct Answers: $correctAnswers" //setting the no of correct answers to the textview
        displayCorrectAnswers.setTextColor(Color.GREEN) //setting the view color as green of the text

        displayIncorrectAnswers.text = "Incorrect Answers: $incorrectAnswers" //settings the no of incorrect answers to the textview
        displayIncorrectAnswers.setTextColor(Color.RED) //setting the view color as red of the text
    }
}