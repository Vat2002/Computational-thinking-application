package com.example.mobilecoursework


import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

class About : androidx.fragment.app.DialogFragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.about_activity, container, false)
    }
}

/*class About : AppCompatActivity (){

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.about_activity)
    }
}*/