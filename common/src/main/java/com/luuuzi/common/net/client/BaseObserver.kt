package com.luuuzi.common.net.client

import android.content.Context
import android.util.Log
import com.luuuzi.common.net.callback.IError
import com.luuuzi.common.net.callback.IFailure
import com.luuuzi.common.net.callback.IRequest
import com.luuuzi.common.net.callback.ISuccess
import com.luuuzi.common.net.error.ApiException
import io.reactivex.Observer
import io.reactivex.disposables.Disposable

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/3 0003 15:12
 *    desc   :
 */
class BaseObserver<T>(
    var context: Context?,
    var iFailure: IFailure?,
    var iSuccess: ISuccess<T>,
    var iRequest: IRequest?,
    var iStatusView: IStatusView?,
    var iError: IError?
) : Observer<T> {
    private val tag:String =javaClass.simpleName
    override fun onComplete() {

    }

    override fun onSubscribe(d: Disposable) {

    }

    override fun onNext(value: T) {
        Log.i(tag, "成功:$value")
//        stopLoading();
        if (this.iStatusView != null) {
            this.iStatusView?.openSuccessView()//隐藏加载view
        }
        if (this.iSuccess != null) {
            this.iSuccess?.success(value)
        }

    }

    override fun onError(e: Throwable) {
        Log.i(tag,"错误:"+e.message)
        if (context != null)
            return
        if (iError != null) {
            if (e is ApiException) {
                iError?.onError(e.error_code, "自定义错误")
            } else {
                //其他错误
            }
        }
        /**
         * 通过反射创建异常处理类
         */
        if (iFailure != null) {
            iFailure?.onFailure()
        }
        if (iRequest != null) {
            iRequest?.onRequestEnd()
        }

    }
}