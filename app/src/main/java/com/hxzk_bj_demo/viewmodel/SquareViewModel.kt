package com.hxzk_bj_demo.viewmodel


import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hxzk_bj_demo.javabean.LoadState
import com.hxzk_bj_demo.javabean.SquareDataBean
import com.hxzk_bj_demo.javabean.SquareDataX
import com.hxzk_bj_demo.network.BaseResponse
import com.hxzk_bj_demo.network.HttpRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

/**
 *作者：created by zjt on 2020/9/15
 *描述:
 *
 */
class SquareViewModel : BaseViewModel() {

    /**
     * 列表数据的LiveData
     */
 private  val _squareDatas = MutableLiveData<List<SquareDataX>>()
 val squareDats : LiveData<List<SquareDataX>> = _squareDatas

    /**
     * 请求的状态
     */
    private  val _loadState = MutableLiveData<LoadState>()
    val loadState : LiveData<LoadState> = _loadState

    fun  requestDtas( pageNum:Int){
        var result : BaseResponse<SquareDataBean>? = null
        launch({
            _loadState.value = LoadState.OnLoading("请求中...")
            withContext(Dispatchers.IO){
                result  =HttpRequest.getInstance().apiService.squareDatasApi(pageNum)
            }
            _squareDatas.value =result?.data?.datas
            _loadState.value = LoadState.OnSuccess("请求成功...")
        },{
            _loadState.value =LoadState.OnFail(it.message?:"请求失败...")
        },{
            _loadState.value =LoadState.OnFinish("请求完成...")
        })
    }

}