package com.example.takeout.ui.activity

import android.app.Activity
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.takeout.R
import com.example.takeout.ui.fragment.HomeFragment
import com.example.takeout.ui.fragment.MoreFragment
import com.example.takeout.ui.fragment.OrderFragment
import com.example.takeout.ui.fragment.UserFragment
import com.example.takeout.util.DimensUtils
import com.luuuzi.common.net.bean.BaseBean
import com.luuuzi.common.net.callback.IFailure
import com.luuuzi.common.net.callback.IRequest
import com.luuuzi.common.net.callback.ISuccess
import com.luuuzi.common.net.client.HttpClient
import com.trello.rxlifecycle2.android.ActivityEvent
import com.trello.rxlifecycle2.components.support.RxAppCompatActivity
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : RxAppCompatActivity() {
    private var tag: String = javaClass.simpleName
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val ll_bootom_bar = findViewById<LinearLayout>(R.id.ll_bootom_bar)

        //如果有虚拟按键则设置Activity的statusBar隐藏
        if (DimensUtils.checkDeviceHasNavigationBar(this)) {
//            ll_root.setPadding(0, 0, 0, 50.dp2px())
//            DimensUtils.setNavigationBar(this,View.GONE)
            DimensUtils.statusBarHide(this)
        }

        initBootomBar()
        fragments.add(HomeFragment())
        fragments.add(OrderFragment())
        fragments.add(UserFragment())
        fragments.add(MoreFragment())
        changeIndex(0)

        HttpClient.Builder()
            .url("/oauth2/sys/smsLogin")
            .params("mobile", "18576710134")
            .params("validCode", "079873")
            .params("loginType", "LOGIN_MOBILE")
            .params("iceAppId", "bee20191105@USER")
            .request(object : IRequest {
                override fun onRequestStart() {//请求开始

                }

                override fun onRequestEnd() {//请求结束

                }

            })
            .failure(object : IFailure {
                override fun onFailure() {//请求错误

                }
            })
            .setLifecycleTransformer(bindUntilEvent<Activity>(ActivityEvent.DESTROY))
            .build()
            .post()
//            .request(object :ISuccess<Any>{//无参数写法
//                override fun success(t: Any) {
//
//                }
//            })
            .request(BaseBean::class.java, object : ISuccess<BaseBean> {
                //有参数写法
                override fun success(t: BaseBean) {
                    Log.i(tag, "成功：${t.code} ,${t.message}")
                }

            })
    }

    private fun initBootomBar() {
        for (i in 0 until ll_bootom_bar.childCount) {
//            ll_bootom_bar.getChildAt(i).setOnClickListener(object :View.OnClickListener{
//                override fun onClick(view: View?) {
//
//                }
//            })
            ll_bootom_bar.getChildAt(i).setOnClickListener {
                changeIndex(i)
            }
        }
    }

    //切换
    private fun changeIndex(index: Int) {
        for (i in 0 until ll_bootom_bar.childCount) {
            val child = ll_bootom_bar.getChildAt(i)
//            child.isEnabled = i != index
            setEnable(child, i != index)

//            if (i == index) {
//                child.isEnabled = false
////                setEnable(child,false)
//            } else {
//                child.isEnabled = true
////                setEnable(child,true)
//            }
        }
        supportFragmentManager.beginTransaction().replace(R.id.fral_content, fragments.get(index))
            .commit()
    }

    private fun setEnable(child: View, isEnable: Boolean) {
        child.isEnabled = isEnable
        if (child is ViewGroup) {
            for (i in 0 until child.childCount) {
                child.getChildAt(i).isEnabled = isEnable
            }
        }
    }

    /**
     * 将其添加成int类中作为扩展函数
     * dp转px
     */
    private fun Int.dp2px(): Int {
        //this是调用当前方法的对象，这里传this(50)，然后将50给转成float类型
        return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP,
            this.toFloat(),
            resources.displayMetrics
        ).toInt()

    }
}
