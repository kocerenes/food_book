package com.example.besinler_kitabi.service

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.example.besinler_kitabi.model.Food

//Data access object

@Dao
interface FoodDAO {

    @Insert
    suspend fun insertAll(vararg food : Food) : List<Long>

    @Query("SELECT * FROM food")
    suspend fun getAllFood() : List<Food>

    @Query("SELECT * FROM food WHERE uuid = :foodId")
    suspend fun getFood(foodId : Int): Food

    @Query("DELETE FROM food")
    suspend fun deleteAllFoods()

    @Query("DELETE FROM Food WHERE uuid = :foodId")
    suspend fun deleteThisFood(foodId: Int)

    //Insert -> Room, insert into
    //suspend -> coroutine scope
    //vararg -> birden fazla ve istediğimiz sayıda besin
    // List<Long> -> long, id'ler

}