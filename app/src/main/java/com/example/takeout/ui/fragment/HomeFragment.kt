package com.example.takeout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.takeout.R
import com.example.takeout.ui.adapter.HomeAdapter
import java.util.ArrayList

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/3/26 0026 15:07
 *    desc   :
 */
class HomeFragment : Fragment() {

    var TAG: String = "home"
    lateinit var homeAdapter:HomeAdapter
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
        val rlv = root.findViewById<RecyclerView>(R.id.rlv)
        rlv.layoutManager = LinearLayoutManager(activity)
        homeAdapter = HomeAdapter(activity)
        rlv.adapter=homeAdapter
    }

    var datas: ArrayList<String> = ArrayList()
    fun initData() {
        for (i in 0 until 100) {
            datas.add("我是商家$i")
        }
        homeAdapter.setData(datas)
    }
}