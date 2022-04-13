package com.example.mobilecoursework

import android.annotation.SuppressLint
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {
    @SuppressLint("ResourceType", "InflateParams")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val aboutBt = findViewById<Button>(R.id.aboutButton)
        val newGameBt = findViewById<Button>(R.id.newGameButton)

        //https://youtu.be/SkFcDWt9GV4
        aboutBt.setOnClickListener { //once the button is clicked the about popup window is opened
            val popUp = About()
            popUp.show(supportFragmentManager, "aboutPopUp")
        }

        newGameBt.setOnClickListener { //once the button is clicked the new game activity is opened
            val newGameIntent = Intent(this,NewGame::class.java)
            startActivity(newGameIntent)
        }

        /*aboutBt.setOnClickListener { //once the button is clicked the popup window is opened
           val window  = PopupWindow(this) //opening new window
           val view = layoutInflater.inflate(R.layout.about_activity,null)
           window.contentView = view
           val main = findViewById<ConstraintLayout>(R.id.mainId)
           main.setOnClickListener {
               window.dismiss()
           }
           window.showAtLocation(main,Gravity.CENTER,0,0)
       }*/
    }

}