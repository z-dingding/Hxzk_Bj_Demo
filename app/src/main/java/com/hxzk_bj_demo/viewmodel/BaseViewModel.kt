package com.hxzk_bj_demo.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 *作者：created by zjt on 2020/9/16
 *描述:封装优化ViewModel
 *
 */
open class BaseViewModel : ViewModel() {

    fun launch(block : suspend CoroutineScope.() -> Unit ,
               error: (Throwable) -> Unit = {},
               complete: () -> Unit = {}){
        viewModelScope.launch {
            try {
                block.invoke(this)
            } catch (e: Throwable) {
                error(e)
            } finally {
                complete()
            }
        }
    }
}