package com.example.nikhil.bakingapp.adapters

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.example.nikhil.bakingapp.R
import com.example.nikhil.bakingapp.Step

/**
 * Created by NIKHIL on 15-12-2017.
 */
class StepsAdapter(context: Context, listSteps: ArrayList<Step>, listener: OnStepsClicked): RecyclerView.Adapter<StepsAdapter.StepHolder>() {

    interface OnStepsClicked {
        fun onStepClicked(step: Step)
    }

    var mContext = context
    var stepList: ArrayList<Step> = listSteps
    var mListener: OnStepsClicked = listener

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): StepHolder {
        return StepHolder(LayoutInflater.from(mContext).inflate(R.layout.list_item_step, parent, false))
    }

    override fun onBindViewHolder(holder: StepHolder?, position: Int) {
        val step = stepList[position]
        holder!!.textView_stepName!!.text = step.shortDescription
        holder!!.textView_stepName!!.setOnClickListener({ mListener.onStepClicked(step) })
    }

    override fun getItemCount(): Int {
        return stepList.size
    }

    class StepHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
        var textView_stepName: TextView? = null
        init {
            textView_stepName = itemView.findViewById(R.id.stepName)
        }
    }

}