package com.example.videogames.view.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import com.example.videogames.R
import com.example.videogames.databinding.ActivityEditBinding
import com.example.videogames.db.DbCar
import com.example.videogames.model.Car

class EditActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityEditBinding
    private var marca = ""
    private lateinit var dbCar : DbCar
    var car: Car? =null
    var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityEditBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.planetsSpinnerEdit
        spinner.onItemSelectedListener = this
// Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter.createFromResource(
            this,
            R.array.formulas_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            // Apply the adapter to the spinner
            spinner.adapter = adapter
        }

        val bundle = intent.extras
        dbCar = DbCar(this)

        if (bundle != null) {
            id=bundle.getInt("ID")
            car = dbCar.getCar(id)
            car?.let{
                with(binding){
                    for (i in 0 until spinner.count) {
                        if (spinner.getItemAtPosition(i).toString().equals(it.marca)) {
                            spinner.setSelection(i)
                            break
                        }
                    }
                    tietTitle.setText(it.modelo)
                    tietGenre.setText(it.ano)
                    tietDeveloper.setText(it.color)
                }
            }
        }
    }

    override fun onItemSelected(parent: AdapterView<*>, view: View?, pos: Int, id: Long) {
        marca = parent.getItemAtPosition(pos).toString()

        when(marca){
            parent.getItemAtPosition(0).toString() -> {
                binding.ivHeader.setImageResource(R.drawable.mercedes)
            }
            parent.getItemAtPosition(1).toString() ->{
                binding.ivHeader.setImageResource(R.drawable.bmw)
            }
            parent.getItemAtPosition(2).toString() -> {
                binding.ivHeader.setImageResource(R.drawable.audi)
            }
            else ->{
                binding.ivHeader.visibility = View.GONE
            }
        }
    }

    override fun onNothingSelected(parent: AdapterView<*>) {

    }

    override fun onBackPressed(){
        super.onBackPressed()
        val intent = Intent(this,DetailsActivity::class.java)
        intent.putExtra("ID",id)
        startActivity(intent)
        finish()
    }

    fun clickUpdate(view: View) {
        with(binding){
            when{
                tietTitle.text.toString().isEmpty() ->{
                    tietTitle.error = resources.getString(R.string.no_vacio)
                }
                tietDeveloper.text.toString().isEmpty() ->{
                    tietDeveloper.error = resources.getString(R.string.no_vacio)
                }
                tietGenre.text.toString().isEmpty() ->{
                    tietGenre.error = resources.getString(R.string.no_vacio)
                }
                else ->{
                    val id_update = dbCar.updateCar(id,
                        planetsSpinnerEdit.selectedItem as String,tietTitle.text.toString(),tietGenre.text.toString().toInt(),tietDeveloper.text.toString())
                    if(id_update){
                        Toast.makeText(this@EditActivity,resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                        val intent = Intent(this@EditActivity,DetailsActivity::class.java)
                        intent.putExtra("ID",id)
                        startActivity(intent)
                        finish()
                    }else{
                        Toast.makeText(this@EditActivity,resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
                }
            }
        }
    }
}