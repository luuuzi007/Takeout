package com.example.takeout.ui.fragment

import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.ui.adapter.HomeAdapter
import com.example.takeout.util.DimensUtils
import kotlinx.android.synthetic.main.fragment_home.*
import java.util.ArrayList

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/3/26 0026 15:07
 *    desc   :
 */
class HomeFragment : Fragment() {

    var TAG: String = "HomeFragment"
    lateinit var rlv: RecyclerView
    lateinit var homeAdapter: HomeAdapter
    var distance: Int = 0
    var datas: ArrayList<String> = ArrayList()
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, null)
        initView(root)
        initData()
        return root
    }

    private fun initView(root: View) {
        rlv = root.findViewById<RecyclerView>(R.id.rlv)
        rlv.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeAdapter(activity)
        rlv.adapter = homeAdapter
    }


    var sum = 0
    var alhpa = 55//透明度
    fun initData() {
        for (i in 0 until 100) {
            datas.add("我是商家$i")
        }
        homeAdapter.setData(datas)
        distance = 120.dp2px()
        Log.i(TAG,"distance:$distance")
        //head渐变
        //有数据的时候才去监听滚动事件
        rlv.setOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                super.onScrollStateChanged(recyclerView, newState)
            }

            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                sum += dy
                Log.i(TAG,"sum:$sum")
                if (sum > distance) {
                    alhpa = 255
                } else {
                    alhpa = (sum * 200 / distance)+55
                    Log.i(TAG,"alpha:$alhpa")
                }
                con_head.setBackgroundColor(Color.argb(alhpa, 0x31, 0x90, 0xe8))

            }
        })
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