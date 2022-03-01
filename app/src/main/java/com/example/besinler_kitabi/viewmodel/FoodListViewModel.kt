package com.example.besinler_kitabi.viewmodel

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.besinler_kitabi.model.Food
import com.example.besinler_kitabi.service.FoodService
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.android.schedulers.AndroidSchedulers.mainThread

import io.reactivex.disposables.CompositeDisposable
import io.reactivex.observers.DisposableSingleObserver
import io.reactivex.schedulers.Schedulers

class FoodListViewModel : ViewModel() {
    val foods  = MutableLiveData<List<Food>>()
    val errorMessage = MutableLiveData<Boolean>()
    val isFoodLoading = MutableLiveData<Boolean>()

    private val foodService = FoodService()
    private val disposable = CompositeDisposable()

    fun refreshData(){

        /*val muz = Food("Muz","100","10","5","1","www.test.com")
        val cilek = Food("çilek","200","10","5","1","www.test.com")
        val elma = Food("elma","300","20","5","1","www.test.com")

        val foodList = arrayListOf<Food>(muz,cilek,elma)

        foods.value = foodList
        errorMessage.value = false
        isFoodLoading.value = false*/
        getDataFromInternet()
    }

    fun getDataFromInternet(){

        isFoodLoading.value = true

        disposable.add(
            foodService.getFood()
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(object : DisposableSingleObserver<List<Food>>(){
                    override fun onSuccess(t: List<Food>) {
                        //başarılı
                        foods.value = t
                        errorMessage.value = false
                        isFoodLoading.value = false
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

}