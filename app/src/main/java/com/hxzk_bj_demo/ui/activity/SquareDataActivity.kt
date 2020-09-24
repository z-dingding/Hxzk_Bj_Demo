package com.hxzk_bj_demo.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hxzk_bj_demo.R
import com.hxzk_bj_demo.common.MainApplication
import com.hxzk_bj_demo.javabean.LoadState
import com.hxzk_bj_demo.javabean.SquareDataBean
import com.hxzk_bj_demo.ui.activity.base.BaseKtActivity
import com.hxzk_bj_demo.ui.adapter.SquareDataAdatpter
import com.hxzk_bj_demo.utils.activity.ActivityJump
import com.hxzk_bj_demo.viewmodel.SquareViewModel
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import kotlinx.android.synthetic.main.activity_rank.*

class SquareDataActivity : BaseKtActivity() {

    lateinit var mRecycler : RecyclerView
    val squareAdapter =  SquareDataAdatpter()
    lateinit var mSmartRefersh :SmartRefreshLayout
    lateinit var tvBack :TextView
    lateinit var tvTitle :TextView


    var viewModel :SquareViewModel? = null

    override fun setLayoutId(): Int {
        return R.layout.activity_squaredata
    }

    override fun initView() {
        mRecycler = findViewById(R.id.recycler)
        mSmartRefersh = findViewById(R.id.smartRefresh)
        tvBack  = findViewById(R.id.tv_titlebar_back)
        tvTitle  = findViewById(R.id.tv_titlebar_title)
    }

    override fun initEvent() {
        tvBack.setOnClickListener {
            ActivityJump.Back(this)
        }
        mSmartRefersh.let {
            it.setEnableRefresh(false)
            it.setEnableLoadMore(false)
        }
    }

    override fun initData() {
        recycler.let {

            it.adapter =squareAdapter
            it.layoutManager =LinearLayoutManager(this)
            it.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        }
        tvTitle.text=getString(R.string.squaredata_title)
        viewModel = ViewModelProvider.AndroidViewModelFactory(application).create(SquareViewModel :: class.java)
       //网络请求结果
        viewModel!!.squareDats.observe(this, Observer {
            squareAdapter.addDatas(it)
        })
        //监听网络请求结状态
        viewModel!!.loadState.observe(this, Observer {
           when(it){
               is LoadState.OnLoading ->println(it.loadMsg)
               is LoadState.OnSuccess -> println(it.successMsg)
               is LoadState.OnFail -> println(it.failMsg)
               is LoadState.OnFinish -> println(it.finishMsg)
           }
        })
        //执行请求
        viewModel!!.requestDtas(0)
    }

    override fun notifyByThemeChanged() {
        TODO("Not yet implemented")
    }
}