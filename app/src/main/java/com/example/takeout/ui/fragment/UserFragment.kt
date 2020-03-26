package com.example.takeout.ui.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.AppCompatTextView
import androidx.fragment.app.Fragment
import com.example.takeout.R

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/3/26 0026 15:07
 *    desc   :
 */
class UserFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val root:View=inflater.inflate(R.layout.fragment_user,null)
        (root as AppCompatTextView).text="个人"
        return root
    }
}