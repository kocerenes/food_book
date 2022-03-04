package com.example.besinler_kitabi.view

import android.opengl.Visibility
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.besinler_kitabi.adapter.FoodRecyclerAdapter
import com.example.besinler_kitabi.databinding.FragmentFoodListBinding
import com.example.besinler_kitabi.viewmodel.FoodListViewModel

class FoodListFragment : Fragment() {

    private var _binding : FragmentFoodListBinding? = null
    private val binding get() = _binding!!

    private lateinit var viewModel : FoodListViewModel
    private val recyclerFoodAdapter = FoodRecyclerAdapter(arrayListOf())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        _binding = FragmentFoodListBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        viewModel = ViewModelProviders.of(this).get(FoodListViewModel::class.java)
        viewModel.refreshData()

        binding.foodListRecyclerView.layoutManager = LinearLayoutManager(context)
        binding.foodListRecyclerView.adapter = recyclerFoodAdapter

        binding.swipeRefreshLayout.setOnRefreshListener {
            binding.loadProgressBar.visibility = View.VISIBLE
            binding.errorTextView.visibility = View.GONE
            binding.foodListRecyclerView.visibility = View.GONE
            viewModel.refreshFromInternet()
            binding.swipeRefreshLayout.isRefreshing = false
        }

        observeLiveData()

    }

    private fun observeLiveData(){

        viewModel.foods.observe(viewLifecycleOwner, Observer {
            it?.let {
                binding.foodListRecyclerView.visibility = View.VISIBLE
                recyclerFoodAdapter.foodListUpdate(it)
            }
        })

        //Hata mesajı var mı yok mu bakıyoruz varsa gösteriyoruz.
        viewModel.errorMessage.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.errorTextView.visibility = View.VISIBLE
                    binding.foodListRecyclerView.visibility = View.GONE
                }else{
                    binding.errorTextView.visibility = View.GONE
                }
            }
        })

        //yüklenip yüklenmeme durumuna göre progressbarı kontol ediyoruz
        viewModel.isFoodLoading.observe(viewLifecycleOwner, Observer {
            it?.let {
                if (it){
                    binding.foodListRecyclerView.visibility = View.GONE
                    binding.errorTextView.visibility = View.GONE
                    binding.loadProgressBar.visibility = View.VISIBLE
                }else{
                    binding.loadProgressBar.visibility = View.GONE
                }
            }
        })

    }


}