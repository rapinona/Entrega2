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
import com.example.videogames.databinding.ActivityInsertBinding
import com.example.videogames.db.DbCar

class InsertActivity : AppCompatActivity(), AdapterView.OnItemSelectedListener {

    private lateinit var binding: ActivityInsertBinding
    private var marca = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityInsertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.planetsSpinnerInsert
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
            spinner.setSelection(adapter.getPosition("BMW"))
        }
    }

    fun click(view: View) {
        val dbCar = DbCar(this)

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
                    val id = dbCar.insertCar(planetsSpinnerInsert.selectedItem as String,tietTitle.text.toString(),tietGenre.text.toString().toInt(),tietDeveloper.text.toString())
                    if(id>0){
                        tietTitle.setText("")
                        tietGenre.setText("")
                        tietDeveloper.setText("")
                        tietTitle.requestFocus()
                        Toast.makeText(this@InsertActivity,resources.getString(R.string.success),Toast.LENGTH_SHORT).show()
                    }else{
                        Toast.makeText(this@InsertActivity,resources.getString(R.string.error),Toast.LENGTH_SHORT).show()
                    }
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
        startActivity(Intent(this,MainActivity::class.java))
    }

    fun clickDelete(view: View) {}
    fun clickEdit(view: View) {}
    fun clickUpdate(view: View) {}

}