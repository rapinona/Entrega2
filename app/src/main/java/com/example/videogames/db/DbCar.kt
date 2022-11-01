package com.example.videogames.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import com.example.videogames.model.Car
import java.lang.Exception

class DbCar(private val context:Context):DbHelper(context) {

    public fun insertCar(marca: String, modelo:String ,ano:Number,color:String):Long{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var id :Long =0

        try {
            val values = ContentValues()
            values.put("marca",marca)
            values.put("modelo",modelo)
            values.put("ano",ano.toString())
            values.put("color",color)

            id = db.insert(TABLE_CAR,null,values)
        }catch (e:Exception){
            //Manejo de Exepcion
        }finally {
            db.close()
        }

        return id
    }

    public fun getCars(): ArrayList<Car> {
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var listCars = ArrayList<Car>()

        var carTmp : Car? = null
        var cursorCar:Cursor? = null

        cursorCar = db.rawQuery("SELECT * FROM $TABLE_CAR",null)

        if(cursorCar.moveToFirst()){
            do{
                carTmp = Car(cursorCar.getInt(0),cursorCar.getString(1),cursorCar.getString(2),cursorCar.getString(3),cursorCar.getString(4))
                listCars.add(carTmp)
            }while (cursorCar.moveToNext())
        }

        cursorCar.close()

        return listCars
    }

    fun getCar(id:Int):Car?{
        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        var car : Car? = null

        var cursorCar:Cursor? = null

        cursorCar = db.rawQuery("SELECT * FROM $TABLE_CAR WHERE id = $id LIMIT 1",null)

        if(cursorCar.moveToFirst()){
            car = Car(cursorCar.getInt(0),cursorCar.getString(1),cursorCar.getString(2),cursorCar.getString(3),cursorCar.getString(4))
        }

        cursorCar.close()

        return car
    }

    fun updateCar(id:Int,marca:String,modelo: String,ano:Number,color: String):Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("UPDATE car SET marca = '$marca',modelo='$modelo',ano='${ano.toString()}',color='$color' WHERE id=$id")
            banderaCorrecto = true
        }catch (e:Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

    fun deleteGame(id:Int):Boolean{
        var banderaCorrecto = false

        val dbHelper = DbHelper(context)
        val db = dbHelper.writableDatabase

        try{
            db.execSQL("DELETE FROM car WHERE id=$id")
            banderaCorrecto = true
        }catch (e:Exception){

        }finally {
            db.close()
        }

        return banderaCorrecto
    }

}