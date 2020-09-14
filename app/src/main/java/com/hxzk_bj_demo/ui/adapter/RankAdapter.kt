package com.hxzk_bj_demo.ui.adapter

import android.annotation.SuppressLint
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.hxzk_bj_demo.R
import com.hxzk_bj_demo.javabean.Data
import java.util.zip.Inflater

/**
 *作者：created by zjt on 2020/9/14
 *描述:
 *
 */
class RankAdapter : RecyclerView.Adapter<RankAdapter.ViewHolder>() {


    val datas  = mutableListOf<Data>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =LayoutInflater.from(parent.context).inflate(R.layout.adapter_rank_item,parent,false)
        return ViewHolder(view)
    }

    override fun getItemCount(): Int {
     if (datas.size > 0){
         return datas.size
     }
        return 0
    }


    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
      val data =datas.get(position)
        with(holder){
            tvRankNum.text = data.rank
            tvName.text=data.username
            tvIntegral.text = "+"+data.level
            when(data.rank){
                "1" -> ivRankNum.setBackgroundResource(R.drawable.one)
                 "2" -> ivRankNum.setBackgroundResource(R.drawable.two)
                "3" -> ivRankNum.setBackgroundResource(R.drawable.three)
                else ->ivRankNum.setBackgroundResource(0)
            }
        }
    }


    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
      val tvRankNum : TextView= itemView.findViewById(R.id.tv_ranknum)
      val tvName = itemView.findViewById<TextView>(R.id.tv_name)
      val tvIntegral = itemView.findViewById<TextView>(R.id.tv_integral)
      val ivRankNum = itemView.findViewById<ImageView>(R.id.iv_ranknum)

    }


    fun addData( list :MutableList<Data>){
        datas.clear()
        datas.addAll(list)
        this.notifyDataSetChanged()
    }
}