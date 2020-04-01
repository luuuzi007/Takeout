package com.example.takeout.util

import android.annotation.SuppressLint
import android.app.ActionBar
import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.graphics.Color
import android.os.Build
import android.util.TypedValue
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import java.lang.reflect.Method


/**
 *    author : Luuuzi
 *    e-mail : wang1143303@163.com
 *    date   : 2020/3/26 0026 17:18
 *    desc   :
 */
class DimensUtils {

    companion object {

        /**
         * 获取导航栏高度
         * @param context 上下文
         * @return 单位px
         */
        fun getDaoHnagHeight(context: Context): Int {
//        var result: Int = 0
            val rid =
                context.resources.getIdentifier("config_showNavigationBar", "bool", "android")
            if (rid != 0) {
                val resultId =
                    context.resources.getIdentifier("navigation_bar_height", "dimen", "android")
                return context.resources.getDimensionPixelSize(resultId)
            }
            return 0
        }

        //获取是否存在NavigationBar
        @SuppressLint("PrivateApi")
        fun checkDeviceHasNavigationBar(context: Context): Boolean {
            var hasNavigationBar = false
            val rs: Resources = context.resources
            val id: Int = rs.getIdentifier("config_showNavigationBar", "bool", "android")
            if (id > 0) {
                hasNavigationBar = rs.getBoolean(id)
            }
            try {
                val systemPropertiesClass =
                    Class.forName("android.os.SystemProperties")
                val m: Method = systemPropertiesClass.getMethod("get", String::class.java)
                val navBarOverride =
                    m.invoke(systemPropertiesClass, "qemu.hw.mainkeys") as String
                if ("1" == navBarOverride) {
                    hasNavigationBar = false
                } else if ("0" == navBarOverride) {
                    hasNavigationBar = true
                }
            } catch (e: Exception) {
            }
            return hasNavigationBar
        }

        /**
         * 隐藏导航栏
         */
        fun setNavigationBar(activity: AppCompatActivity, visible: Int) {
            val decorView = activity.window.decorView
            if (visible == View.GONE) {
                val option: Int = View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                decorView.setSystemUiVisibility(option)
            }
        }
        /**
         * 设置Activity的statusBar隐藏
         * @param activity
         */
        fun statusBarHide(activity: Activity) { // 代表 5.0 及以上
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                val decorView = activity.window.decorView
                val option = View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                decorView.systemUiVisibility = option
                activity.window.statusBarColor = Color.TRANSPARENT
                val actionBar: ActionBar? = activity.actionBar
                actionBar?.hide()
                return
            }
            // versionCode > 4.4  and versionCode < 5.0
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT && Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP) {
                activity.window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
            }
        }

        /**
         * dp转px
         */
        fun dp2px(context: Context,dpValue:Float):Float{
            return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,dpValue,context.resources.displayMetrics);
        }
    }

}