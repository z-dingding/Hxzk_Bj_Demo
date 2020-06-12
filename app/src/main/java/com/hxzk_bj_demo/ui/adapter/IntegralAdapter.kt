package com.hxzk_bj_demo.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hxzk_bj_demo.R
import com.hxzk_bj_demo.javabean.DataX
import com.hxzk_bj_demo.utils.CalendarUtil
import java.util.*

/**
 *作者：created by hxzk on 2020/6/10
 *描述:
 *
 */
class IntegralAdapter : RecyclerView.Adapter<IntegralAdapter.IntegralView>() {

  var mDataList : List<DataX> = LinkedList()


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): IntegralView {
        val holderView: View = LayoutInflater.from(parent.context).inflate(R.layout.adapter_integral_item, parent, false)
        return  IntegralView(holderView)
    }

    override fun getItemCount(): Int {
        return mDataList.size
    }

    override fun onBindViewHolder(holder: IntegralView, position: Int) {
        holder.tvCount.text ="+"+mDataList[position].coinCount
        holder.tvDesc.text= CalendarUtil.timeStamp2Date(mDataList[position].date.toString(),"yyyy-MM-dd")
       holder.tvReason.text= mDataList[position].reason
    }



    class IntegralView(itemView: View) : RecyclerView.ViewHolder(itemView){
        val  tvDesc :TextView =itemView.findViewById(R.id.item_integral_desc)
      val tvReason :TextView=itemView.findViewById(R.id.item_integral_reason)
        val  tvCount :TextView=itemView.findViewById(R.id.item_integral_coinCount)
    }

    fun notifyChangeData(dataList: List<DataX>){
        mDataList= dataList
        notifyDataSetChanged()
    }
}