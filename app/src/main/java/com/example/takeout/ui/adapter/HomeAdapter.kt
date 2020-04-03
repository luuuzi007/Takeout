package com.example.takeout.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.daimajia.slider.library.SliderLayout
import com.daimajia.slider.library.SliderTypes.TextSliderView
import com.example.takeout.R
import java.util.ArrayList
import java.util.HashMap

/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/4/1 0001 10:46
 *    desc   :
 */
class HomeAdapter(val context: Context?) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private var mDatas: ArrayList<String> = ArrayList()

    //定义常量
    companion object {
        val TYPE_TITLE = 0
        val TYPE_SELLER = 1
    }

    fun setData(datas: ArrayList<String>) {
        mDatas = datas
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        when (viewType) {
            TYPE_TITLE ->
                return TitleItemHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_title,
                        null
                    )
                )
            TYPE_SELLER ->
                return SellerItemHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_seller,
                        null
                    )
                )
            else ->
                return TitleItemHolder(
                    LayoutInflater.from(context).inflate(
                        R.layout.item_home_common,
                        null
                    )
                )
        }

    }

    override fun getItemViewType(position: Int): Int {
        if (position == 0) {
            return TYPE_TITLE
        }
        return TYPE_SELLER
    }

    override fun getItemCount(): Int {
        if (mDatas.size > 0) {
            return mDatas.size + 1
        }
        return 0
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val itemViewType = getItemViewType(position)
        when (itemViewType) {
            TYPE_TITLE -> (holder as TitleItemHolder).bindData("我的头布局------------------")
            TYPE_SELLER -> (holder as SellerItemHolder).bindData(mDatas[position - 1])
            else ->
                (holder as TitleItemHolder).bindData(mDatas[position])

        }
    }

    //头holder
    var url_maps: HashMap<String, String> = HashMap()

    inner class TitleItemHolder(item: View) : RecyclerView.ViewHolder(item) {
        var slider: SliderLayout

        init {
            slider = item.findViewById(R.id.slider)
        }

        fun bindData(data: String) {
            if (url_maps.size == 0) {
                url_maps.put(
                    "Hannibal",
                    "http://a3.att.hudong.com/14/75/01300000164186121366756803686.jpg"
                );
                url_maps.put(
                    "Big Bang Theory",
                    "http://a4.att.hudong.com/21/09/01200000026352136359091694357.jpg"
                );
                url_maps.put("House of Cards", "http://a0.att.hudong.com/64/76/20300001349415131407760417677.jpg")
                url_maps.put(
                    "Game of Thrones",
                    "http://bbs.jooyoo.net/attachment/Mon_0905/24_65548_2835f8eaa933ff6.jpg"
                );
            }
            //map遍历
            for ((key, value) in url_maps) {
                var textSliderView = TextSliderView(context)
                textSliderView.description(key).image(value)
                slider.addSlider(textSliderView)
            }
        }
    }

    //商家holder
    inner class SellerItemHolder(item: View) : RecyclerView.ViewHolder(item) {

        init {
        }

        fun bindData(data: String) {
        }
    }

}