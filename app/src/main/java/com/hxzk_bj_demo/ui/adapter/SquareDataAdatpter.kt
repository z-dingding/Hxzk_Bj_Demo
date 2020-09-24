package com.hxzk_bj_demo.ui.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hxzk_bj_demo.R
import com.hxzk_bj_demo.javabean.SquareDataX
import java.text.SimpleDateFormat
import java.util.*

/**
 *作者：created by zjt on 2020/9/15
 *描述:
 *
 */
class SquareDataAdatpter : RecyclerView.Adapter<SquareDataAdatpter.SquareViewHolder>() {


    var dataList : List<SquareDataX>? = null

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SquareViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.adapter_squaredata_item,parent,false)
       return SquareViewHolder(view)
    }

    override fun getItemCount(): Int {
        if(dataList != null){
            return dataList?.size!!
        }
       return 0
    }

    @SuppressLint("SetTextI18n", "SimpleDateFormat")
    override fun onBindViewHolder(holder: SquareViewHolder, position: Int) {
        with(holder){
            tvTitle.text=dataList?.get(position)?.title
            tvName.text="分享人:${dataList?.get(position)?.shareUser}"
            val  sdf =  SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
           val time ="发布时间:${sdf.format(dataList?.get(position)?.publishTime?.let { Date(it) })}"
            tvTime.text=time
        }
    }

    fun addDatas(it: List<SquareDataX>?) {
        dataList =it
        notifyDataSetChanged()
    }


    class SquareViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView){
     val tvTitle =itemView.findViewById<TextView>(R.id.tvTitle)
     val tvName =itemView.findViewById<TextView>(R.id.tvName)
     val tvTime =itemView.findViewById<TextView>(R.id.tvTime)
    }


}