package com.example.besinler_kitabi.view

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.example.besinler_kitabi.databinding.FragmentFoodDetailBinding
import com.example.besinler_kitabi.viewmodel.FoodDetailViewModel

class FoodDetailFragment : Fragment() {

    private var foodid = 0

    private var _binding : FragmentFoodDetailBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : FoodDetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodDetailBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodDetailViewModel::class.java)
        viewModel.roomGetData()

        arguments?.let {
            foodid = FoodDetailFragmentArgs.fromBundle(it).foodId
            println(foodid)
        }

        observeLiveData()

    }

    fun observeLiveData(){

        viewModel.foodLiveData.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.foodNameWithDetailTextView.text = it.name
                binding.foodCaloriWithDetailTextView.text = it.calori
                binding.foodCarbohydrateWithDetailTextView.text = it.carbonhydrate
                binding.foodProteinWithDetailTextView.text = it.protein
                binding.foodOilWithDetailTextView.text = it.oil
            }
        })

    }

}