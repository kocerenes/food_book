package com.example.besinler_kitabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinler_kitabi.model.Food

class FoodDetailViewModel : ViewModel() {

    val foodLiveData = MutableLiveData<Food>()

    fun roomGetData(){
        val muz = Food("Muz","100","10","5","1","www.test.com")
        foodLiveData.value = muz
    }

}