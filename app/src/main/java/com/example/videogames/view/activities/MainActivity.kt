package com.example.videogames.view.activities

import CarsAdapter
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.videogames.databinding.ActivityMainBinding
import com.example.videogames.db.DbCar
import com.example.videogames.model.Car

class MainActivity : AppCompatActivity() {

    private lateinit var binding : ActivityMainBinding
    private lateinit var listCars : ArrayList<Car>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val dbCar = DbCar(this)

        listCars = dbCar.getCars()

        val carsAdapter = CarsAdapter(this,listCars)

        binding.rvGames.layoutManager = LinearLayoutManager(this)
        binding.rvGames.adapter = carsAdapter

        if(listCars.size == 0) binding.tvSinRegistros.visibility = View.VISIBLE
        else binding.tvSinRegistros.visibility = View.INVISIBLE

    }

    fun click(view: View) {
        startActivity(Intent(this,InsertActivity::class.java))
        finish()
    }

    fun selectedCar(car:Car){
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("ID",car.id)
        startActivity(intent)
        finish()
    }

}