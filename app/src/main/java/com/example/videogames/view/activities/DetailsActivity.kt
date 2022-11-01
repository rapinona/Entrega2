package com.example.videogames.view.activities

import android.content.DialogInterface
import android.content.Intent
import android.os.Bundle
import android.text.InputType
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.example.videogames.R
import com.example.videogames.databinding.ActivityDetailsBinding
import com.example.videogames.db.DbCar
import com.example.videogames.model.Car

class DetailsActivity : AppCompatActivity() , AdapterView.OnItemSelectedListener{

    private lateinit var binding: ActivityDetailsBinding
    private var marca = ""
    private lateinit var dbCar : DbCar
    var car:Car? =null
    var id=0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDetailsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val spinner: Spinner = binding.planetsSpinnerDetails
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

        val myAdap = binding.planetsSpinnerDetails.adapter

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

                    //desactivar teclado
                    tietTitle.inputType = InputType.TYPE_NULL
                    tietGenre.inputType = InputType.TYPE_NULL
                    tietDeveloper.inputType = InputType.TYPE_NULL

                }
            }
        }
        spinner.setEnabled(false);
    }

    override fun onBackPressed(){
        super.onBackPressed()
        startActivity(Intent(this,MainActivity::class.java))
    }

    fun clickEdit(view: View) {
        val intent = Intent(this,EditActivity::class.java)
        intent.putExtra("ID",id)
        startActivity(intent)
        finish()
    }
    fun clickDelete(view: View) {
        AlertDialog.Builder(this)
            .setTitle(resources.getString(R.string.confirmacion))
            .setMessage(resources.getString(R.string.seguro_eliminar,car?.modelo))
            .setPositiveButton(resources.getString(R.string.aceptar),DialogInterface.OnClickListener{
                dialog,wich ->
                    if(dbCar.deleteGame(id)){
                        Toast.makeText(this@DetailsActivity,resources.getString(R.string.success), Toast.LENGTH_SHORT).show()
                        startActivity(Intent(this@DetailsActivity,MainActivity::class.java))
                    }else{
                        Toast.makeText(this@DetailsActivity,resources.getString(R.string.error), Toast.LENGTH_SHORT).show()
                    }
            })
            .setNegativeButton(resources.getString(R.string.cancelar),DialogInterface.OnClickListener{
                dialog,wich->
                dialog.dismiss()
            })
            .show()
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

    override fun onNothingSelected(p0: AdapterView<*>?) {
        return
    }
}