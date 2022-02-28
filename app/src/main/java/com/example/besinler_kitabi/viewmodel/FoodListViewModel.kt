package com.example.besinler_kitabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinler_kitabi.model.Food

class FoodListViewModel : ViewModel() {
    val foods  = MutableLiveData<List<Food>>()
    val errorMessage = MutableLiveData<Boolean>()
    val isFoodLoading = MutableLiveData<Boolean>()

    fun refreshData(){

        val muz = Food("Muz","100","10","5","1","www.test.com")
        val cilek = Food("çilek","200","10","5","1","www.test.com")
        val elma = Food("elma","300","20","5","1","www.test.com")

        val foodList = arrayListOf<Food>(muz,cilek,elma)

        foods.value = foodList
        errorMessage.value = false
        isFoodLoading.value = false
    }

}