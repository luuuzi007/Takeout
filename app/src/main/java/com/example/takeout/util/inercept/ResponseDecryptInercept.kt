package com.example.takeout.util.inercept

import android.os.Build
import android.os.Handler
import android.util.Log
import android.webkit.WebSettings
import com.example.takeout.BuildConfig
import com.google.gson.Gson
import com.luuuzi.common.app.AccountManager
import com.luuuzi.common.app.App
import com.luuuzi.common.net.HttpResultCode
import com.luuuzi.common.net.bean.BaseBean
import com.luuuzi.common.net.error.ApiException
import com.luuuzi.common.net.interceptor.BaseInterceptor
import com.luuuzi.common.util.log.MLog
import okhttp3.*
import okio.Buffer
import java.io.IOException
import java.nio.charset.StandardCharsets

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/7 0007 16:20
 *    desc   :
 */
class ResponseDecryptInercept : BaseInterceptor() {
    private val TAG = "ResponseDecryptInercept"
    private val UTF8 = StandardCharsets.UTF_8
    override fun intercept(chain: Interceptor.Chain): Response {

        val req =
            chain.request().newBuilder()
//                .removeHeader("User-Agent")
//                .addHeader("User-Agent", getUserAgent())
//                .addHeader("token", if (AccountManager.userToken == null) "" else AccountManager.userToken)
                .addHeader("loginType", "LOGIN_APP_PASSWORD")
                .build()
        val handler = Handler()
        //打印请求内容
        if (BuildConfig.DEBUG) {
            Log.e(TAG, "url:" + chain.request().url().toString())
            Log.e(TAG, "token:" + AccountManager.userToken)
        }
        val requestBody = req.body() //请求内容

        if (requestBody != null && BuildConfig.DEBUG) { //get请求时为空
            Log.e(TAG, "requsetbody:$requestBody")
            val connection = chain.connection()
            val protocol =
                if (connection != null) connection.protocol() else Protocol.HTTP_1_1
            val requestStartMessage = "--> " + req.method() + ' ' + req.url() + ' ' + protocol
            Log.e(TAG, "url:$requestStartMessage")
            Log.e(TAG, "Content-Type: " + requestBody.contentType())
            Log.e(TAG, "Content-Length: " + requestBody.contentLength())
            Log.e(TAG, "requestBody:" + requestBodyToString(requestBody))
        }

        var rep = chain.proceed(req)

        val responseBody = rep.body()
        val source = responseBody!!.source()
        source.request(Long.MAX_VALUE) // Buffer the entire body.

        val buffer = source.buffer()

        //处理返回结果
        //处理返回结果
        val json = buffer.clone().readString(UTF8)
        Log.e(TAG, "json--->>:$json")
        val data: BaseBean = Gson().fromJson<BaseBean>(json, BaseBean::class.java)
        // 如果不是正确返回码且不是第三方绑定的验证码
        // 如果不是正确返回码且不是第三方绑定的验证码
        if (data.code !== HttpResultCode.REQUEST_SUCCESS) { //进来这里就直接抛异常 之后就不会在走下面的
//            if (data.getCode() === HttpResultCode.TOKEN_PAST_DUE) {
//                ToastUtil.showMessage("请重新登录")
//                EventBus.getDefault().post(EventBusBean(EventBusBean.FLAG_7))
//            } else {
//                ToastUtil.showMessage(data.getMessage())
//            }
            throw ApiException(data.code, data.message)
        }
        if (BuildConfig.DEBUG) {
            MLog.e(rep.request().url().toString() + "&is_not_encrypt=1&is_api_test=1")
            MLog.json(MLog.E, json)
        }
        rep = rep.newBuilder()
            .body(ResponseBody.create(responseBody.contentType(), json))
            .build()

        return rep
    }

    //输出body
    @Throws(IOException::class)
    fun requestBodyToString(requestBody: RequestBody): String? {
        val buffer = Buffer()
        requestBody.writeTo(buffer)
        return buffer.readUtf8()
    }

    /**
     * 代理设置
     */
    private fun getUserAgent(): String? {
        var userAgent = ""
        userAgent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN_MR1) {
            try {
                WebSettings.getDefaultUserAgent(App.getApplicationContext())
            } catch (e: Exception) {
                System.getProperty("http.agent")
            }
        } else {
            System.getProperty("http.agent")
        }
        val sb = StringBuffer()
        var i = 0
        val length = userAgent.length
        while (i < length) {
            val c = userAgent[i]
            if (c <= '\u001f' || c >= '\u007f') {
                sb.append(String.format("\\u%04x", c.toInt()))
            } else {
                sb.append(c)
            }
            i++
        }
        return sb.toString()
    }
}