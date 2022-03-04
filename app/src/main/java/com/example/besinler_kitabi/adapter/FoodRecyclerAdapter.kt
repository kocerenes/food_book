package com.example.besinler_kitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.navigation.Navigation
import androidx.recyclerview.widget.RecyclerView
import com.example.besinler_kitabi.R
import com.example.besinler_kitabi.databinding.FoodRecyclerRowBinding
import com.example.besinler_kitabi.databinding.FragmentFoodDetailBinding
import com.example.besinler_kitabi.databinding.FragmentFoodListBinding
import com.example.besinler_kitabi.model.Food
import com.example.besinler_kitabi.util.downloadImage
import com.example.besinler_kitabi.util.makePlaceholder
import com.example.besinler_kitabi.view.FoodListFragmentDirections
import kotlinx.android.synthetic.main.food_recycler_row.view.*

class FoodRecyclerAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>(),FoodClickListener {

    class FoodViewHolder(var view : FoodRecyclerRowBinding) : RecyclerView.ViewHolder(view.root){
        //val binding = FoodRecyclerRowBinding.bind(view.root)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        //val view = inflater.inflate(R.layout.food_recycler_row,parent,false)
        val view = DataBindingUtil.inflate<FoodRecyclerRowBinding>(inflater,R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {

        holder.view.food = foodList[position]
        holder.view.listener = this

        /*
        holder.binding.foodNameTextView.text = foodList.get(position).name
        holder.binding.foodCaloriTextView.text = foodList.get(position).calori
        //image eklenecek
        holder.binding.imageView.downloadImage(foodList.get(position).image, makePlaceholder(holder.itemView.context))

        holder.itemView.setOnClickListener{
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(foodList.get(position).uuid)
            Navigation.findNavController(it).navigate(action)
        }
        */

    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun foodListUpdate(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

    override fun clickedFood(view: View) {
        val uuid = view.food_uuid.text.toString().toIntOrNull()
        uuid?.let {
            val action = FoodListFragmentDirections.actionFoodListFragmentToFoodDetailFragment(it)
            Navigation.findNavController(view).navigate(action)
        }
    }

}