package com.example.besinler_kitabi.viewmodel

import android.app.Application
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import com.example.besinler_kitabi.model.Food
import com.example.besinler_kitabi.service.FoodDatabase
import com.example.besinler_kitabi.service.FoodService
import com.example.besinler_kitabi.util.PrivateSharedPreferences
import io.reactivex.android.schedulers.AndroidSchedulers

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers
import kotlinx.coroutines.launch

class FoodListViewModel(application: Application) : BaseViewModel(application) {
    val foods  = MutableLiveData<List<Food>>()
    val errorMessage = MutableLiveData<Boolean>()
    val isFoodLoading = MutableLiveData<Boolean>()
    private var updateTime = 10*60*1000*1000*1000L

    private val foodService = FoodService()
    private val disposable = CompositeDisposable()

    private val privateSharedPreferences = PrivateSharedPreferences(getApplication())


    fun refreshData(){
        val savedTime = privateSharedPreferences.getTime()

        if (savedTime != null && savedTime != 0L && System.nanoTime()-savedTime < updateTime){
            getDataFromSQLite()
        }else{
            getDataFromInternet()
        }
    }

    fun refreshFromInternet(){
        getDataFromInternet()
    }

    private fun getDataFromSQLite(){
        isFoodLoading.value = true
        launch {
            val foodsList = FoodDatabase(getApplication()).foodDao().getAllFood()
            showFoods(foodsList)
            Toast.makeText(getApplication(),"Besinleri Room'dan aldık",Toast.LENGTH_LONG).show()
        }
    }

    private fun getDataFromInternet(){

        isFoodLoading.value = true

        disposable.add(
            foodService.getFood()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        //başarılı
                        keepSqlite(t)
                        Toast.makeText(getApplication(),"Besinleri internetten aldık",Toast.LENGTH_LONG).show()
                    }

                    override fun onError(e: Throwable) {
                        //hata
                        errorMessage.value = true
                        isFoodLoading.value = false
                        e.printStackTrace()
                    }

                })
        )
    }

    private fun showFoods(foodsList : List<Food>){
        foods.value = foodsList
        errorMessage.value = false
        isFoodLoading.value = false
    }

    private fun keepSqlite(foodsList : List<Food>){
        launch {
            val dao = FoodDatabase(getApplication()).foodDao()
            dao.deleteAllFoods()
            val uuidList = dao.insertAll(*foodsList.toTypedArray())
            var i = 0
            while (i < foodsList.size){
                foodsList[i].uuid = uuidList[i].toInt()
                i=i+1
            }
            showFoods(foodsList)
        }
        privateSharedPreferences.saveTime(System.nanoTime())
    }

}