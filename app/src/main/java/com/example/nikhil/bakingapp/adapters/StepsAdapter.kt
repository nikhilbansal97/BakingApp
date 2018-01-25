package com.example.nikhil.bakingapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.pojos.Step

import java.util.ArrayList

/**
 * Created by NIKHIL on 23-12-2017.
 */

class StepsAdapter(private val context: Context, stepList: ArrayList<Step>, private val listener: OnStepsClicked) : RecyclerView.Adapter<StepsAdapter.StepHolder>() {
    private var stepList = ArrayList<Step>()

    init {
        this.stepList = stepList
    }

    interface OnStepsClicked {
        fun onStepClicked(step: Step)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): StepHolder {
        return StepHolder(LayoutInflater.from(context).inflate(R.layout.list_item_step, parent, false))
    }

    override fun onBindViewHolder(holder: StepHolder, position: Int) {
        val step = stepList[position]
        holder.textViewStepName.text = step.shortDescription
        holder.textViewStepName.setOnClickListener { listener.onStepClicked(step) }
    }

    override fun getItemCount(): Int {
        return stepList?.size
    }

    inner class StepHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textViewStepName: TextView = itemView.findViewById(R.id.stepName)

    }
}
