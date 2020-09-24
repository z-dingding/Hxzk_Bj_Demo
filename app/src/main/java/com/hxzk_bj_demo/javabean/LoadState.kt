package com.hxzk_bj_demo.javabean

/**
 *作者：created by zjt on 2020/9/15
 *描述:
 *
 */
sealed class LoadState(msg:String) {
    //sealed类是一种特殊的父类，它只允许内部继承，所以在与when表达式合用来判断状态时很适合
    //Fail状态必须指定错误信息，其他的状态信息可为空
    class OnLoading(val loadMsg: String ="") :LoadState(loadMsg)
    class OnSuccess(val successMsg: String ="") :LoadState(successMsg)
    class OnFail(val failMsg: String ="") :LoadState(failMsg)
    class OnFinish(val finishMsg: String ="") :LoadState(finishMsg)
}