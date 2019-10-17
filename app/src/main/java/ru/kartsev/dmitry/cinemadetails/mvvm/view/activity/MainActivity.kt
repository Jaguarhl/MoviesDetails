package ru.kartsev.dmitry.cinemadetails.mvvm.view.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.kartsev.dmitry.cinemadetails.R

class MainActivity : AppCompatActivity() {

    /** Section: Private Properties. */
    lateinit var navController: NavController

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        navController = Navigation.findNavController(this, R.id.container)
    }
}