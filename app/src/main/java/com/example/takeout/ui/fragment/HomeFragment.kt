package com.example.takeout.ui.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.takeout.R

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/3/26 0026 15:07
 *    desc   :
 */
class HomeFragment : Fragment() {

    var TAG:String="home"

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root: View = inflater.inflate(R.layout.fragment_home, null)
        Log.i(TAG, "home")
        return root
    }
}