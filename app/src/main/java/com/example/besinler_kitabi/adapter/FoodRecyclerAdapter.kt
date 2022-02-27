package com.example.besinler_kitabi.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.besinler_kitabi.R
import com.example.besinler_kitabi.databinding.FoodRecyclerRowBinding
import com.example.besinler_kitabi.databinding.FragmentFoodDetailBinding
import com.example.besinler_kitabi.databinding.FragmentFoodListBinding
import com.example.besinler_kitabi.model.Food

class FoodRecyclerAdapter(val foodList : ArrayList<Food>) : RecyclerView.Adapter<FoodRecyclerAdapter.FoodViewHolder>() {

    class FoodViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView){
        val binding = FoodRecyclerRowBinding.bind(itemView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): FoodViewHolder {

        val inflater = LayoutInflater.from(parent.context)
        val view = inflater.inflate(R.layout.food_recycler_row,parent,false)
        return FoodViewHolder(view)
    }

    override fun onBindViewHolder(holder: FoodViewHolder, position: Int) {
        holder.binding.foodNameTextView.text = foodList.get(position).name
        holder.binding.foodCaloriTextView.text = foodList.get(position).calori
        //image eklenecek
    }

    override fun getItemCount(): Int {
        return foodList.size
    }

    fun foodListUpdate(newFoodList : List<Food>){
        foodList.clear()
        foodList.addAll(newFoodList)
        notifyDataSetChanged()
    }

}