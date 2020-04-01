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
                    "http://static2.hypable.com/wp-content/uploads/2013/12/hannibal-season-2-release-date.jpg"
                );
                url_maps.put(
                    "Big Bang Theory",
                    "http://tvfiles.alphacoders.com/100/hdclearart-10.png"
                );
                url_maps.put("House of Cards", "http://cdn3.nflximg.net/images/3093/2043093.jpg");
                url_maps.put(
                    "Game of Thrones",
                    "http://images.boomsbeat.com/data/images/full/19640/game-of-thrones-season-4-jpg.jpg"
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