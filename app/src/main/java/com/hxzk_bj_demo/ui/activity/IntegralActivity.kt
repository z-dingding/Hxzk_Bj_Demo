package com.hxzk_bj_demo.ui.activity

import android.view.Gravity
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.hxzk_bj_demo.R
import com.hxzk_bj_demo.javabean.DataX
import com.hxzk_bj_demo.javabean.IntegralListBean
import com.hxzk_bj_demo.mvp.constract.IntegralConstract
import com.hxzk_bj_demo.mvp.presenter.IntegralPresener
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.HttpRequest
import com.hxzk_bj_demo.ui.activity.base.BaseBussActivity.isShowMenu
import com.hxzk_bj_demo.ui.activity.base.BaseKtMvpActivity
import com.hxzk_bj_demo.ui.activity.base.BaseMvpActivity
import com.hxzk_bj_demo.ui.adapter.IntegralAdapter
import com.hxzk_bj_demo.utils.ProgressDialogUtil
import com.hxzk_bj_demo.utils.activity.ActivityJump
import com.hxzk_bj_demo.utils.toastutil.ToastCustomUtil
import com.scwang.smartrefresh.layout.SmartRefreshLayout
import com.scwang.smartrefresh.layout.listener.OnLoadMoreListener
import com.scwang.smartrefresh.layout.listener.OnRefreshListener
import rx.Observable
import java.util.*

/**
 * 积分记录Activity
 */
class IntegralActivity : BaseKtMvpActivity<IntegralPresener>(), IntegralConstract.IntegralView {

    private lateinit var mSmartRefreshLayout: SmartRefreshLayout
    private lateinit var mRecyvler: RecyclerView
    lateinit var mIntegralAdapter: IntegralAdapter
    lateinit var tvTitle: TextView
    lateinit var tvBack: TextView
    lateinit var tvOrder: TextView


    lateinit var mObserable: Observable<BaseResponse<IntegralListBean>>

    /**
     * 当前页码,默认从1开始
     */
    var currentPageNum = 1

    /**
     * 总共页码
     */
    var allPageNum = 0

    /**
     * 每页条目
     */
    var pageSize = 20

    /**
     * 是否是刷新请求
     */
    var isRefersh = false

    /**
     * 是否是刷新请求
     */
    var isLoadMore = false

    /**
     * 数据源
     */
    var mDataList: MutableList<DataX> = mutableListOf()


    override fun setLayoutId(): Int {
        return R.layout.activity_integral
    }

    override fun initView() {
        mSmartRefreshLayout = findViewById(R.id.srl_integral)
        mRecyvler = findViewById(R.id.rv_integral)
        tvTitle = findViewById(R.id.tv_titlebar_title)
        tvBack = findViewById(R.id.tv_titlebar_back)
        tvOrder = findViewById(R.id.tv_titlebar_other)
        presenter = IntegralPresener()
        presenter!!.onAttachView(this)
    }

    override fun initEvent() {
        tvBack.setOnClickListener {
            ActivityJump.Back(this)
        }
        tvOrder.setOnClickListener {
            ActivityJump.NormalJump(this, RankActivity::class.java)
        }
        mSmartRefreshLayout.setOnRefreshListener {
            mSmartRefreshLayout.resetNoMoreData()
            currentPageNum = 1
            isRefersh = true
            mDataList.clear()
            mObserable = presenter!!.intergralListP(currentPageNum, this)
        }
        mSmartRefreshLayout.setOnLoadMoreListener {
            isLoadMore = true
            if (currentPageNum < allPageNum) {
                currentPageNum++
                mObserable = presenter!!.intergralListP(currentPageNum, this)
            } else {
                //完成加载并标记没有更多数据
                mSmartRefreshLayout.finishLoadMoreWithNoMoreData()
            }
        }

    }


    override fun initData() {
        //设置标题
        tvTitle.setText(resources.getString(R.string.integral_title))
        tvOrder.text = resources.getString(R.string.integral_order)
        val rvManager: LinearLayoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        mRecyvler.layoutManager = rvManager
        mRecyvler.addItemDecoration(DividerItemDecoration(this, DividerItemDecoration.VERTICAL))
        mIntegralAdapter = IntegralAdapter()
        mRecyvler.adapter = mIntegralAdapter
        //请求积分列表
        mObserable = presenter!!.intergralListP(currentPageNum, this)
    }

    override fun onDestroy() {
        super.onDestroy()
        HttpRequest.getInstance().unsubscribe(mObserable)
    }

    override fun notifyByThemeChanged() {

    }

    override fun onShowLoading() {
        if (!isRefersh && !isLoadMore) {
            ProgressDialogUtil.getInstance().mshowDialog(this)
        }

    }

    override fun onHiddenLoading() {
        ProgressDialogUtil.getInstance().mdismissDialog()
    }

    override fun onFail(throwable: Throwable) {
        ToastCustomUtil.showLongToast(throwable.message)
    }

    override fun onIngegralResult(loginBean: BaseResponse<IntegralListBean>) {
        if (!loginBean.isOk) {
            ToastCustomUtil.showLongToast(loginBean.msg)
        } else {
            allPageNum = if (loginBean.data.total % pageSize == 0) loginBean.data.total / pageSize else loginBean.data.total / pageSize + 1
            mDataList.addAll(loginBean.data.datas)
            mIntegralAdapter.notifyChangeData(mDataList)

            if (isRefersh) {
                isRefersh = false
                mSmartRefreshLayout.finishRefresh()
            } else if (isLoadMore) {
                isLoadMore = false
                mSmartRefreshLayout.finishLoadMore()
            }

        }
    }
}



