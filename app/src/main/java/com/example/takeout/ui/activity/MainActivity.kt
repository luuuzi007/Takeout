package com.example.takeout.ui.activity

import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.takeout.R
import com.example.takeout.ui.fragment.HomeFragment
import com.example.takeout.ui.fragment.MoreFragment
import com.example.takeout.ui.fragment.OrderFragment
import com.example.takeout.ui.fragment.UserFragment
import com.example.takeout.util.DimensUtils
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    val fragments: ArrayList<Fragment> = ArrayList()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
//        val ll_bootom_bar = findViewById<LinearLayout>(R.id.ll_bootom_bar)

        //如果有虚拟按键则设置paddingbottom
        if (DimensUtils.checkDeviceHasNavigationBar(this)) {
//            ll_root.setPadding(0, 0, 0, DimensUtils.getDaoHnagHeight(this))
//            DimensUtils.setNavigationBar(this,View.GONE)
            DimensUtils.statusBarHide(this)
        }

        initBootomBar()
        fragments.add(HomeFragment())
        fragments.add(OrderFragment())
        fragments.add(UserFragment())
        fragments.add(MoreFragment())
        changeIndex(0)
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


}
